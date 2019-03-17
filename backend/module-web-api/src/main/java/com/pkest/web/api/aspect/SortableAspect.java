package com.pkest.web.api.aspect;

import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYKnownException;
import com.pkest.web.api.annotation.Sortable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 21:22
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class SortableAspect {

    @Pointcut("@annotation(com.pkest.web.api.annotation.Sortable)")
    public void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint) throws HYKnownException{
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Sortable annotation = method.getAnnotation(Sortable.class);
        if(annotation == null){
            return;
        }
        String message = annotation.message();
        List<String> sortable = Arrays.asList(annotation.value());
        for (Object arg: joinPoint.getArgs()){
            if(arg instanceof Pageable){
                this.judgeSortable(((Pageable)arg).getSort(), sortable, message);
            }
        }

    }

    private void judgeSortable(Sort sort, List<String> sortable, String message) throws HYKnownException {
        if(sort != null) {
            Iterator<Sort.Order> iterable = sort.iterator();
            while (iterable.hasNext()) {
                Sort.Order order = iterable.next();
                if (!sortable.contains(order.getProperty())) {
                    throw new HYKnownException(ResultCode.INVALID_PARAM.message(String.format(message, order.getProperty())));
                }
            }
        }
    }

}
