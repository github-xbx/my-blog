package com.xingbingxuan.blog.account.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * feign 拦截器
 * @author : xbx
 * @date : 2022/10/6 19:31
 */
@Configuration
public class AccountFeignInterceptor implements RequestInterceptor {
    /**
     * 复写feign请求对象
     * @param requestTemplate hhh
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取请求头
        Map<String,String> headers = getHeaders(Objects.requireNonNull(getHttpServletRequest()));
        for(String headerName : headers.keySet()){
            requestTemplate.header(headerName, getHeaders(getHttpServletRequest()).get(headerName));
        }
    }
    //获取请求对象
    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //拿到请求头信息
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }


}
