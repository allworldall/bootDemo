package com.example.bootdemo.redis.jedis;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

@Configuration
public class JedisConfiguration {

    @Autowired
    private JedisProperties jedisProperties;

    @Bean
    public JedisPool getJedisPool() {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        JedisPool jedisPool = new JedisPool(poolConfig, jedisProperties.getHost(), jedisProperties.getPort(),
                jedisProperties.getTimeout(), jedisProperties.getPassword(), jedisProperties.getSsl());

        return jedisPool;
    }

    @Bean
    @Profile("redisCluster")
    public JedisCluster getJedisCluster() {

        List<String> nodesList = jedisProperties.getCluster().getNodes();
        HashSet<HostAndPort> nodesSet = new HashSet<>();
        if (nodesList != null && nodesList.size() > 0) {
            for (String node : nodesList) {
                if (node != null) {
                    String[] hostAndPort = node.split(":");
                    HostAndPort hostAndPort2 = new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
                    nodesSet.add(hostAndPort2);
                }
            }
        }

        return new JedisCluster(nodesSet);
    }
}