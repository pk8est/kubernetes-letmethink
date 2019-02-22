package com.pkest.lib.myibatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wuzhonggui on 2018/5/21.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HYTable {
    String value() default "";
}

