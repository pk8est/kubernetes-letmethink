package com.pkest.web.api.exception;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pkest.common.bean.ResponseBean;
import com.pkest.common.enums.ResultCode;
import com.pkest.common.exception.HYClientException;
import com.pkest.common.exception.HYKnownException;
import com.pkest.common.exception.HYServerException;
import com.pkest.common.exception.RecordNotFoundException;
import com.pkest.lib.kubernetes.exception.K8sDriverException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

/**
 * @author 360733598@qq.com
 * @date 2019/2/22 21:13
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResponseBean<String> defaultErrorHandler(HttpServletRequest req, Throwable e) {
        this.exceptionLogger(req, e);
        return ResultCode.UNKNOWN_ERROR.wrap(null);
    }

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, HYClientException.class})
    public ResponseBean<String> badRequestErrorHandler(HttpServletRequest req, Exception e) {
        this.exceptionLogger(req, e);
        return ResultCode.BAD_REQUEST.message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {HYKnownException.class})
    public ResponseBean<String> knowErrorHandler(HttpServletRequest req, HYKnownException e){
        this.exceptionLogger(req, e);
        return e.getResponseBean();
    }

    @ResponseBody
    @ExceptionHandler(value = {K8sDriverException.class})
    public ResponseBean<String> k8sErrorHandler(HttpServletRequest req, K8sDriverException e) {
        this.exceptionLogger(req, e);
        return ResultCode.K8S_SERVER_ERROR.message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseBean<String> k8sErrorHandler(HttpServletRequest req, ConstraintViolationException e) {
        this.exceptionLogger(req, e);
        return ResultCode.K8S_SERVER_ERROR.message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UndeclaredThrowableException.class)
    public ResponseBean<String> permissionExceptionHandler(HttpServletRequest req, UndeclaredThrowableException e) {
        this.exceptionLogger(req, e);
        if (UndeclaredThrowableException.class.isAssignableFrom(e.getClass())) {
            return ResultCode.PERMISSION_DENIED.message(e.getUndeclaredThrowable().getMessage());
        }
        return ResultCode.UNKNOWN_ERROR.wrap(null);
    }

    @ResponseBody
    @ExceptionHandler(value = HYServerException.class)
    public ResponseBean<String> defaultErrorHandler(HttpServletRequest req, HYServerException e) {
        this.exceptionLogger(req, e);
        return ResultCode.SERVER_ERROR.message(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {BindException.class})
    public ResponseBean bindErrorHandler(HttpServletRequest req, BindException e) {
        this.exceptionLogger(req, e);
        List<String> errors = Lists.newArrayList();
        e.getFieldErrors().forEach(fieldError -> {
            errors.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        });
        return ResultCode.INVALID_PARAM.wrap(ImmutableMap.of("errors", errors));
    }

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseBean methodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        this.exceptionLogger(req, e);

        List<String> errors = Lists.newArrayList();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        });
        return ResultCode.INVALID_PARAM.wrap(ImmutableMap.of("errors", errors));
    }

    public void exceptionLogger(HttpServletRequest req, Throwable e) {
        StringBuilder builder = new StringBuilder();
        builder.append("[IP]").append(req.getRemoteAddr()).append(" ");
        builder.append(req.getMethod()).append(" ").append(req.getRequestURL());
        if (StringUtils.isNotBlank(req.getQueryString())) {
            builder.append("?").append(req.getQueryString());
        }
        log.error("{}", builder, e);
    }
}
