package com.example.distributedauthentication.handler;

import com.example.distributedauthentication.utils.ResultUtil;
import com.example.distributedcommon.custom.SystemStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
 * date: 2021/1/4 16:23
 * description: 无权限
 */
@Slf4j
@Component("customAccessDeniedHandler")
public class CustomAccessDeniedHandler  implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException accessDeniedException) throws IOException {
        StringBuilder msg = new StringBuilder("请求: ");
        msg.append(httpServletRequest.getRequestURI()).append(accessDeniedException.getMessage());
        log.info(msg.toString());
        ResultUtil.writeJavaScript(httpServletResponse,HttpServletResponse.SC_UNAUTHORIZED, SystemStatusCode.SC_UNAUTHORIZED, msg.toString());
    }
}
