package com.pkest.common.enums;

import com.pkest.common.bean.ResponseBean;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * Created by wuzhonggui on 2018/11/13.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public enum ResultCode {
    SUCCESS(0, "操作成功"),//成功
    FAILURE(-1, "操作失败"),//失败
    BAD_REQUEST(-400, "请求无效"),//失败
    UNAUTHORIZED(-401,"签名错误"),//未认证（签名错误）
    PERMISSION_DENIED(-402,"权限不足"),//权限不足
    NOT_FOUND(-404, "此接口不存在"),//接口不存在
    SERVER_ERROR(-500, "服务器内部错误"),//服务器内部错误
    UNKNOWN_ERROR(-501, "系统繁忙,请稍后再试"),//服务器内部错误
    ALIYUN_SERVER_ERROR(-601, "阿里云请求API出错"),//服务器内部错误
    K8S_SERVER_ERROR(-701, "K8S请求API出错"),
    CMDB_SERVER_ERROR(-801, "CMDB请求API出错"),
    OTHER_SERVER_ERROR(-901, "第三方请求API出错"),
    INVALID_PARAM(-10000, "参数错误"),

    ;
    public final int state;
    public final String message;

    ResultCode(int state, String message){
        this.state = state;
        this.message = message;
    }

    public <T> ResponseBean<T> wrap(T data) {
        return wrap(data, null);
    }

    public <T> ResponseBean<T> wrap(T data, String message) {
        if(data == null) data = (T) new HashMap(0);
        if(StringUtils.isBlank(message)) message = this.message;
        return new ResponseBean(data, this.state, this.name(), message);
    }

    public <T> ResponseBean<T> message() {
        return message(message, false);
    }

    public <T> ResponseBean<T> message(String message) {
        return message(message, true);
    }

    public <T> ResponseBean<T> message(String message, boolean append) {
        if(append){
            message = this.message + ":" + message;
        }
        return new ResponseBean(new HashMap(0), this.state, this.name(), message);
    }

}

