package com.xingbingxuan.blog.account.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingbingxuan.blog.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 角色权限拦截器，用于鉴别用户权限
 * @author : xbx
 * @date : 2022/9/21 22:09
 */
@Component
public class RoleAuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("========role注解拦截器=========");
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Method method = handlerMethod.getMethod();
            Role roleByMethod = AnnotationUtils.findAnnotation(method, Role.class);
            if (Objects.isNull(roleByMethod)){
                return true;
            }

            //判断是否存在token
            String token = request.getHeader(TOKEN);
            //注解的参数
            String[] value = roleByMethod.value();

            if (token == null) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString("{'code':401,'message':'请求没有token'}"));
                return false;
            } else {
                //TODO 验证
                boolean contains = Arrays.asList(value).contains(token);
                if (!contains){
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString("{'code':401,'message':'token没有权限'}"));
                    return false;
                }
            }
        }
        return true;


    }
}
