package com.example.distributedauthentication.security.config;

import com.example.distributedauthentication.entity.User;
import com.example.distributedauthentication.security.JwtAuthenticationEntryPoint;
import com.example.distributedauthentication.security.JwtAuthenticationTokenFilter;
import com.example.distributedauthentication.security.RestAuthenticationAccessDeniedHandler;
import com.example.distributedauthentication.security.SecurityUser;
import com.example.distributedauthentication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity //启动web安全性
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    private final JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler,
                             JwtAuthenticationTokenFilter authenticationTokenFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.restAuthenticationAccessDeniedHandler = restAuthenticationAccessDeniedHandler;
        this.authenticationTokenFilter = authenticationTokenFilter;
    }


    /**
     * 配置策略
     *
     * @param httpSecurity httpSecurity
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 权限不足处理类
                .exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler).and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 对于登录login要允许匿名访问
                .antMatchers("/api/**", "/favicon.ico").permitAll()
                // 访问/user 需要拥有admin权限
//                .antMatchers("/user").hasAuthority("admin")
                .and()
                .authorizeRequests()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 设置UserDetailsService
                .userDetailsService(userDetailsService())
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    /**
     * 装载BCrypt密码编码器 密码加密
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登陆身份认证
     *
     * @return UserDetailsService
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Autowired
            private UserService userService;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                log.info("登录用户：" + username);
                User user = userService.findUserByAccount(username);
                if (user == null) {
                    log.info("登录用户：" + username + " 不存在.");
                    throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
                }
                //获取用户拥有的角色
                return new SecurityUser(user.getId(), username, user.getPassword());
            }
        };
    }
}
