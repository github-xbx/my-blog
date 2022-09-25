package com.xingbingxuan.blog.oauth2.provider;


import com.xingbingxuan.blog.oauth2.feign.AccountFeignService;
import com.xingbingxuan.blog.oauth2.serveice.GiteeClientService;
import com.xingbingxuan.blog.oauth2.token.GiteeAuthenticationToken;
import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 自定义第三方（gitee）登录 AuthenticationProvider
 *
 * @author xbx
 * @version 1.0
 * @date 2022/9/3 19:02
 */

@Component
public class GiteeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private GiteeClientService giteeClientService;

    @Autowired
    private AccountFeignService accountFeignService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //用户的权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        //获取code 和 status并在gitee的服务器验证登录
        String code = authentication.getName();
        String  state = String.valueOf(authentication.getCredentials());
        Map<String, String> map = giteeClientService.queryAccessToken(code,state);

        //根据第三方的类型和uid在用户表中查询是否有该用户，没有就添加一个
        UserParam userParam = new UserParam();
        userParam.setSocialType(map.get("socialType"));
        userParam.setSocialUid(map.get("socialUid"));

        //账户服务，获取关联的用户信息
        UserAllInfoDto userInfo = accountFeignService.thirdPartyLogin(userParam);

        //封装用户的权限信息
        userInfo.getRoleVos().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
        });

        return new UsernamePasswordAuthenticationToken(userInfo.getUsername(), null, authorities);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        /**
         * providerManager会遍历所有
         * SecurityConfig中注册的provider集合
         * 根据此方法返回true或false来决定由哪个provider
         * 去校验请求过来的authentication
         */
        return (GiteeAuthenticationToken.class.isAssignableFrom(aClass));

    }


}
