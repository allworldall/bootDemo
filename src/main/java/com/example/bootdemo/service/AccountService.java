package com.example.bootdemo.service;

import com.example.bootdemo.dao.daoInterface.AccountDao;
import com.example.bootdemo.pojo.dto.AccountDTO;
import com.example.bootdemo.pojo.dto.QueryAccountDTO;
import com.example.bootdemo.pojo.po.SysAccountPO;
import com.example.bootdemo.pojo.vo.ResponseVO;
import com.example.bootdemo.redis.jedis.JedisClientPool;
import com.example.bootdemo.utils.DataFormatUtils.StringUtils;
import com.example.bootdemo.utils.constant.SysCodeConstant;
import com.example.bootdemo.utils.constant.SysStringConstant;
import com.example.bootdemo.utils.sign.MD5Util;
import com.example.bootdemo.utils.visit.ProtectedVisitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

    @Autowired
    JedisClientPool jedisClientPool;

    public ResponseVO getAccount(QueryAccountDTO query, String remoteIP) {
        if(!ProtectedVisitUtil.legalVisit(remoteIP, jedisClientPool, "getAccount")) {
            return ResponseVO.createbyFail(SysCodeConstant.ERROR_ILLEGAL_VISIST, SysStringConstant.DESC_ILLEGAL_VISIST);
        }
        if(!MD5Util.getMD5(query.getOpenId()+query.getAppId()).equalsIgnoreCase(query.getSign())) {
            return ResponseVO.createbyFail(SysCodeConstant.ERROR_SIGN, SysStringConstant.DESC_ERROR_SIGN);
        }
        SysAccountPO sysAccountPO = accountDao.getAccount(query);
        if(StringUtils.isEmpty(sysAccountPO.getAppId())) {
            return ResponseVO.createbyFail(SysCodeConstant.ERROR_QUERY_ACCOUNT, SysStringConstant.DESC_QUERY_ACCOUNT);
        }
        return ResponseVO.createbySuccess(sysAccountPO);
    }

    @Transactional(rollbackFor=java.lang.Throwable.class, propagation= Propagation.REQUIRED)
    public ResponseVO addAccount(AccountDTO account, String remoteIP) {
        if(!ProtectedVisitUtil.legalVisit(remoteIP, jedisClientPool, "addAccount")) {
            return ResponseVO.createbyFail(SysCodeConstant.ERROR_ILLEGAL_VISIST, SysStringConstant.DESC_ILLEGAL_VISIST);
        }
        if(!MD5Util.getMD5(account.getOpenId()+account.getAppId()).equalsIgnoreCase(account.getSign())) {
            return ResponseVO.createbyFail(SysCodeConstant.ERROR_SIGN, SysStringConstant.DESC_ERROR_SIGN);
        }
        accountDao.insertAccount(account);
        return ResponseVO.createbySuccess();
    }

    @Transactional(rollbackFor=java.lang.Throwable.class, propagation= Propagation.REQUIRED)
    public ResponseVO updateAccount(AccountDTO account, String remoteIP) {
        if(!ProtectedVisitUtil.legalVisit(remoteIP, jedisClientPool, "updateAccount")) {
            return ResponseVO.createbyFail(SysCodeConstant.ERROR_ILLEGAL_VISIST, SysStringConstant.DESC_ILLEGAL_VISIST);
        }
        if(!MD5Util.getMD5(account.getOpenId()+account.getAppId()).equalsIgnoreCase(account.getSign())) {
            return ResponseVO.createbyFail(SysCodeConstant.ERROR_SIGN, SysStringConstant.DESC_ERROR_SIGN);
        }
        accountDao.updateAccount(account);
        return ResponseVO.createbySuccess();
    }

}
