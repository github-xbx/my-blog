package com.xingbingxuan.blog.authorize.service.impl;

import com.xingbingxuan.blog.authorize.service.TokenService;
import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.token.AccessTokenUtil;
import com.xingbingxuan.blog.token.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void removeAccessToken(Authentication authentication) {

        AccessToken accessToken = AccessTokenUtil.getAccessToken(authentication.getUserId(), authentication.getUsername());

        AccessTokenUtil.removeAccessToken(accessToken,authentication);

    }

    @Override
    public Authentication loadAuthentication(String accessTokenValue) {

        Authentication authentication = AccessTokenUtil.getAuthentication(accessTokenValue);

        return authentication;
    }


}
