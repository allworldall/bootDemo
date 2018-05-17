package com.example.bootdemo.controller;

import com.example.bootdemo.pojo.vo.Md5VO;
import com.example.bootdemo.service.SignService;
import com.example.bootdemo.utils.DataFormatUtils.StringUtils;
import com.example.bootdemo.utils.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "sign")
public class SignController extends BaseController {

    @Autowired
    SignService signService;

    /**
     * 获取MD5大写加密结果
     * @param param
     * @param response
     * @param request
     */
    @RequestMapping(value = "md5")
    public void md5(String param, HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        try{
            if(StringUtils.isEmpty(param)) {
                result = "sign param is empty";
            }
            result = new Md5VO().getReponseJsonString(param, signService.getMd5(param));
        }catch(Exception e) {
            e.printStackTrace();
            result = "system error";
        }finally {
            response(response, result);
        }
    }

    /**
     * 获取RSA一对公私钥
     * @param response
     * @param request
     */
    @RequestMapping(value = "getRSAPairKey")
    public void getPubAndPriKey(HttpServletResponse response, HttpServletRequest request){
        String result = "";
        try{
            result = signService.getPubAndPriKey();
        }catch (Exception e) {
            LoggerUtil.error(this.getClass(), "error info:"+e.toString());
            result = "system error";
        }finally {
            response(response, result);
        }
    }
}
