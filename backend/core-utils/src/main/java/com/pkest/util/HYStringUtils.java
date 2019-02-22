package com.pkest.util;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 23:49
 */
public class HYStringUtils {

    public static String toUnderline(String str) {
        StringBuilder buf = new StringBuilder();
        buf.append(Character.toLowerCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                buf.append("_" + Character.toLowerCase(c));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    public static String toCamel(String str){
        if (str==null||"".equals(str.trim())){
            return "";
        }
        int len=str.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=str.charAt(i);
            if (c=='_'){
                if (++i<len){
                    sb.append(Character.toUpperCase(str.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
