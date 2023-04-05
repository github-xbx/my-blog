package com.xingbingxuan.blog.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingbingxuan.blog.oauth2.filter.GiteeLoginAuthenticationFilter;
import com.xingbingxuan.blog.oauth2.filter.UserPswLoginAuthenticationFilter;
import com.xingbingxuan.blog.oauth2.provider.GiteeAuthenticationProvider;
import com.xingbingxuan.blog.oauth2.provider.UsernamePasswordAuthenticationProvider;
import com.xingbingxuan.blog.oauth2.uitls.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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


//    @Autowired
//    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
//    @Autowired
//    private GiteeAuthenticationProvider giteeAuthenticationProvider;
    @Autowired
    private ApplicationProperties properties;


    /**
     * 让Security 忽略这些url，不做拦截处理 application.yaml 中配置
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

        //auth.authenticationProvider(usernamePasswordAuthenticationProvider);
        //auth.authenticationProvider(giteeAuthenticationProvider);
    }


    /**
     * 用户名和密码登录过滤
     * @Author xbx
     **/
    //@Bean
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
    //@Bean
     GiteeLoginAuthenticationFilter giteeLoginAuthenticationFilter() throws Exception {
        GiteeLoginAuthenticationFilter giteeLoginAuthenticationFilter = new GiteeLoginAuthenticationFilter();
        giteeLoginAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        giteeLoginAuthenticationFilter.setFilterProcessesUrl("/giteeLogin");
        return giteeLoginAuthenticationFilter;
    }

//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    //
    // http://localhost:11001/oauth/authorize?client_id=BLOGUSER&response_type=code&scope=all&redirect_uri=http://www.baidu.com
    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
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
                .csrf().disable().exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable();

       // http.addFilterAt(userPswLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
      //  http.addFilterAt(giteeLoginAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);



    }



}
