package com.example.bootdemo.utils.visit;

import com.example.bootdemo.redis.jedis.JedisClientPool;
import com.example.bootdemo.utils.DataFormatUtils.StringUtils;
import com.example.bootdemo.utils.cache.GuavaUtil;
import com.example.bootdemo.utils.constant.SysStringConstant;
import com.example.bootdemo.utils.log.LoggerUtil;

/**
 * 此类用于防止服务器被刷
 */
public class ProtectedVisitUtil {

    /**
     * 此方法根据保各个请求的IP在redis中，然后每次请求来判断是否符合被刷的条件
     * @param remoteIP
     * @param jedis
     * @return
     */
    public static boolean legalVisit(String remoteIP, JedisClientPool jedis, String methodName) {
        String visitRedisKey = SysStringConstant.PROTECTED_VISIT_PREFIX + methodName + remoteIP;
        String visitExpire = GuavaUtil.getConfigValue(SysStringConstant.PROTECTED_VISIT_EXPIRE_KEY);
        String visitCount = GuavaUtil.getConfigValue(SysStringConstant.PROTECTED_VISIT_COUNT_KEY);
        if(StringUtils.isEmpty(visitExpire) || StringUtils.isEmpty(visitCount)) {
            LoggerUtil.error(ProtectedVisitUtil.class, "protected_visit_expire and protected_visit_count exsist not cnfig");
            return false;
        }
        if(!jedis.exists(visitRedisKey)){
            jedis.incr(visitRedisKey);
            jedis.expire(visitRedisKey, Integer.parseInt(visitExpire));
            return true;
        }else {//之前有记过这个IP
            String incr = jedis.get(visitRedisKey);
            if(!StringUtils.isEmpty(incr) && Integer.parseInt(incr) >= Integer.parseInt(visitCount)) {
                return false;
            }else {
                jedis.incr(visitRedisKey);
                return true;
            }
        }

    }
}
