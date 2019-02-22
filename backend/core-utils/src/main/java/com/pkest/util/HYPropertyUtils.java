package com.pkest.util;

import org.springframework.beans.BeanUtils;

/**
 * Created by wuzhonggui on 2019/1/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYPropertyUtils {

    public static <T> T getProperty(Object object, String field){
        return getProperty(object, field, null);
    }

    public static <T> T getProperty(Object object, String field, T defaultValue){
        try {
            return (T) BeanUtils.getPropertyDescriptor(object.getClass(), field).getReadMethod().invoke(object);
        }catch (Exception e){
            if(defaultValue != null){
                return defaultValue;
            }else{
                throw new RuntimeException("Property["+field+"] not found!");
            }
        }
    }

}
