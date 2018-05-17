package com.example.bootdemo.utils.cache;

import com.example.bootdemo.dao.cache.CacheDao;
import com.example.bootdemo.utils.log.LoggerUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class GuavaUtil {
    @Autowired
    private CacheDao cacheDao;

    private static GuavaUtil guavaUtil;

    @PostConstruct
    public void init(){
        guavaUtil = this;
        guavaUtil.cacheDao = this.cacheDao;
    }

    /**
     * 查询参数配置
     * @param propertyKey   参数key
     * @return  参数值
     */
    public static String getConfigValue(String propertyKey){
        String key = propertyKey;
        try {
            return getConfigValue.get(key).toString();
        } catch (Exception e) {
            LoggerUtil.error(GuavaUtil.class, "param="+propertyKey+",error info:"+e.toString());
        }
        return null;
    }
    //查询短信验证码服务商的url缓存
    private static LoadingCache<String, Object> getConfigValue = CacheBuilder.newBuilder().maximumSize(1000L)
            .expireAfterWrite(120, TimeUnit.SECONDS)//在120秒没有访问或者覆盖则移除缓存
            .refreshAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    return guavaUtil.cacheDao.getConfigValue(key);
                }
            });
}
