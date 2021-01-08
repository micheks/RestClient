package com.alliky.restclient;

import android.app.Application;

import com.alliky.http.config.Config;
import com.alliky.http.interceptors.DebugInterceptor;
import com.alliky.restclient.Global.BaseAPI;

/**
 * @Description: java类作用描述
 * @Author: wxianing
 * @CreateDate: 2021/1/8 0008
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化常用配置
        Config.init(this)
                .withLoaderDelayed(500)
                .withApiHost(BaseAPI.API_HOST)
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .configure();
    }
}
