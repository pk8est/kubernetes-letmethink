package com.pkest.web.api.request;

import org.springframework.beans.BeanUtils;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 22:39
 */
public class BaseBody {
    public <T> T toDto(Class<T> clazz){
        T dto = BeanUtils.instantiate(clazz);
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
