package com.pkest.web.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 21:22
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class RequestAspect {

    @Pointcut("execution(public * com.pkest.web.api.controller..*.*(..))")
    public void cut() {}

    @Before("cut()")
    public void around(JoinPoint joinPoint) throws Exception {


    }

    @AfterReturning(returning = "obj", pointcut = "cut()")
    public void doAfterReturning(Object obj) {

    }
}
