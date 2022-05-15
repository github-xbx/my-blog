package com.xingbingxuan.blog.admin.exception;

import com.xingbingxuan.blog.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * admin服务全局异常处理类
 * @author : xbx
 * @date : 2022/5/14 11:04
 */
@ControllerAdvice
@Slf4j
public class AdminGlobalExceptionController {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        log.error(e.getMessage());
        return Result.error(e.getMessage());
    }

}
