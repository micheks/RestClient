package com.alliky.restclient.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alliky.restclient.R;
import com.alliky.restclient.base.BaseActivity;
import com.alliky.restclient.entity.UserEntity;
import com.alliky.restclient.ui.contract.LoginContract;
import com.alliky.restclient.ui.presenter.LoginPresenter;
import com.alliky.restclient.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_username)
    ClearEditText et_username;
    @BindView(R.id.et_password)
    ClearEditText et_password;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public Object setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onInitView(@Nullable Bundle savedInstanceState) {

    }

    //登录
    @OnClick(R.id.btn_login)
    public void login(View v){
        mPresenter.login();
    }

    @Override
    public void loginResult(UserEntity result) {
        if (result!=null){
            startActivity(MainActivity.class);
        }
    }

    @Override
    public String getUsername() {
        return et_username.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString().trim();
    }

    @Override
    public void onErrorCode(int code, String msg) {
        showToast(msg);
    }
}
