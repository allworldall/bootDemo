package com.example.bootdemo.utils.function.concurrent;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Description 并发控制信息实体类，每一个实例对应个控制点
 * @Author panpan
 * @Date 2018/8/15 14:52
 * @Vrerison 1.0
 *
 * 注意: 该对象需要锁保护
 **/
public class ConcurrentBean implements Serializable {

    private String key;                   //需要控制并发频率的方法(这里采用 全类名+方法名)

    private int timer;                   //时间粒度,单位为妙

    private int count;                   //该时间粒度下可执行的次数

    private long currentTime = 0;        //每次正常访问后设置为当前时间，单位为妙的时间戳

    private int requestCount = 0;        //当前粒度下已经访问次数

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ConcurrentBean that = (ConcurrentBean) o;
        return key == that.getKey() || key.equals(that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, timer, count);
    }

    public ConcurrentBean() {
    }

    public ConcurrentBean(String key, int timer, int count) {
        this.key = key;
        this.timer = timer;
        this.count = count;
    }

    public ConcurrentBean(String key, int timer, int count, long currentTime, int requestCount) {
        this.key = key;
        this.timer = timer;
        this.count = count;
        this.currentTime = currentTime;
        this.requestCount = requestCount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public String toString() {
        return "ConcurrentBean{" +
                "key='" + key + '\'' +
                ", timer=" + timer +
                ", count=" + count +
                ", currentTime=" + currentTime +
                ", requestCount=" + requestCount +
                '}';
    }
}
