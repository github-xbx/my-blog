package com.xingbingxuan.blog.oauth2.code;

import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;

import javax.sql.DataSource;

/**
 * @Description 自定义生成一个授权code 继承JdbcAuthorizationCodeServices code存储在数据库中
 *              继承InMemoryAuthorizationCodeServices code存储在内存中
 * @Author xbx
 * @Date 2022/8/31 13:21
 */

public class AuthorizationCodeServicesImpl extends JdbcAuthorizationCodeServices implements AuthorizationCodeServices {
    // 生成随机字符的类
    private RandomValueStringGenerator generator = new RandomValueStringGenerator(32);

    public AuthorizationCodeServicesImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = generator.generate();
        store(code, authentication);
        return code;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code)
            throws InvalidGrantException {
        OAuth2Authentication auth = this.remove(code);
        if (auth == null) {
            throw new InvalidGrantException("无效的授权代码: " + code);
        }
        return auth;
    }
}
