package com.example.bootdemo.pojo.po;


public class SysAccountPO {
    private String openId;

    private String appId;

    private String fullName;

    private int sex;

    private String telephoneNum;

    private int level;

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public SysAccountPO() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("openId=").append(openId);
        sb.append(",appId=").append(appId);
        sb.append(",fullName=").append(fullName);
        sb.append(",sex=").append(sex);
        sb.append(",telephoneNum=").append(telephoneNum);
        sb.append(",level=").append(level);
        return sb.toString();
    }
}
