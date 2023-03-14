package com.pcy.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by lanxw
 * 登录加密算法
 */
public class MD5Util {

    private static final String DEFAULT_SALT = "1a2b3c4d5e";

    public static String encode(String password, String salt) {
        return DigestUtils.md5Hex("" + salt.charAt(0) + salt.charAt(2) + password + salt.charAt(4) + salt.charAt(5));
    }

    public static String encodeWithDefaultSalt(String password) {
        String salt = DEFAULT_SALT;
        return DigestUtils.md5Hex("" + salt.charAt(0) + salt.charAt(2) + password + salt.charAt(4) + salt.charAt(5));
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("123456789", "1a2b3c4d5e"));
    }
}
