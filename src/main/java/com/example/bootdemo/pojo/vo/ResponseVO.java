package com.example.bootdemo.pojo.vo;

import com.example.bootdemo.pojo.po.SysAccountPO;
import com.example.bootdemo.utils.constant.SysCodeConstant;
import com.example.bootdemo.utils.constant.SysStringConstant;

public class ResponseVO<T>{

    private int status;

    private String msg;

    private T data;

    private ResponseVO() {
    }

    private ResponseVO(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseVO(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    //下方静态方法对外提供故事里创建
    public static ResponseVO createbySuccess() {
        return new ResponseVO(SysCodeConstant.DEAL_SUCCESS, SysStringConstant.DEL_SEND_MESSAGE_SUCCESS);
    }

    public static <T> ResponseVO<T> createbySuccess(T data) {
        return new ResponseVO(SysCodeConstant.DEAL_SUCCESS, SysStringConstant.DEL_SEND_MESSAGE_SUCCESS, data);
    }

    public static ResponseVO createbyFail(int status, String msg) {
        return new ResponseVO(status, msg);
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("status=").append(status);
        sb.append(",msg=").append(msg);
        sb.append(",data=").append(data);
        return sb.toString();
    }

}
