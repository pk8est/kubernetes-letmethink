package com.pkest.web.api.annotation;

import com.pkest.lib.myibatis.enums.QueryOperator;

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
public @interface QueryItem {
        String value();

        String fieldName() default "";

        String paramName() default "";

        QueryOperator operator() default QueryOperator.EQ;

        String[] defaultValue() default {};

        boolean and() default true;

        boolean lLike() default true;

        boolean rLike() default true;

        boolean camelCaseToUnderscore() default true;

        boolean allowEmpey() default true;
}
