package com.xingbingxuan.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一的返回集合
 * @author : xbx
 * @date : 2022/3/24 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result<T> {

    private Integer code;

    private String message;

    private T object;

    /*success*/
    public static <T>Result<T> success(T object){
        return new Result(200, "success", object);
    }
    public static <T>Result<T> success(Integer status, T data) {
        return new Result<T>(status, "success", data);
    }
    public static <T>Result<T> success(){
        return new Result(200,"success","");
    }
    public static <T>Result<T> success(String message, T data) {
        return new Result<T>(200,message, data);
    }
    /*error*/
    public static <T>Result<T> error(){
        return new Result<T>(400,"error", (T) "");
    }
    public static <T>Result error(String errorMessage){
        return new Result<T>(400,errorMessage, (T) "");
    }
    public static <T>Result<T> error(Integer code,String message){
        return new Result<T>(code,message, (T) "");
    }
}
