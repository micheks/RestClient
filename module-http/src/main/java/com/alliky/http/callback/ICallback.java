package com.alliky.http.callback;

/**
 * @Description TODO
 * @Author wxianing
 * @Date 2020-12-18
 */
public interface ICallback<T> {
    /**
     * 当任务成功的时候回调
     *
     * @param result 任务请求结果
     */
    void onSuccess(T result);

    /**
     * 当任务失败的时候回调
     *
     * @param code 错误码
     * @param msg  错误消息
     */
    void onError(int code, String msg);

}
