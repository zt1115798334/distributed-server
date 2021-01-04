package com.example.distributedauthentication.security;

import com.example.distributedauthentication.utils.ResultUtil;
import com.example.distributedcommon.custom.SystemStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        StringBuilder msg = new StringBuilder("请求访问: ");
        msg.append(httpServletRequest.getRequestURI()).append(" 接口， 经jwt 认证失败，无法访问系统资源.");
        log.info(msg.toString());
        log.info(e.toString());
        // 用户登录时身份认证未通过
        if (e instanceof BadCredentialsException) {
            log.info("用户登录时身份认证失败.");
            ResultUtil.writeJavaScript(httpServletResponse,HttpServletResponse.SC_OK, SystemStatusCode.SC_UNAUTHORIZED, msg.toString());
        } else if (e instanceof InsufficientAuthenticationException) {
            log.info("缺少请求头参数,Authorization传递是token值所以参数是必须的.");
            ResultUtil.writeJavaScript(httpServletResponse,HttpServletResponse.SC_OK, SystemStatusCode.JWT_NOT_FOUND, msg.toString());
        } else {
            log.info("用户token无效.");
            ResultUtil.writeJavaScript(httpServletResponse,HttpServletResponse.SC_OK, SystemStatusCode.JWT_ERROR, msg.toString());
        }
    }
}
