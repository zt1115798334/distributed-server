package com.example.distributedauthentication.service.impl;

import com.example.distributedauthentication.bo.ResponseUserToken;
import com.example.distributedauthentication.security.SecurityUser;
import com.example.distributedauthentication.service.LoginService;
import com.example.distributedauthentication.utils.JwtTokenUtil;
import com.example.distributedcommon.custom.SystemStatusCode;
import com.example.distributedcommon.exception.OperationException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final SecurityUser userDetail = (SecurityUser) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateAccessToken(userDetail.getId(), userDetail.getAccount());
        //存储token
//        jwtTokenUtil.putToken(username, token);
        return new ResponseUserToken(token, userDetail);
    }

    private Authentication authenticate(String username, String password) {
        try {
            // 该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到security 的 context中
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, username + password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new OperationException(SystemStatusCode.FAILED.getMsg());
        }
    }
}
