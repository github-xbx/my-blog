package com.xingbingxuan.blog.account.exception;

import com.xingbingxuan.blog.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLSyntaxErrorException;

/**
 * 账户服务全局的异常处理
 * @author : xbx
 * @date : 2022/10/1 10:37
 */
@ControllerAdvice
@Slf4j
public class AccountGlobExceptionController {

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
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public Result SQLSyntaxErrorException(SQLSyntaxErrorException e){
        log.error(e.getMessage());
        return Result.error(500,"sql错误！！！");
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public Result NullPointerException(NullPointerException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error(500,"空指针异常！！！");
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result UsernameNotFoundException(UsernameNotFoundException e){
        log.error(e.getMessage());
        return Result.error(500,e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public Result BadCredentialsException(BadCredentialsException e){
        log.error(e.getMessage());
        return Result.error(500,e.getMessage());
    }
}
