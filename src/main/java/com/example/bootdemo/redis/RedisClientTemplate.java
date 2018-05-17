package com.example.bootdemo.redis;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


/*@Component*/
public class RedisClientTemplate {
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过可变参数批量传入key然后进行删除
     *
     * @param String
     *            ... keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 通过key匹配的方式来进行key值的批量删除
     *
     * @param String
     *            pattern
     */
    @SuppressWarnings("unchecked")
    public void removePattern(final String pattern) {
        Set<Serializable> keys = this.redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的key值
     *
     * @param String
     *            key
     */
    @SuppressWarnings("unchecked")
    public void remove(final String key) {
        if (exists(key)) {
            this.redisTemplate.delete(key);
        }
    }

    /**
     * 判断key值是否存在
     *
     * @param String
     *            key
     * @return boolean 存在返回true,不存在返回false
     */
    @SuppressWarnings("unchecked")
    public boolean exists(final String key) {
        return this.redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存中的内容
     *
     * @param String
     *            key
     * @return String
     */
    @SuppressWarnings("unchecked")
    public String get(final String key) {
        Object result = null;
        this.redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        if (result == null) {
            return null;
        } else {
            return result.toString();
        }
    }

    /**
     * 写入缓存
     *
     * @param String
     *            key
     * @param Object
     *            value
     * @return boolean true 写入成功，false 写入失败
     */
    @SuppressWarnings("unchecked")
    public void set(final String key, String value)  {
        ValueOperations<String, String> operations = this.redisTemplate.opsForValue();
        operations.set(key, value);
    }

    /**
     * 写入缓存
     *
     * @param String
     *            key
     * @param Object
     *            value
     * @param Long
     *            expireTime
     * @return boolean 成功返回true 失败返回false
     */
    @SuppressWarnings("unchecked")
    public void set(final String key, Object value, Long expireTime) {
        ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
        operations.set(key, value);
        this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);

    }

    /**
     * hashMap值修改
     *
     * @param String
     *            key
     * @param Map<String,String>
     *            value
     * @return boolean true 修改成功 false 修改失败
     */
    @SuppressWarnings("unchecked")
    public void hmset(String key, Map<String, String> value)  {
        this.redisTemplate.opsForHash().putAll(key, value);
    }

    /**
     * 获取hashMap值
     *
     * @param String
     *            key 获取对应的key值
     * @return Map<String,String> result
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> hmget(String key)  {
        return this.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置redis自增计数器,每次递增值
     *
     * @param String
     *            key 计数key值
     * @param long
     *            每次递增值
     * @return 累计总数
     */
    @SuppressWarnings("unchecked")
    public long incr(String key, long increment)  {
        return this.redisTemplate.opsForValue().increment(key, increment);

    }

    /**
     * 存储list数据
     */
    @SuppressWarnings("unchecked")
    public long setList(String key, Object value)  {
        return this.redisTemplate.opsForList().leftPush(key, value);
    }
    /**
     * 设置过期时间
     * @param String key
     * @param long timeout
     *             过期时间单位为秒
     */
    @SuppressWarnings("unchecked")
    public void setExpire(String key,long timeout) {
        this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
    /**
     * redis set 集合操作
     * @param String key
     * @param Object value
     */
    @SuppressWarnings("unchecked")
    public long addSet(String key,Object value) {
        return this.redisTemplate.opsForSet().add(key, value);
    }
    /**
     * 获取set集合元数个数
     * @param String key
     * @return Integer
     */
    @SuppressWarnings("unchecked")
    public int getSet(String key) {
        return this.redisTemplate.opsForSet().members(key).size();
    }

}
