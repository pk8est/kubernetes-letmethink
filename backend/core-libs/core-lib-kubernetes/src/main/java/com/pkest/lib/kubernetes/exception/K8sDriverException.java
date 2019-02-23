package com.pkest.lib.kubernetes.exception;

import io.fabric8.kubernetes.api.model.Status;
import io.fabric8.kubernetes.client.KubernetesClientException;

/**
 * @author 360733598@qq.com
 * @date 2018/11/6 22:45
 */
public class K8sDriverException extends Exception {

    private int code;
    private Status status;
    private String message;
    private Throwable cause;


    public int getCode() {
        return code;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public String getMessage(){
        StringBuilder builder = new StringBuilder(cause.toString() + ", " + message);
        if(code != 0) builder.append(", code: [" + code + "]");
        if(status != null) builder.append(", status: [" + status.getMessage() + "]");
        return builder.toString();
    }

    public K8sDriverException(String message) {
        this.message = message;
    }

    public K8sDriverException(KubernetesClientException e) {
        this.code = e.getCode();
        this.status = e.getStatus();
        this.cause = e.getCause();
        this.message = e.getMessage();
    }

}
