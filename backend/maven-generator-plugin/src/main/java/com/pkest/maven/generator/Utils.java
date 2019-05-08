package com.pkest.maven.generator;

/**
 * Created by wuzhonggui on 2019/4/9.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class Utils {

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

    public static String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

}
