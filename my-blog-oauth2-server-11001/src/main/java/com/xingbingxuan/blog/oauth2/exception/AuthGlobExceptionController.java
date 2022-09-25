package com.xingbingxuan.blog.oauth2.exception;

import com.xingbingxuan.blog.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : xbx
 * @date : 2022/9/5 10:32
 */
@ControllerAdvice
@Slf4j
public class AuthGlobExceptionController {

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result RequestMethod(HttpRequestMethodNotSupportedException e){
        //log.error(e.getMessage());
        return Result.error(405,"不支持"+e.getMethod()+"方式的请求！！！");
    }

    @ResponseBody
    @ExceptionHandler(RequestRejectedException.class)
    public Result RequestRejectedException(RequestRejectedException e){
        log.error(e.getMessage());
        return Result.error(400,"请求被拒绝");
    }
}
