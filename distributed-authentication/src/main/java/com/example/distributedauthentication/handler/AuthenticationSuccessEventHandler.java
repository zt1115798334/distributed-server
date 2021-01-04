package com.example.distributedauthentication.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/4 16:19
 * description: 登录成功映射类
 */
@Slf4j
@Component
public class AuthenticationSuccessEventHandler  implements ApplicationListener<AuthenticationSuccessEvent> {
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (Objects.nonNull(authentication.getAuthorities())) {
            log.info("用户：{} 登录成功", authentication.getPrincipal());
        }

    }
}
