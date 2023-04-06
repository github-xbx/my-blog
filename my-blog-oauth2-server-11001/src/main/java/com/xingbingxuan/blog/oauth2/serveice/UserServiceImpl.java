package com.xingbingxuan.blog.oauth2.serveice;

import com.xingbingxuan.blog.oauth2.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : xbx
 * @date : 2023/4/2 11:17
 */
@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(passwordEncoder.encode("1111"));
        sysUser.setEnabled(true);
        sysUser.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_BLOG_USER")));

        User user = new User(username, passwordEncoder.encode("1111"), Arrays.asList(new SimpleGrantedAuthority("ROLE_BLOG_USER")));


        return user;

    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encode = passwordEncoder.encode("00000000");
        System.out.println(encode);

        boolean matches = passwordEncoder.matches("00000000",
                "$2a$10$3JG.CtZxweGHiNaqZE/LD.qXS6vrM4tsm9x9q.z6bLCNTSOR9nlDu");

        System.out.println(matches);
    }
}
