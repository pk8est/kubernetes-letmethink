package com.pkest.web.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 拦截器
 *
 * @author Jaward
 */
@Slf4j
@Component
public class GlobalInterceptor implements HandlerInterceptor {

    String REQUEST_ID_KEY = "requestId";

    String REQUEST_STARTTIME_KEY = "requestStartTimeKey";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equals(request.getMethod())){
            return true;
        }

        if(StringUtils.isBlank(request.getHeader("RequestId"))){
            MDC.put(REQUEST_ID_KEY, String.valueOf(UUID.randomUUID()));
        }else{
            MDC.put(REQUEST_ID_KEY, request.getHeader("RequestId"));
        }
        MDC.put(REQUEST_STARTTIME_KEY, String.valueOf(System.currentTimeMillis()));
        log.info("{} {}",request.getMethod(), getRequestURL(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
            throws Exception {
        MDC.clear();
    }

    private String getRequestURL(HttpServletRequest r){
        if(StringUtils.isBlank(r.getQueryString())){
            return r.getRequestURL().toString();
        }else{
            return r.getRequestURL().append("?").append(r.getQueryString()).toString();
        }
    }
}
