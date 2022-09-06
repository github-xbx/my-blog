package com.xingbingxuan.blog.auth.provider;

import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.auth.feign.AccountFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自定义用户名密码方式登录AuthenticationProvider
 *
 * @author xbx
 * @version 1.0
 * @date 2022/9/3 19:00
 */
@Slf4j
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountFeignService accountFeignService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //用户输入的用户名和密码
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();


        //用户的权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();


        //调用账户服务查询用户信息
        UserAllInfoDto userAllInfoDto = accountFeignService.userLogin(userName);

        if (userAllInfoDto == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(password,userAllInfoDto.getPassword())){
            throw new BadCredentialsException("用户名或密码错误");
        }
        //封装用户的权限信息
        userAllInfoDto.getRoleVos().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
        });


        //安全起见密码不要返回
        return new UsernamePasswordAuthenticationToken(userName, null, authorities);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        /**
         * providerManager会遍历所有
         * SecurityConfig中注册的provider集合
         * 根据此方法返回true或false来决定由哪个provider
         * 去校验请求过来的authentication
         */
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));

    }
}

