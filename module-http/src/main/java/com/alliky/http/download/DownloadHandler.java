package com.alliky.http.download;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.alliky.http.RestCreator;
import com.alliky.http.callback.IError;
import com.alliky.http.callback.IFailure;
import com.alliky.http.callback.ILoading;
import com.alliky.http.callback.IRequest;
import com.alliky.http.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author wxianing
 * date 2018/6/26
 */
public final class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final ILoading LOADING;

    public DownloadHandler(String url,
                           IRequest request,
                           String downDir,
                           String extension,
                           String name,
                           ISuccess success,
                           ILoading loading,
                           IFailure failure,
                           IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADING = loading;
    }

    public final void handleDownload() {

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator
                .getHttpService()
                .download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS, LOADING);

                            String ext = EXTENSION;

                            if (TextUtils.isEmpty(ext)){
                                ext = URL.substring(URL.lastIndexOf('.') + 1);
                            }

                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, ext, responseBody, NAME);


                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure(t);
                            RestCreator.getParams().clear();
                        }
                    }
                });

    }
}
