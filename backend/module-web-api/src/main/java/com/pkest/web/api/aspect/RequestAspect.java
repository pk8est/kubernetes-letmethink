package com.pkest.web.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 21:22
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class RequestAspect {

    public static final String requestIdKey = "requestId";

    @Pointcut("execution(public * com.pkest.web.api.controller..*.*(..))")
    public void cut() {}

    @Before("cut()")
    public void around(JoinPoint joinPoint) throws Exception {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if (StringUtils.isBlank(request.getHeader("RequestId"))) {
            MDC.put(requestIdKey, String.valueOf(UUID.randomUUID()));
        } else {
            MDC.put(requestIdKey, request.getHeader("RequestId"));
        }
        log.debug("{} {}?{}", request.getMethod(), request.getRequestURL(), request.getQueryString());
        //log.debug("dispatch {}",joinPoint.getSignature().getDeclaringTypeName()+'.'+ joinPoint.getSignature().getName());

    }

    @AfterReturning(returning = "obj", pointcut = "cut()")
    public void doAfterReturning(Object obj) {
        MDC.clear();
    }
}
