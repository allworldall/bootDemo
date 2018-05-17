package com.example.bootdemo.pojo.dto;

import com.example.bootdemo.utils.annotation.DataValidate;
import com.example.bootdemo.utils.annotation.RegexType;

public class AccountDTO {
    @DataValidate(description = "微信用户唯一标识openId", nullable = false, maxLength = 30)
    private String openId;

    @DataValidate(description = "应用Id", nullable = false)
    private String appId;

    @DataValidate(description = "用户姓名", nullable = false, maxLength = 30)
    private String fullName;

    @DataValidate(description = "性别,1:男  0:女", nullable = false, length = 1)
    private String sex;

    @DataValidate(description = "手机号", nullable = false, regexType = RegexType.PHONENUMBER)
    private String telephoneNum;

    @DataValidate(description = "签名", nullable = false)
    private String sign;


    public AccountDTO() {
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
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
        sb.append(",fullName=").append(fullName);
        sb.append(",sex=").append(sex);
        sb.append(",telephoneNum=").append(telephoneNum);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
