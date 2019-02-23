package com.pkest.util;

import org.springframework.beans.BeanUtils;

import javax.swing.text.html.Option;
import java.util.Collection;

/**
 * Created by wuzhonggui on 2019/1/22.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYPropertyUtils {

    public static <T> T get(Object object, String key){
        return get(object, key, null);
    }

    public static <T> T get(Object object, String key, Class<T> tClass){
        return get(object, key, null, true);
    }

    public static <T> T get(Object object, String key, T defaultValue){
        return get(object, key, defaultValue, true);
    }

    public static <T> T get(Object object, String key, T defaultValue, boolean quiet){
        int pos = key.indexOf(".");
        if(pos == -1){
            return getProperty(object, key, defaultValue);
        }else{
            String firstKey = key.substring(0, pos);
            Object current = getProperty(object, firstKey, null, quiet);
            if(current == null){
                if(quiet){
                    return defaultValue;
                }else{
                    throw new RuntimeException("Property["+key+"] not found!");
                }
            }
            return get(current, key.substring(pos + 1), defaultValue, quiet);
        }
    }

    public static <T> T getProperty(Object object, String key){
        return getProperty(object, key, null, true);
    }

    public static <T> T getProperty(Object object, String key, Class<T> tClass){
        return getProperty(object, key, null, true);
    }

    public static <T> T getProperty(Object object, String key, T defaultValue){
        return getProperty(object, key, defaultValue, true);
    }

    public static <T> T getProperty(Object object, String key, T defaultValue, boolean quiet){
        try {
            T item = (T) BeanUtils.getPropertyDescriptor(object.getClass(), key).getReadMethod().invoke(object);
            return item == null ? defaultValue : item;
        }catch (Exception e){
            if(quiet){
                return defaultValue;
            }else{
                throw new RuntimeException("Property["+key+"] not found!");
            }
        }
    }

}
