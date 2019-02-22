package com.pkest.common.exception;

/**
 * Created by wuzhonggui on 2019/1/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYClientException extends Exception{

    public HYClientException() {
        this("");
    }

    public HYClientException(String message) {
        super(message);
    }
}
