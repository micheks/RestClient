package com.alliky.restclient.ui.model;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alliky.http.RestClient;
import com.alliky.http.callback.ICallback;
import com.alliky.http.callback.IError;
import com.alliky.http.callback.ISuccess;
import com.alliky.restclient.entity.BaseResponse;
import com.alliky.restclient.entity.UserEntity;
import com.alliky.restclient.ui.contract.LoginContract;

/**
 * @Description: java类作用描述
 * @Author: wxianing
 * @CreateDate: 2021/1/8 0008
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public void login(Context context, String username, String password, ICallback<UserEntity> callback) {
        RestClient.builder()
                .url("user/login")
                .params("username",username)
                .params("password",password)
                .loader(context)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        BaseResponse<UserEntity> baseResponse = JSONObject.parseObject(response,new TypeReference<BaseResponse<UserEntity>>(){});
                        if (null!=baseResponse&&baseResponse.isOk()){
                            callback.onSuccess(baseResponse.getData());
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        callback.onError(code,msg);
                    }
                })
                .build()
                .post();
    }
}
