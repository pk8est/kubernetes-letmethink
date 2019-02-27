package com.pkest.web.api.annotation;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

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
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "分页页码"),
        @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "分页大小. 默认20"),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "排序, 例: sort=id&sort=created_at,desc"
        ) })
public @interface ApiPageable {

}
