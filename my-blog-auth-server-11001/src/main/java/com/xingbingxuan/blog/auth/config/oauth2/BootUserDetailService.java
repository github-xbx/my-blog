package com.xingbingxuan.blog.auth.config.oauth2;

import com.xingbingxuan.blog.auth.feign.AccountFeignService;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BootUserDetailService implements UserDetailsService {

    @Autowired
    private AccountFeignService accountFeignService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        Result<UserVo> oauthLogin = accountFeignService.userLogin(username);
        if (oauthLogin.getCode() != 200){
            throw new UsernameNotFoundException("登录错误，重新登录。。。");
        }

        if(oauthLogin.getObject()==null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        User user = new User(username, oauthLogin.getObject().getPassword(),authorities);

        return user;
    }

}
