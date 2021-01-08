package com.alliky.restclient.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * @Description: DOTO
 * @Author: wxianing
 * @CreateDate: 2019/9/29 17:18
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    public static final String TAG = "BaseActivity";
    protected T mPresenter;
    public WeakReference<Context> mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (setLayout() instanceof Integer) {
            setContentView((int) setLayout());
        } else if (setLayout() instanceof View) {
            setContentView((View) setLayout());
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }

        mPresenter = createPresenter();

        mContext = new WeakReference<>(this);
        mPresenter.setContext(mContext.get());

        ButterKnife.bind(this);

        onInitView(savedInstanceState);
        onInitData();
        onInitEvent();
    }

    protected abstract T createPresenter();

    public abstract Object setLayout();

    public abstract void onInitView(@Nullable Bundle savedInstanceState);

    public void onInitData() {
    }

    public void onInitEvent() {
    }

    public void showToast(String message) {
        Toast.makeText(mContext.get(),message,Toast.LENGTH_LONG).show();
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
        intent.setClass(this, clazz);
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
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    private static long lastClickTime;
    private static long delayTime = 800;

    //防止点击事件过快,点击时间间隔小于800毫秒时为重复点击
    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - lastClickTime;
        if (0 < intervalTime && intervalTime < delayTime) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销消息接收
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
