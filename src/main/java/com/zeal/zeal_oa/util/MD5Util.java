package com.zeal.zeal_oa.util;

import com.zeal.zeal_oa.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @version: java version 1.8
 * @author: zeal
 * @description:md5算法加密
 * @date: 2022-06-19 10:31
 */
public class MD5Util {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue+ Constant.SALT).getBytes()));
    }

    public static String getMD5Str2(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] mdb = md5.digest((strValue + Constant.SALT).getBytes());
        for (int i = 0; i < mdb.length; i++) {
            mdb[i]=(byte)(mdb[i]+Constant.sal);
        }
        return Base64.encodeBase64String(mdb);
    }

    public static void main(String[] args) {
            String str=null;
            String str2=null;
        try {
            str=getMD5Str("test");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        try {
            str2=getMD5Str2("test");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(str2);
    }

}
