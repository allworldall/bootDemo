package com.example.bootdemo.dao.cache.impl;

import com.example.bootdemo.dao.cache.CacheDao;
import com.example.bootdemo.dao.daoInterface.ConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cacheDaoImpl")
public class CacheDaoImpl implements CacheDao {

    @Autowired
    ConfigDao configDao;

    /**
     * 查询短信验证码服务商的url
     * @param configKey
     * @return
     */
    @Override
    public String getConfigValue(String configKey) {
        return configDao.getConfigValue(configKey);
    }

}
