package com.alliky.http;

/**
 * Author wxianing
 * date 2018/6/26
 */
public enum RestMethod {

    GET("GET"),
    POST("POST"),
    POST_RAW("POST_RAW"),
    PUT("PUT"),
    PUT_RAW("PUT_RAW"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    UPLOADS("UPLOAD"),
    UPLOAD("UPLOAD");


    private final String value;

    RestMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static boolean permitsRetry(RestMethod method) {
        return method == GET;
    }

    public static boolean permitsCache(RestMethod method) {
        return method == GET || method == POST;
    }

    public static boolean permitsRequestBody(RestMethod method) {
        return method == null
                || method == POST
                || method == PUT
                || method == PATCH
                || method == DELETE;
    }

}
