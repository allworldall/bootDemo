package com.example.bootdemo.pojo.dto;

import com.example.bootdemo.utils.annotation.DataValidate;

public class QueryAccountDTO {
    @DataValidate(description = "用户唯一标识", nullable = false, maxLength = 30)
    private String openId;

    @DataValidate(description = "应用Id", nullable = false)
    private String appId;

    @DataValidate(description = "参数签名", nullable = false)
    private String sign;
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("openId=").append(openId);
        sb.append(",appId=").append(appId);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
