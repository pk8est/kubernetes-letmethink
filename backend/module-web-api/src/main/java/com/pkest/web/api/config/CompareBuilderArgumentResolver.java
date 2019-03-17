package com.pkest.web.api.config;

import com.pkest.lib.myibatis.CompareBuilder;
import com.pkest.lib.myibatis.HYMybatisReflectUtil;
import com.pkest.lib.myibatis.QueryBuilder;
import com.pkest.lib.myibatis.enums.QueryOperator;
import com.pkest.web.api.annotation.QueryItem;
import com.pkest.web.api.annotation.Searchable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

/**
 * @author 360733598@qq.com
 * @date 2019/3/17 11:13
 */
public class CompareBuilderArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().isAssignableFrom(QueryBuilder.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return builder(parameter.getMethod().getAnnotation(Searchable.class), webRequest);
    }

    protected QueryBuilder builder(Searchable annotation, NativeWebRequest webRequest){
        QueryBuilder builder = CompareBuilder.builder();
        if(annotation != null){
            for (String field: annotation.value()){
                builder.compare(HYMybatisReflectUtil.toUnderline(field), webRequest.getParameter(field));
            }
            for (QueryItem queryItem: annotation.queryItems()){
                Object value = getParamValue(queryItem, webRequest);
                if(QueryOperator.LIKE.equals(queryItem.operator()) || QueryOperator.NOTLIKE.equals(queryItem.operator())){
                    builder.addSearchCondition(
                            getFieldName(queryItem),
                            value,
                            queryItem.and(),
                            QueryOperator.LIKE.equals(queryItem.operator()),
                            queryItem.lLike(),
                            queryItem.rLike(),
                            queryItem.allowEmpey()
                    );
                }else{
                    builder.compare(getFieldName(queryItem), queryItem.operator(), value);
                }
            }
        }
        return builder;
    }

    public Object getParamValue(QueryItem queryItem, NativeWebRequest webRequest){
        String[] value;
        if(webRequest.getParameterMap().containsKey(getParamName(queryItem))){
            value = webRequest.getParameterMap().get(getParamName(queryItem));
        }else{
            value = queryItem.defaultValue();
        }
        if(QueryOperator.IN.equals(queryItem.operator())){
            return Arrays.asList(value);
        }else{
            return value;
        }
    }

    public String getParamName(QueryItem queryItem){
        return StringUtils.isBlank(queryItem.paramName()) ? queryItem.value() : queryItem.paramName();
    }

    public String getFieldName(QueryItem queryItem){
        if(StringUtils.isNotBlank(queryItem.fieldName())){
            return queryItem.fieldName();
        }else{
            return queryItem.camelCaseToUnderscore() ? HYMybatisReflectUtil.toUnderline(queryItem.value()) : queryItem.value();
        }
    }

}
