package com.xingbingxuan.blog.gateway.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 *  认证管理的作用就是获取传递过来的令牌，对其进行解析、验签、过期时间判定。
 * @author : xbx
 * @date : 2023/4/5 10:35
 */
@Slf4j
@Component
public class TokenReactiveAuthenticationManager implements ReactiveAuthenticationManager {


    @Autowired
    private TokenStore tokenStore;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        Mono<Authentication> mono = Mono.justOrEmpty(authentication).filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class).map(BearerTokenAuthenticationToken::getToken)
                .flatMap(accessToken -> {
                    log.info("accessToken is :{}", accessToken);
                    OAuth2AccessToken oAuth2AccessToken = this.tokenStore.readAccessToken(accessToken);
                    //根据access_token从数据库获取不到OAuth2AccessToken
                    if (oAuth2AccessToken == null) {
                        return Mono.error(new InvalidTokenException("无效的token"));
                    } else if (oAuth2AccessToken.isExpired()) {
                        return Mono.error(new InvalidTokenException("token已过期"));
                    }

                    OAuth2Authentication oAuth2Authentication = this.tokenStore.readAuthentication(accessToken);
                    if (oAuth2Authentication == null) {
                        return Mono.error(new InvalidTokenException("无效的token!"));
                    } else {
                        return Mono.just(oAuth2Authentication);
                    }

                }).cast(Authentication.class);

        return mono;
    }
}
