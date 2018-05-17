package com.example.bootdemo.utils.function;


import com.example.bootdemo.utils.DataFormatUtils.DateUtil;
import com.example.bootdemo.utils.DataFormatUtils.StringUtils;
import com.example.bootdemo.utils.cache.GuavaUtil;
import com.example.bootdemo.utils.constant.SysCodeConstant;
import com.example.bootdemo.utils.constant.SysStringConstant;
import com.example.bootdemo.utils.http.HttpUtils;
import com.example.bootdemo.utils.sign.MD5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class SendShortMessage {

    /**
     * 发送短信验证码方法
     * @param String phoneNum
     *               手机号
     * @param String phoneCode
     *               验证码
     * @return int
     */
    public static int sendMessage(String phoneNum,String phoneCode) throws UnsupportedEncodingException {
        String url = GuavaUtil.getConfigValue(SysStringConstant.SEND_MESSAGE_URL);
        String password = GuavaUtil.getConfigValue(SysStringConstant.SEND_MESSAGE_PASSWORD); //用户密码
        String send = DateUtil.getTimeString(new Date());
        String account = GuavaUtil.getConfigValue(SysStringConstant.SEND_MESSAGE_ACOUNT);
        String sign = GuavaUtil.getConfigValue(SysStringConstant.SEND_MESSAGE_SIGN_KEY);
        String content = GuavaUtil.getConfigValue(SysStringConstant.SEND_MESSAGE_CONTENT);
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(password) || StringUtils.isEmpty(send)
                || StringUtils.isEmpty(account) || StringUtils.isEmpty(sign) || StringUtils.isEmpty(content)) {
            return SysCodeConstant.ERROR_PROPERTY_CONFIG;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("name=").append(URLEncoder.encode(account, "GBK")).append("&");
        buffer.append("seed=").append(URLEncoder.encode(send, "GBK")).append("&");
        buffer.append("key=").append(URLEncoder.encode(MD5Util.getMD5(MD5Util.getMD5(password) + send), "GBK")).append("&");
        buffer.append("dest=").append(URLEncoder.encode(phoneNum, "GBK")).append("&");
        buffer.append("content=").append(URLEncoder.encode( sign+ content.replace("xxxxxx", phoneCode), "GBK"));
        //此处单独对发送验证码做一个异常捕捉，方便查问题
        String sendResult = "";
        try {
            sendResult = HttpUtils.sendGet(url, buffer.toString());
        } catch (Exception e) {
            return SysCodeConstant.HTTP_EXCEPION;
        }
        if (sendResult.contains(SysStringConstant.DEL_SEND_MESSAGE_SUCCESS)) {
            return SysCodeConstant.DEAL_SUCCESS;
        } else {
            return SysCodeConstant.ERROR_SEND_PHONE_MESSAGE;
        }
    }
}
