package com.example.distributedauthentication.security;

import com.example.distributedauthentication.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private String tokenHeader = "Authorization";

    private String authTokenStart = "Bearer";

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authToken = httpServletRequest.getHeader(this.tokenHeader);
        System.out.println(authToken);
        if (StringUtils.isNotEmpty(authToken) && authToken.startsWith(authTokenStart)) {
            authToken = authToken.substring(authTokenStart.length());
            log.info("请求" + httpServletRequest.getRequestURI() + "携带的token值：" + authToken);
            //如果在token过期之前触发接口,我们更新token过期时间，token值不变只更新过期时间
            //获取token生成时间
            Date createTokenDate = jwtTokenUtil.getExpirationDateFromToken(authToken);
            log.info("createTokenDate: " + createTokenDate);

        } else {
            // 不按规范,不允许通过验证
            authToken = null;
        }
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        Long userId = jwtTokenUtil.getUserIdFromToken(authToken);
        log.info("JwtAuthenticationTokenFilter[doFilterInternal] checking authentication " + username);

        if (StringUtils.isNotEmpty(authToken) && jwtTokenUtil.validateToken(authToken, userId, username) && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityUser userDetail = jwtTokenUtil.getUserFromToken(authToken);
            if (jwtTokenUtil.validateToken(authToken, userId, username)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                log.info(String.format("Authenticated userDetail %s, setting security context", username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
