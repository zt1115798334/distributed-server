package com.example.distributedauthentication.service.impl;

import com.example.distributedauthentication.bo.ResponseUserToken;
import com.example.distributedauthentication.security.SecurityUser;
import com.example.distributedauthentication.service.LoginService;
import com.example.distributedauthentication.utils.JwtTokenUtil;
import com.example.distributedcommon.custom.SystemStatusCode;
import com.example.distributedcommon.exception.OperationException;
import com.example.distributedcommoncache.custom.CacheKeys;
import com.example.distributedcommoncache.service.RedisDatabase;
import com.example.distributedcommoncache.service.StringRedisService;
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

    private final StringRedisService stringRedisService;

    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final SecurityUser userDetail = (SecurityUser) authentication.getPrincipal();
        final Long userId = userDetail.getId();
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetail.getId(), userDetail.getAccount());
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetail.getId(), userDetail.getAccount());

        //token 存储redis
        stringRedisService.setContainExpire(RedisDatabase.REDIS_ZERO, CacheKeys.getJwtAccessTokenKey(userId), accessToken);
        stringRedisService.setContainExpire(RedisDatabase.REDIS_ZERO, CacheKeys.getJwtRefreshTokenKey(userId), refreshToken);
        //存储token
        return new ResponseUserToken(accessToken, userDetail);
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
