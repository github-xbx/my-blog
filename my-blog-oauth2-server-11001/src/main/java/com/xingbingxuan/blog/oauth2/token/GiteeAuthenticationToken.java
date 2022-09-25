package com.xingbingxuan.blog.oauth2.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @Description
 * @Author xbx
 * @Date 2022/8/29 13:24
 */
public class GiteeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object code;
    private Object state;


    public GiteeAuthenticationToken(Object code,Object state) {
        super((Collection)null);
        this.code = code;
        this.state = state;
        this.setAuthenticated(false);
    }

    public GiteeAuthenticationToken(Object code, Object openId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.code = code;

        this.state = openId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.state;
    }

    @Override
    public Object getPrincipal() {
        return this.code;
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this openId to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }


}
