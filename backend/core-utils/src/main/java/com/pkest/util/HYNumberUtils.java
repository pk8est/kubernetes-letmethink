package com.pkest.util;

import com.google.common.primitives.Doubles;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wuzhonggui on 2018/11/28.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class HYNumberUtils {

    public static boolean isNullorZero(Number i){
        return 0 == ( i == null ? 0 : i).intValue();
    }

    public static boolean isPositiveNumber(Number i){
        if(isNullorZero(i)){
            return false;
        }
        return i.intValue() > 0;
    }

    public static <T> T number(String content) {
         return (T) number(content, 0);
    }

    public static <T> T number(String content, T defaultValue) {
        return number(content, defaultValue, null);
    }

    public static <T> T number(String content, Class<T> clazz) {
        return number(content, null, clazz);
    }

    public static <T> T number(String content, T defaultValue, Class<T> clazz) {
        if(clazz == null && defaultValue != null){
            clazz = (Class<T>) defaultValue.getClass();
        }
        Double number = Doubles.tryParse(numberString(content));
        if(Integer.class.equals(clazz)){
            return (T) new Integer(number.intValue());
        }else if(Long.class.equals(clazz)){
            return (T) new Long(number.longValue());
        }else if(Double.class.equals(clazz)){
            return (T) number;
        }else if(Float.class.equals(clazz)){
            return (T) new Float(number.floatValue());
        }
        return defaultValue;
    }

    public static String numberString(String content) {
        return numberString(content, "0");
    }

    public static String numberString(String content, String defaultValue) {
        if (StringUtils.isBlank(content)) {
            return defaultValue;
        }
        int ascll;
        char ch;
        int len = content.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            ch = content.charAt(i);
            ascll = (int)ch;
            if((ascll >= 48 && ascll < 59) || ascll == 46){
                stringBuilder.append(ch);
            }else{
                break;
            }
        }
        return stringBuilder.toString();
    }

}
