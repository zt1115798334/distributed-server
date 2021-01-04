package com.example.distributedauthentication.handler;

import com.example.distributedauthentication.utils.ResultUtil;
import com.example.distributedcommon.custom.SystemStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/4 16:17
 * description:
 */
@Slf4j
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    /**
     * token错误时进入到这里
     *
     * @param httpServletRequest httpServletRequest
     * @param httpServletResponse httpServletResponse
     * @param authException authException
     */

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
        StringBuilder msg = new StringBuilder("请求: ");
        msg.append(httpServletRequest.getRequestURI()).append(authException.getMessage());
        log.info(msg.toString());
        ResultUtil.writeJavaScript(httpServletResponse,HttpServletResponse.SC_UNAUTHORIZED, SystemStatusCode.SC_UNAUTHORIZED, msg.toString());

    }
}
