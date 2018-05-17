package com.example.bootdemo.dao.daoInterface;

import com.example.bootdemo.pojo.dto.AccountDTO;
import com.example.bootdemo.pojo.dto.QueryAccountDTO;
import com.example.bootdemo.pojo.po.SysAccountPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户操作
 */
@Mapper
public interface AccountDao {

    void insertAccount(AccountDTO account);

    void updateAccount(AccountDTO account);

    SysAccountPO getAccount(QueryAccountDTO query);
}
