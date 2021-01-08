package com.alliky.restclient.base;
/**
 * @Description: DOTO
 * @Author: wxianing
 * @CreateDate: 2019/9/29 17:18
 */
public interface IBaseView {
    //里面可以写loading显示和隐藏的方法回调，由于项目中已经把loading封装到网络加载中，故可做空接口处理
    /**
     * 错误码
     */
    void onErrorCode(int code, String msg);
}
