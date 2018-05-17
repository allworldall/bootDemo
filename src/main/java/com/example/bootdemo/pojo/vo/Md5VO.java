package com.example.bootdemo.pojo.vo;

import com.alibaba.fastjson.JSONObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "md5VO")
public class Md5VO {
    private String original;
    private String lower;
    private String upper;

    public String getReponseJsonString(String param, String resultSign) {
        JSONObject jo = new JSONObject();
        jo.put("original", param);
        jo.put("lower", resultSign);
        jo.put("upper", resultSign.toUpperCase());
        return jo.toJSONString();
    }

    public Md5VO() {
    }

    public Md5VO(String original, String lower, String upper) {
        this.original = original;
        this.lower = lower;
        this.upper = upper;
    }
    @XmlElement(name = "Original")
    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
    @XmlElement(name = "Lower")
    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }
    @XmlElement(name = "Upper")
    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("original=").append(original);
        sb.append(",lower=").append(lower);
        sb.append(",upper=").append(upper);
        return sb.toString();
    }
}
