package com.alliky.http;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alliky.http.callback.IError;
import com.alliky.http.callback.IFailure;
import com.alliky.http.callback.IRequest;
import com.alliky.http.callback.ISuccess;
import com.alliky.http.config.Config;
import com.alliky.http.config.ConfigKeys;
import com.alliky.http.loader.Loader;
import com.alliky.http.loader.LoaderStyle;
import com.alliky.http.util.KLog;

import java.util.WeakHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author wxianing
 * date 2018/6/26
 */
public final class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final String URL;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = Config.getHandler();
    private WeakHashMap<String, Object> mParams;

    public RequestCallbacks(String url, WeakHashMap<String, Object> params, IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.URL = Config.getConfiguration(ConfigKeys.API_HOST) + url;
        mParams = new WeakHashMap<>();
        this.mParams.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    try {
                        SUCCESS.onSuccess(response.body());

                        String paramJson = "";

                        if (null != mParams) {
                            //打印参数，方便调试
                            paramJson = JSON.toJSONString(mParams);
                        }

                        KLog.i("response", "URL：" + URL + "\n\n"
                                + "param：" + paramJson + "\n\n"
                                + response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                        KLog.e("Exception", e.getMessage());
                    }
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

        KLog.e("onFailure", "URL：" + URL + "\n\n" + t.getMessage());

        if (FAILURE != null) {
            FAILURE.onFailure(t);
        }

        if (ERROR!=null){
            ERROR.onError(-1,t.getMessage());
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        onRequestFinish();
    }

    private void onRequestFinish() {
        final long delayed = Config.getConfiguration(ConfigKeys.LOADER_DELAYED);
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        RestCreator.getParams().clear();
                        Loader.stopLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, delayed);
        }
    }
}
