package com.pkest.web.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 360733598@qq.com
 * @date 2019/2/27 21:55
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Sortable {
        String[] value() default {};
        String message() default "排序参数[%s]不合法，该参数不能排序!";
}
