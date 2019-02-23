package com.pkest.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.developer.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Created by wuzhonggui on 2018/8/28.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYObjMapper {

    private static ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(MapperFeature.USE_ANNOTATIONS);

    public static String toJson(Object value) {
        return toJson(value, true);
    }

    public static String toJson(Object value, boolean quiet) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            if(!quiet){
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        return toObject(json, valueType);
    }

    public static <T> T toObject(String json, Class<T> valueType, boolean quiet) {
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            if(!quiet){
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }


}
