package com.pkest.lib.kubernetes.exception;

/**
 * @author 360733598@qq.com
 * @date 2018/11/6 22:45
 */
public class TimeoutException extends Exception{
    public TimeoutException(String message) {
        super(message);
    }
}
