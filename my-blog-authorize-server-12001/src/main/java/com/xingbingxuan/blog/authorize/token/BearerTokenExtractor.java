package com.xingbingxuan.blog.authorize.token;

import com.xingbingxuan.blog.token.AccessTokenUtil;
import com.xingbingxuan.blog.token.Authentication;
import lombok.extern.slf4j.Slf4j;



import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * token处理类
 * @author : xbx
 * @date : 2022/10/5 16:03
 */
@Slf4j
public class BearerTokenExtractor {

    private final static String BEARER_TYPE = "Bearer";

    private final static String ACCESS_TOKEN_TYPE = BearerTokenExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public Authentication extract(HttpServletRequest request) {
        String tokenValue = extractToken(request);
        if (tokenValue != null) {

            return getTokenAuthentication(tokenValue);
        }
        return null;
    }

    private Authentication getTokenAuthentication(String tokenValue){
        //数据库查询token授权信息
        Authentication authentication = AccessTokenUtil.getAuthentication(tokenValue);

        return authentication;


    }

    protected String extractToken(HttpServletRequest request) {
        // 先检查请求头
        String token = extractHeaderToken(request);

        // bearer type allows a request parameter as well
        if (token == null) {
            log.debug("在标头中找不到标记。正在尝试请求参数。");
            token = request.getParameter("access_token");
            if (token == null) {
                log.debug("在请求参数中找不到令牌。不是OAuth2请求。");
            } else {
                request.setAttribute(ACCESS_TOKEN_TYPE, "Bearer");
            }
        }

        return token;
    }

    /**
     * 从标头提取OAuth承载令牌。
     *
     * @param request The request.
     * @return 令牌，如果未提供OAuth授权头，则为null。
     */
    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) {
            // typically there is only one (most servers enforce that)
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                // Add this here for the auth details later. Would be better to change the signature of this method.
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }


}
