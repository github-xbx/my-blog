package com.xingbingxuan.blog.authorize.service.impl;

import com.xingbingxuan.blog.authorize.service.TokenService;
import com.xingbingxuan.blog.authorize.token.BearerTokenExtractor;
import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.token.AccessTokenUtil;
import com.xingbingxuan.blog.token.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : xbx
 * @date : 2022/9/25 10:11
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private static final String TokenType = "bearer";

    @Override
    public AccessToken createAccessToken(Authentication authentication) {

        String token = AccessTokenUtil.getToken();
        AccessToken accessToken = new AccessToken();
        accessToken.setTokenType(TokenType);
        accessToken.setScope(authentication.getScope());
        accessToken.setToken(token);
        AccessToken isAccessToken = AccessTokenUtil.getAccessToken(authentication.getUserId(), authentication.getUsername());

        if (isAccessToken == null){
            try {
                AccessTokenUtil.storeAccessToken(accessToken,authentication);
            }catch (Exception e){
                log.error(e.getMessage());
                return null;
            }
        }else {
            //先删除在创建
            try {
                AccessTokenUtil.removeAccessToken(isAccessToken,authentication);

                AccessTokenUtil.storeAccessToken(accessToken,authentication);
            }catch (Exception e){
                log.error(e.getMessage());
                return null;
            }
        }


        return accessToken;
    }

    @Override
    public Boolean removeAccessToken(HttpServletRequest request) {

        Authentication authentication = new BearerTokenExtractor().extract(request);

        AccessToken accessToken = AccessTokenUtil.getAccessToken(authentication.getUserId(), authentication.getUsername());
        try {
            AccessTokenUtil.removeAccessToken(accessToken,authentication);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public Authentication loadAuthentication(HttpServletRequest request) {

        Authentication authentication = new BearerTokenExtractor().extract(request);

        return authentication;
    }


}
