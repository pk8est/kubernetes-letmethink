package com.pkest.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pkest.common.enums.ResultCode;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * Created by wuzhonggui on 2018/11/13.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = JsonDeserializer.None.class)
@JsonPropertyOrder({"status", "code", "requestId", "message", "data"})
public class ResponseBean<T> implements Serializable {

    private T data;
    @JsonProperty("status")
    private int state;
    private String code;
    private String requestId;
    private String message = "";

    public ResponseBean(T data, int state, String code, String message) {
        this.data = data;
        this.state = state;
        this.code = code;
        this.message = message;
        this.requestId = MDC.get("requestId");
    }


    public ResponseBean(T data, ResultCode resultCode) {
        this(data, resultCode.state,  resultCode.name(), resultCode.message);
    }

    public ResponseBean(ResultCode resultCode) {
        this(null, resultCode);
    }
}
