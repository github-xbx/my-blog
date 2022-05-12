package com.xingbingxuan.blog.client.exception;

import com.xingbingxuan.blog.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * @author : xbx
 * @date : 2022/4/4 23:07
 */
@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result RequestMethod(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage());
        return Result.error(405,"不支持"+e.getMethod()+"方式的请求！！！");
    }


    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result RequestType(HttpMediaTypeNotSupportedException e){
        log.error(e.getMessage());
        return Result.error(415,"请求格式错误！！！ ");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result EntityException(MethodArgumentNotValidException e){
        log.error(e.getBindingResult().getFieldError().getDefaultMessage());
        //log.error(e.getMessage());
        return Result.error(401,e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result Exception(Exception e){
        log.error(e.getMessage());
        return Result.error(500,e.getMessage());
    }


}
