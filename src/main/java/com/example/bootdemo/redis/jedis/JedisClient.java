package com.example.bootdemo.redis.jedis;

import java.util.Map;

/**
 * redis客户端接口
 *
 * @author dell
 *
 */
public interface JedisClient {

    public String set(final String key, String value);

    public String set(final byte[] key, final byte[] value);

    public String get(final String key);

    public byte[] get(final byte[] key);

    public Long del(String key);

    public Long del(final byte[] key);

    public Long del(final String... keys);

    public Long del(final byte[]... keys);

    public Boolean exists(final String key);

    public Long expire(final String key, final int seconds);

    public Long expire(final byte[] key, final int seconds);

    public Long ttl(final String key);

    public Long incr(final String key);

    public Long hset(final String key, final String field, final String value);

    public String hget(final String key, final String field);

    public Map<String, String> hgetAll(final String key);

    public Long hdel(final String key, final String... field);

}
