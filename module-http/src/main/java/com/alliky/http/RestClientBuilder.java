package com.alliky.http;

import android.content.Context;

import com.alliky.http.callback.IError;
import com.alliky.http.callback.IFailure;
import com.alliky.http.callback.ILoading;
import com.alliky.http.callback.IRequest;
import com.alliky.http.callback.ISuccess;
import com.alliky.http.loader.LoaderStyle;

import java.io.File;
import java.util.List;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author wxianing
 * date 2018/6/26
 */
public final class RestClientBuilder {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private static final WeakHashMap<String, String> HEADERS = RestCreator.getHeaders();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private ILoading mILoading = null;
    private IFailure mIFailure = null;
    private IError mIError = null;

    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;
    private boolean mCancelable = true;
    private List<File> mFileList;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder headers(WeakHashMap<String, String> headers) {
        HEADERS.putAll(headers);
        return this;
    }

    public final RestClientBuilder headers(String key, String value) {
        HEADERS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder fileList(List<File> fileList) {
        this.mFileList = fileList;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder loading(ILoading iLoading) {
        this.mILoading = iLoading;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RestClientBuilder loader(Context context, boolean cancelable) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        this.mCancelable = cancelable;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, HEADERS,
                mDownloadDir, mExtension, mName,
                mIRequest, mISuccess, mILoading, mIFailure,
                mIError, mBody, mFile, mFileList, mContext, mCancelable,
                mLoaderStyle);
    }
}
