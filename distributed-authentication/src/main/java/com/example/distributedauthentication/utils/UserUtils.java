package com.example.distributedauthentication.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/9/25 14:51
 * description:
 */
public class UserUtils {
    /**
     * 获取加密密码
     *
     * @param account  账户
     * @param password 未加密密码
     * @return String
     */
    public static String getEncryptPassword(String account, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        return encoder.encode(account + password);
    }

}
