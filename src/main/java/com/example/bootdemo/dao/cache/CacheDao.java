package com.example.bootdemo.dao.cache;


import java.util.List;

public interface CacheDao {
    /**
     * 查询短信验证码服务商的url
     * @param key
     * @return
     */
    String getConfigValue(String key);
}
