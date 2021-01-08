package com.alliky.restclient.ui.contract;

import android.content.Context;

import com.alliky.http.callback.ICallback;
import com.alliky.restclient.base.IBaseView;
import com.alliky.restclient.entity.UserEntity;


/**
 * @Description: java类作用描述
 * @Author: wxianing
 * @CreateDate: 2021/1/8 0008
 */
public interface LoginContract {

    interface View extends IBaseView {
        void loginResult(UserEntity result);
        String getUsername();
        String getPassword();
    }

    interface Model {
        void login(Context context, String username, String password , ICallback<UserEntity> callback);
    }

    interface Presenter {
        void login();
    }
}
