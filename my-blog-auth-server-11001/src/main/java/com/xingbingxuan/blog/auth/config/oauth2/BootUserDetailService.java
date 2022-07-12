package com.xingbingxuan.blog.auth.config.oauth2;

import ch.qos.logback.classic.Logger;
import com.xingbingxuan.blog.auth.feign.AccountFeignService;
import com.xingbingxuan.blog.auth.feign.Oauth2UserFeignService;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.UserVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootUserDetailService implements UserDetailsService {

    @Autowired
    private Oauth2UserFeignService oauth2UserFeignService;

    private Logger logger = (Logger) LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        Result<UserVo> oauthLogin = oauth2UserFeignService.userLogin(username);
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
