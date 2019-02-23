package com.pkest.web.api.controller;

import com.pkest.web.service.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wuzhonggui on 2019/1/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Controller
public abstract class BaseController<T extends BaseService> {

    private T service;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public void setService(T service){
        this.service = service;
    }

    public T getService(){
        return service;
    }

    public HttpServletRequest getRequest() {
        return request;
    }


}
