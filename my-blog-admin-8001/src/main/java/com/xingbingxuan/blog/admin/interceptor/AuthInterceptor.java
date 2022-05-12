package com.xingbingxuan.blog.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xingbingxuan.blog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author : xbx
 * @date : 2022/5/6 21:37
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("admin 后台请求 -> {}",requestURI);

        String authorization = request.getHeader("Authorization");

        String o = (String) RedisUtil.get(authorization);

        if (StringUtils.hasText(o)){
            //不为空，登录有效 拦截放行

            return true;

        }else {
            //登录无效
            log.info("登录过期！！！");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",401);
            jsonObject.put("message","登录过期请重新登录！！！");
            response.getWriter().write(jsonObject.toString());
            return false;
        }



    }
}
