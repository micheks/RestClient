package com.alliky.restclient.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description: DOTO
 * @Author: wxianing
 * @CreateDate: 2019/9/29 17:18
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    //上下文
    public P mPresenter;
    private Unbinder mUnbinder;
    public WeakReference<Context> mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }

        mPresenter = createPresenter();
        mContext = new WeakReference<>(getActivity());
        mPresenter.setContext(mContext.get());

        mUnbinder = ButterKnife.bind(this, rootView);

        onInitView(savedInstanceState, rootView);

        onInitData();

        onInitEvent();

        return rootView;
    }

    protected abstract P createPresenter();

    public abstract Object setLayout();

    public abstract void onInitView(Bundle savedInstanceState, View view);

    public void onInitData() {
    }

    public void onInitEvent() {
    }

    public void showToast(String message) {
        Toast.makeText(mContext.get(),message,Toast.LENGTH_LONG).show();
    }

    public boolean isBackPressed() {
        return false;
    }

    /**
     * 页面跳转
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }

    /**
     * 携带数据跳转
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext.get(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext.get(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder!=null) {
            mUnbinder.unbind();
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
