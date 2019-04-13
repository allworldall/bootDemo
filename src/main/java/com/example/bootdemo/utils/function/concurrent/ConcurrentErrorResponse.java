package com.example.bootdemo.utils.function.concurrent;

/**
 * @Description 拦截器返回false时的响应内容设置
 * @Author panpan
 * @Date 2018/8/16 16:15
 * @Vrerison 1.0
 **/
public class ConcurrentErrorResponse {

    private boolean access;           //是否允许访问

    private String responseContent = "success";   //统一的响应格式,目前只对禁止访问添加格式，成功为success


    //提供出来
    public ConcurrentErrorResponse() {
    }

    public ConcurrentErrorResponse(boolean access) {
        this.access = access;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public void setResponseContent(int timer) {
        if (!access)
            this.responseContent = "访问太频繁，请稍后";
    }
}
