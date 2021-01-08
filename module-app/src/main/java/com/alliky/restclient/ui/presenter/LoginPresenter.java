package com.alliky.restclient.ui.presenter;

import com.alliky.http.callback.ICallback;
import com.alliky.restclient.base.BasePresenter;
import com.alliky.restclient.entity.UserEntity;
import com.alliky.restclient.ui.contract.LoginContract;
import com.alliky.restclient.ui.model.LoginModel;

/**
 * @Description: java类作用描述
 * @Author: wxianing
 * @CreateDate: 2021/1/8 0008
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    LoginContract.Model mModel;

    public LoginPresenter(LoginContract.View view) {
        super(view);
        mModel = new LoginModel();
    }

    @Override
    public void login() {
        if (isViewAttached()){
            mModel.login(mContext, mView.getUsername(), mView.getPassword(), new ICallback<UserEntity>() {
                @Override
                public void onSuccess(UserEntity result) {
                    mView.loginResult(result);
                }

                @Override
                public void onError(int code, String msg) {
                    mView.onErrorCode(code,msg);
                }
            });
        }
    }
}
