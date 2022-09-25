package com.xingbingxuan.blog.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 角色信息注解
 * @author : xbx
 * @date : 2022/9/21 22:12
 */

@Target({ElementType.METHOD}) //目标，主要在方法
@Retention(RetentionPolicy.RUNTIME) //运行期也保留该注解，因此可以使用反射机制读取该注解的信息
@Documented
public @interface Role {
    String[] value() default {};
}