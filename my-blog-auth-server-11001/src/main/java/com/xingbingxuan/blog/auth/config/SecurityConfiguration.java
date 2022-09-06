package com.xingbingxuan.blog.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingbingxuan.blog.auth.filter.GiteeLoginAuthenticationFilter;
import com.xingbingxuan.blog.auth.filter.UserPswLoginAuthenticationFilter;
import com.xingbingxuan.blog.auth.provider.GiteeAuthenticationProvider;
import com.xingbingxuan.blog.auth.provider.UsernamePasswordAuthenticationProvider;
import com.xingbingxuan.blog.auth.uitls.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * spring security 配置
 *
 * @author xbx
 * @version 1.0
 * @date 2022/9/3 22:25
 */
@Configuration
@Order(1)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    @Autowired
    private GiteeAuthenticationProvider giteeAuthenticationProvider;
    @Autowired
    private ApplicationProperties properties;


    /**
     * 让Security 忽略这些url，不做拦截处理
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers(properties.getSecurity().getExcludeUrls().toArray(new String[]{}));
        /*web.ignoring().antMatchers
                ("/giteeurl","/static/**","/css/**,/images/**","/assets/**",
                        "/loginHtml");*/


    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
        auth.authenticationProvider(giteeAuthenticationProvider);
    }


    /**
     * 用户名和密码登录过滤
     * @Author xbx
     **/
    @Bean
     UserPswLoginAuthenticationFilter userPswLoginAuthenticationFilter() throws Exception {
        UserPswLoginAuthenticationFilter userPswLoginAuthenticationFilter = new UserPswLoginAuthenticationFilter();
        userPswLoginAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        userPswLoginAuthenticationFilter.setFilterProcessesUrl("/vueLogin");
        return userPswLoginAuthenticationFilter;
    }
    /**
     * gitee 登录过滤
     * @Author xbx
     **/
    @Bean
     GiteeLoginAuthenticationFilter giteeLoginAuthenticationFilter() throws Exception {
        GiteeLoginAuthenticationFilter giteeLoginAuthenticationFilter = new GiteeLoginAuthenticationFilter();
        giteeLoginAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        giteeLoginAuthenticationFilter.setFilterProcessesUrl("/giteeLogin");
        return giteeLoginAuthenticationFilter;
    }



    //
    // http://localhost:11001/oauth/authorize?client_id=BLOGUSER&response_type=code&scope=all&redirect_uri=http://www.baidu.com
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //.loginProcessingUrl("vueLogin")
                //自定义登录页登录
                .loginPage("/loginHtml").loginProcessingUrl("/vueLogin")
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessHandler((req, resp, authentication) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write(new ObjectMapper().writeValueAsString("注销成功!"));
                            out.flush();
                            out.close();
                        }
                )
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling();

        http.addFilterAt(userPswLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(giteeLoginAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);



    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
