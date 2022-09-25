package com.xingbingxuan.blog.oauth2.filter;


import com.xingbingxuan.blog.oauth2.token.GiteeAuthenticationToken;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author xbx
 * @Date 2022/8/29 13:21
 */
@Order(2)
public class GiteeLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public static final String SPRING_SECURITY_CODE_KEY = "code";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");
    private String codeParameter = SPRING_SECURITY_CODE_KEY;
    private boolean postOnly = true;
    private static final String METHOD_POST = "GET";

    public GiteeLoginAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public GiteeLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !METHOD_POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("不支持身份验证方法: " + request.getMethod());
        } else {
            String code = this.obtainCode(request);
            String state = this.obtainState(request);
            state = state != null ? state : "";
            state = state.trim();
            code = code != null ? code : "";
            code = code.trim();
            GiteeAuthenticationToken authRequest = new GiteeAuthenticationToken(code,state);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
            //return null;
        }
    }


    @Nullable
    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }

    @Nullable
    protected String obtainState(HttpServletRequest request){ return request.getParameter("state"); }

    protected void setDetails(HttpServletRequest request, GiteeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Code 参数不能为空或null");
        this.codeParameter = codeParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getCodeParameter() {
        return this.codeParameter;
    }

}
