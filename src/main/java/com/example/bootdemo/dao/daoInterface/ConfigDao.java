package com.example.bootdemo.dao.daoInterface;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigDao {

    String getConfigValue(String configKey);
}
