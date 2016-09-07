package com.zhp.framwork.utils;


import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 授权相关工具类
 * Created by Zhp on 2014/5/21.
 */
public class AuthUtils {

    private static char[] hexDigits = "0123456789abcdef".toCharArray();

    public static String md5(String input) {
        String result = input;
        if (input != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(input.getBytes());
                result = getString(md.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String sha1(byte[] input) {
        String result = null;
        if (input != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(input);
                result = getString(md.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String HmacSha1(String key, String input) {
        String result = null;
        Mac mac;
        String enc;
        try {
            mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(input.getBytes("UTF-8"));
            result = getString(digest);
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @NonNull
    private static String getString(byte[] digest) {
        StringBuilder sb = new StringBuilder(32);
        for (byte b : digest) {
            sb.append(hexDigits[(b >> 4) & 0x0f]);
            sb.append(hexDigits[b & 0x0f]);
        }
        return sb.toString();
    }

    public static String md5Sign(long timestamp, String app_secret, String access_secret) {
        String text = timestamp + app_secret + access_secret;
        return md5(text);
    }

    public static String HmacSHA1Sign(String key, String token, String secret) {
        String content = token + secret;
        return HmacSha1(key, content);
    }

    public static void main(String[] args) {
//        System.out.print(md5(1464000194, "761ef88d13b47d02212565d0dcf86616", "b0edde212ac40f9b0c5b03739652d920"));
    }
}
