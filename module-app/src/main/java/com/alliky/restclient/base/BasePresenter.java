package com.alliky.restclient.base;

import android.content.Context;

/**
 * @Description: DOTO
 * @Author: wxianing
 * @CreateDate: 2019/9/29 17:18
 */
public class BasePresenter<V extends IBaseView> {

    public V mView;
    private Context mContext;

    public BasePresenter(V view) {
        this.mView = view;
    }

//    /**
//     * 绑定view，一般在初始化中调用该方法
//     *
//     * @param view view
//     */
//    public void attachView(V view) {
//        this.mView = view;
//    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    public void detachView() {

        if (mView != null) {
            this.mView = null;
        }

        if (mContext != null) {
            mContext = null;
        }
    }

    /**
     * View是否绑定
     */
    public boolean isViewAttached() {
        return mView != null;
    }
}