package com.xingbingxuan.blog.account.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingbingxuan.blog.account.feign.AccountAuthorizeServiceFeign;
import com.xingbingxuan.blog.account.utils.SpringContextUtil;
import com.xingbingxuan.blog.annotation.Role;
import com.xingbingxuan.blog.token.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 角色权限拦截器，用于鉴别用户权限
 * @author : xbx
 * @date : 2022/9/21 22:09
 */
@Component
@Slf4j
public class RoleAuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpServletRequest thisRequest = request;

        log.info("======== account service role注解拦截器 =========");
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Method method = handlerMethod.getMethod();
            Role roleByMethod = AnnotationUtils.findAnnotation(method, Role.class);
            if (Objects.isNull(roleByMethod)){
                return true;
            }
            //拦截器中回去feign对象
            AccountAuthorizeServiceFeign accountAuthorizeServiceFeign =
                    (AccountAuthorizeServiceFeign) SpringContextUtil.getBeanByClass(AccountAuthorizeServiceFeign.class);

            //注解的参数
            String[] value = roleByMethod.value();

            //判断是否存在token
            Authentication authentication = accountAuthorizeServiceFeign.checkToken();

            if (authentication != null){
                //用户的权限信息
                List<String> authorities = authentication.getAuthorities();
                //验证手否存在权限信息
                Boolean aBoolean = checkAuth(value, authorities);
                if (!aBoolean){
                    log.info("token没有权限");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString("{'code':401,'message':'token没有权限'}"));
                    return false;
                }

            }else {
                log.info("请求没有token");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString("{'code':401,'message':'请求没有token'}"));
                return false;
            }

        }
        return true;


    }


    private Boolean checkAuth(String[] strings,List list){
        for (String string : strings) {
            if (list.contains(string)){
                return  true;
            }
        }
        return false;
    }
}
