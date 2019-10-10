package com.vike.spider.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: lsl
 * @createDate: 2019/9/29
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception{
        auth.userDetailsService(customUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers( "/favicon.ico","/custom/**","/framework/**","/img/**","/client/login","/client/login-post").permitAll()
                // 任何尚未匹配的URL只需要验证用户即可访问
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/client/login").successForwardUrl("/stock/index").failureForwardUrl("/client/login")
                .and()
                //权限拒绝的页面
                .exceptionHandling().accessDeniedPage("/403")
                .and().csrf().disable();

        http.logout().logoutSuccessUrl("/client/login");
    }


    /**
     * 设置用户密码的加密方式
     * @return
     */
    @Bean
    public CustomPasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();

    }

    /**
     * 自定义UserDetailsService，授权
     * @return
     */
    @Bean
    public ClientLoginService customUserDetailsService(){
        return new ClientLoginService();
    }

    /**
     * AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
