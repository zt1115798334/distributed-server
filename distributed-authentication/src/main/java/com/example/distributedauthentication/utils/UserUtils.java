package com.example.distributedauthentication.utils;

import com.example.distributedcommon.utils.Digests;
import com.example.distributedcommon.utils.Encodes;


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
     * @param salt     盐
     * @return String
     */
    public static String getEncryptPassword(String account, String password, String salt) {
        byte[] hashPassword = Digests.sha1((account + password).getBytes(), Encodes.decodeHex(salt), Digests.HASH_INTERACTIONS);
        return Encodes.encodeHex(hashPassword);
    }

}
