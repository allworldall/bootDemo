package com.example.bootdemo.controller;

import com.example.bootdemo.pojo.dto.AccountDTO;
import com.example.bootdemo.pojo.dto.QueryAccountDTO;
import com.example.bootdemo.pojo.vo.ResponseVO;
import com.example.bootdemo.service.AccountService;
import com.example.bootdemo.utils.DataFormatUtils.IPUtils;
import com.example.bootdemo.utils.DataFormatUtils.JsonUtil;
import com.example.bootdemo.utils.annotation.support.ValidateService;
import com.example.bootdemo.utils.constant.SysCodeConstant;
import com.example.bootdemo.utils.constant.SysStringConstant;
import com.example.bootdemo.utils.exceptionUtil.ParamException;
import com.example.bootdemo.utils.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController{

    @Autowired
    AccountService accountService;

    @RequestMapping("/get")
    public void getAccount(HttpServletResponse response, HttpServletRequest request, QueryAccountDTO query) {
        ResponseVO respVO = null;
        try{
            ValidateService.valid(query);
            respVO = accountService.getAccount(query, IPUtils.getTrueIP(request));
        }catch (ParamException paramExcep) {
            respVO = ResponseVO.createbyFail(SysCodeConstant.ERROR_PARAM, SysStringConstant.DESC_ERROR_PARAM);
            LoggerUtil.error(this.getClass(), paramExcep.toString());
        }catch (Throwable e){
            respVO = ResponseVO.createbyFail(SysCodeConstant.ERROR_SYSTEM, SysStringConstant.DESC_ERROR_SYSTEM);
            LoggerUtil.error(this.getClass(), e.toString());
        } finally{
            response(response, JsonUtil.convertBeanToJson(respVO));
        }
    }

    @RequestMapping("/add")
    public void addAccount(HttpServletResponse response, HttpServletRequest request, AccountDTO account) {
        ResponseVO respVO = null;
        try{
            ValidateService.valid(account);
            respVO = accountService.addAccount(account, IPUtils.getTrueIP(request));
        }catch (ParamException paramExcep) {
            respVO = ResponseVO.createbyFail(SysCodeConstant.ERROR_PARAM, SysStringConstant.DESC_ERROR_PARAM);
            LoggerUtil.error(this.getClass(), paramExcep.toString());
        }catch (Throwable e){
            respVO = ResponseVO.createbyFail(SysCodeConstant.ERROR_SYSTEM, SysStringConstant.DESC_ERROR_SYSTEM);
            LoggerUtil.error(this.getClass(), e.toString());
        } finally{
            response(response, JsonUtil.convertBeanToJson(respVO));
        }
    }

    @RequestMapping("/update")
    public void updateAccount(HttpServletResponse response, HttpServletRequest request, AccountDTO account) {
        ResponseVO respVO = null;
        try{
            ValidateService.valid(account);
            respVO = accountService.updateAccount(account, IPUtils.getTrueIP(request));
        }catch (ParamException paramExcep) {
            respVO = ResponseVO.createbyFail(SysCodeConstant.ERROR_PARAM, SysStringConstant.DESC_ERROR_PARAM);
            LoggerUtil.error(this.getClass(), paramExcep.toString());
        }catch (Throwable e){
            respVO = ResponseVO.createbyFail(SysCodeConstant.ERROR_SYSTEM, SysStringConstant.DESC_ERROR_SYSTEM);
            LoggerUtil.error(this.getClass(), e.toString());
        } finally{
            response(response, JsonUtil.convertBeanToJson(respVO));
        }
    }
}
