package com.example.bootdemo.utils.sign;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * 此类用于MD5加密解密
 */
public class MD5Util {
    /**
     * md5加密认证
     * @param String str
     *               需要加密的内容
     * @return
     * 				 返回md5加密后的内容
     */
    public static String getMD5(String str){
        return DigestUtils.md5Hex(str);
    }

    public static void main(String[] args) {
        System.out.println(getMD5("156113134276480301234"));
    }
}
