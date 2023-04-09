package com.quvideo.controller;

import com.quvideo.exception.BusinessException;
import com.quvideo.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 定义异常处理器类
 */
@RestControllerAdvice
public class ProjectExceptionAdvice {
    /**
     * 拦截异常: 表明拦截哪一种异常
     */
    // 拦截自定义的业务异常
    public Result doBusinessException(BusinessException ex) {
        System.out.println(ex.getLocalizedMessage());
        return new Result(ex.getCode(), ex.getMessage(), null);
    }

    // 拦截自定义的系统异常
    public Result doSystemException(SystemException ex) {
        System.out.println(ex.getLocalizedMessage());
        return new Result(ex.getCode(), ex.getMessage(), null);
    }



    // 拦截其他类型的未知异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        System.out.println(ex.getLocalizedMessage());
        return new Result(Code.UNKNOWN_ERR, "服务异常, 请稍后再试！", null);
    }

}
