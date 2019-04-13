package com.example.bootdemo.WebControl;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @Description
 * @Author panpan
 * @Date 2019-04-13 12:28
 * @Version 1.0
 **/
public class MyInterceptor implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (true) {
            System.out.println("已经进行拦截了。。。。。。。。");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object
            handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }

//    public void returnErrorResponse(HttpServletResponse response, JSONResult result) throws IOException, UnsupportedEncodingException {
//        OutputStream out = null;
//        try {
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("text/json");
//            out = response.getOutputStream();
//            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
//            out.flush();
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
}
