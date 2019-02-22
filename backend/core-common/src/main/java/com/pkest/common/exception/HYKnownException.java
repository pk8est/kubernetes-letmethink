package com.pkest.common.exception;


import com.pkest.common.bean.ResponseBean;

/**
 * Created by wuzhonggui on 2019/1/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYKnownException extends HYServerException{

    private ResponseBean responseBean;

    public HYKnownException(ResponseBean responseBean) {
        super(responseBean.getMessage());
        setResponseBean(responseBean);
    }

    public ResponseBean getResponseBean() {
        return responseBean;
    }

    public void setResponseBean(ResponseBean responseBean) {
        this.responseBean = responseBean;
    }
}
