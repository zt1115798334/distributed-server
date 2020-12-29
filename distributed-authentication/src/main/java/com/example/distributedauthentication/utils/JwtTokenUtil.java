package com.example.distributedauthentication.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.distributedauthentication.properties.JwtProperties;
import com.example.distributedauthentication.security.SecurityUser;
import com.example.distributedcommon.utils.DateUtils;
import com.example.distributedcommondatasource.utils.UniqueIdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/12/18 17:25
 * description: jwt 工具类
 */
@AllArgsConstructor
@Component
public class JwtTokenUtil {
    private static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";
    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";
    private static final String CLAIM_KEY_ACCOUNT_ENABLED = "enabled";
    private static final String CLAIM_KEY_ACCOUNT_NON_LOCKED = "non_locked";
    private static final String CLAIM_KEY_ACCOUNT_NON_EXPIRED = "non_expired";

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private final JwtProperties jwtProperties;

    /**
     * 根据token 获取用户信息
     * @param token
     * @return
     */
    public SecurityUser getUserFromToken(String token) {
        SecurityUser userDetail;
        try {
            final Claims claims = getClaimsFromToken(token);
            Long userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            userDetail = new SecurityUser(userId, username,  "");
        } catch (Exception e) {
            userDetail = null;
        }
        return userDetail;
    }
    /**
     * 根据token获取用户id
     *
     * @param token token
     * @return Long
     */
    public Long getUserIdFromToken(String token) {
        Long userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Long.valueOf(claims.get(CLAIM_KEY_USER_ID).toString());
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * 根据token获取用户名称
     *
     * @param token token
     * @return string
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }


    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 过期时间 单位 小时
     *
     * @param expiration 小时
     * @return date
     */
    private Date generateExpirationDate(long expiration, String timeUnit) {
        return DateUtils.localDateTimeToDate(DateUtils.currentDateTimeAddTime(expiration, timeUnit));
    }

    /**
     * 过期时间 单位 天数
     *
     * @param expiration 天数
     * @return date
     */
    private Date generateRememberMeExpirationDate(long expiration, String timeUnit) {
        return DateUtils.localDateTimeToDate(DateUtils.currentDateTimeAddTime(expiration, timeUnit));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String generateAccessToken(String subject, Map<String, Object> claims, Boolean rememberMe) {
        Date expirationDate;
        if (rememberMe) {
            expirationDate = generateRememberMeExpirationDate(jwtProperties.getRememberMeExpiration(),
                    jwtProperties.getRememberMeExpirationUnit());
        } else {
            expirationDate = generateExpirationDate(jwtProperties.getRememberMeExpiration(), jwtProperties.getRememberMeExpirationUnit());
        }
        return generateToken(subject, claims, expirationDate);
    }

    public String generateRefreshToken(Long userIdParam, String accountParam) {
        return generateRefreshToken(userIdParam, accountParam, false);
    }

    public String generateRefreshToken(Long userIdParam, String accountParam, Boolean rememberMe) {
        Map<String, Object> claims = generateClaims(userIdParam);
        // 只授于更新 token 的权限
        String[] roles = new String[]{ROLE_REFRESH_TOKEN};
        claims.put(CLAIM_KEY_AUTHORITIES, JSONObject.parse(JSON.toJSONString(roles)));
        return generateRefreshToken(accountParam, claims, rememberMe);
    }

    public String generateAccessToken(Long userIdParam, String accountParam) {
        Map<String, Object> claims = generateClaims(userIdParam);
        return generateAccessToken(accountParam, claims, false);
    }

    public String generateAccessToken(Long userIdParam, String accountParam, Boolean rememberMe) {
        Map<String, Object> claims = generateClaims(userIdParam);
        return generateAccessToken(accountParam, claims, rememberMe);
    }

    private Map<String, Object> generateClaims(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ID, userId);
        claims.put(CLAIM_KEY_ACCOUNT_NON_LOCKED, false);
        claims.put(CLAIM_KEY_ACCOUNT_NON_EXPIRED, false);
        return claims;
    }

    private String generateRefreshToken(String subject, Map<String, Object> claims, Boolean rememberMe) {
        Date expirationDate;
        if (rememberMe) {
            expirationDate = generateRememberMeExpirationDate(jwtProperties.getRememberMeRefreshExpiration(),
                    jwtProperties.getRememberMeRefreshExpirationUnit());
        } else {
            expirationDate = generateExpirationDate(jwtProperties.getRememberMeRefreshExpiration(),
                    jwtProperties.getRememberMeRefreshExpirationUnit());
        }
        return generateToken(subject, claims, expirationDate);
    }

    private String generateToken(String subject, Map<String, Object> claims, Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UniqueIdUtils.getUUID())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SIGNATURE_ALGORITHM, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 检验token是否正确
     *
     * @param token        token
     * @param userIdParam  用户id
     * @param accountParam 用户账户
     * @return boolean
     */
    public Boolean validateToken(String token, Long userIdParam, String accountParam) {
        final Long userId = getUserIdFromToken(token);
        final String userName = getUsernameFromToken(token);
        return (Objects.equals(userId, userIdParam))
                && (Objects.equals(userName, accountParam))
                && !isTokenExpired(token);
    }
}
