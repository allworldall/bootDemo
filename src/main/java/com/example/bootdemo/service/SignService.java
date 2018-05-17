package com.example.bootdemo.service;

import com.example.bootdemo.utils.sign.MD5Util;
import com.example.bootdemo.utils.sign.RSAUtil;
import org.springframework.stereotype.Service;

@Service
public class SignService {

    /**
     * MD5加密
     * @param param     待加密串
     * @return
     */
    public String getMd5(String param){
        return MD5Util.getMD5(param);
    }

    /**
     * 获取一对RSA公私钥
     * @return
     */
    public String getPubAndPriKey() throws Exception {
        return RSAUtil.getPubAndPriKey();
    }
}
