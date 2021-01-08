package com.alliky.http;

import android.content.Context;

import com.alliky.http.callback.IService;
import com.alliky.http.callback.IError;
import com.alliky.http.callback.IFailure;
import com.alliky.http.callback.ILoading;
import com.alliky.http.callback.IRequest;
import com.alliky.http.callback.ISuccess;
import com.alliky.http.download.DownloadHandler;
import com.alliky.http.loader.Loader;
import com.alliky.http.loader.LoaderStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Author wxianing
 * date 2018/6/26
 */
public final class RestClient {
    //参数
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    //请求头
    private static final WeakHashMap<String, String> HEADERS = RestCreator.getHeaders();
    //URL
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    private final ISuccess SUCCESS;
    private final ILoading LOADING;
    private final IFailure FAILURE;
    private final IError ERROR;

    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final boolean CANCELABLE;
    private final File FILE;
    private final List<File> FILELIST;
    private final Context CONTEXT;

    RestClient(String url,
               Map<String, Object> params,
               Map<String, String> headers,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               ILoading loading,
               IFailure failure,
               IError error,
               RequestBody body,
               File file,
               List<File> fileList,
               Context context,
               boolean cancelable,
               LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        HEADERS.putAll(headers);
        this.FILELIST = fileList;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.LOADING = loading;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CANCELABLE = cancelable;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    /**
     * 建造者创建HttpClientBuilder对象
     *
     * @return
     */
    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(RestMethod method) {
        final IService service = RestCreator.getHttpService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (CONTEXT != null) {
            if (LOADER_STYLE != null) {
                Loader.showLoading(CONTEXT, LOADER_STYLE, CANCELABLE);
            }
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS, HEADERS);
                break;
            case POST:
                call = service.post(URL, PARAMS, HEADERS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY, HEADERS);
                break;
            case PUT:
                call = service.put(URL, PARAMS, HEADERS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY, HEADERS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS, HEADERS);
                break;
            case UPLOAD:

                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);

                call = service.upload(URL, body, HEADERS);

                break;
            case UPLOADS:

//                MultipartBody.Builder builder = new MultipartBody.Builder();
//                for (File file : FILELIST){
//                    RequestBody responseBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),file);
//                    builder.addFormDataPart("file",file.getName(),responseBody);
//                }
//
//                builder.setType(MultipartBody.FORM);
//                MultipartBody multipartBody = builder.build();
//                call = service.upload(URL,multipartBody,HEADERS);


                List<MultipartBody.Part> partList = new ArrayList<>();
                for (File file : FILELIST) {
                    final RequestBody requestBodys = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                    final MultipartBody.Part bodys = MultipartBody.Part.createFormData("file", file.getName(), requestBodys);
                    partList.add(bodys);
                }
                call = service.upload(URL, partList, HEADERS);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
            PARAMS.clear();
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                URL,
                PARAMS,
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get() {
        request(RestMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(RestMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(RestMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(RestMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(RestMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(RestMethod.DELETE);
    }

    public final void upload() {
        request(RestMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, LOADING, FAILURE, ERROR)
                .handleDownload();
    }
}
