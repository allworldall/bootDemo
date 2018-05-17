package com.example.bootdemo.controller;

import com.example.bootdemo.utils.log.LoggerUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    public void response(HttpServletResponse response, int result) {
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtil.error(this.getClass(), "response error, error info:"+e.toString()+",param:"+result);
        }
    }

    public void response(HttpServletResponse response, String result) {
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtil.error(this.getClass(), "response error, error info:"+e.toString()+",param:"+result);
        }
    }
}
