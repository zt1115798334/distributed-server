package com.example.distributedcommoncache.custom;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/12/12 15:30
 * description:
 */
public class CacheKeys {

    /**
     * 用户登录次数计数
     */
    private static final String PREFIX_SECURITY_LOGIN_COUNT = "security:login_count_";

    /**
     * 用户登录是否被锁定
     */
    private static final String PREFIX_SECURITY_IS_LOCK = "security:is_lock_";

    /**
     * shiro 缓存
     */
    private static final String PREFIX_SECURITY_CACHE = "security:cache:";

    /**
     * jwt验证token
     */
    private static final String PREFIX_JWT_ACCESS_TOKEN = "jwt:access_token:";

    /**
     * jwt刷新token
     */
    private static final String PREFIX_JWT_REFRESH_TOKEN = "jwt:refresh_token:";




    public static String getSecurityLoginCountKey(String account) {
        return PREFIX_SECURITY_LOGIN_COUNT + account;
    }

    public static String getSecurityIsLockKey(String account) {
        return PREFIX_SECURITY_IS_LOCK + account;
    }

    public static String getSecurityCacheKey(Long key) {
        return PREFIX_SECURITY_CACHE + ":" + key;
    }

    public static String getJwtAccessTokenKey( Long userId) {
        return PREFIX_JWT_ACCESS_TOKEN + userId ;
    }

    public static String getJwtRefreshTokenKey( Long userId) {
        return PREFIX_JWT_REFRESH_TOKEN +  userId;
    }
}
