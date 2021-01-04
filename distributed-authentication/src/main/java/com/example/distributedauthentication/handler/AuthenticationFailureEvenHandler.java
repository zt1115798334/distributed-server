package com.example.distributedauthentication.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/4 16:18
 * description: 登录失败映射类
 */
@Slf4j
@Component
public class AuthenticationFailureEvenHandler implements ApplicationListener<AbstractAuthenticationFailureEvent> {
    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        // 登录的authentication 对象
        AuthenticationException authenticationException = event.getException();
        // 登录的authenticationException 对象
        Authentication authentication = (Authentication) event.getSource();

        log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
    }
}
