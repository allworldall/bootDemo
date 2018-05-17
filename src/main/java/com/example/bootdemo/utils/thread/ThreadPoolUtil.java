package com.example.bootdemo.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
    //队列容量，默认和最小线程池一样大
    private static final int     queueCapacity       = 100;
    //线程最大等待时间
    private static final long    keepAliveTime       = 180000L;
    
    private static final int     corePoolSize        = 50;
    
    private static final int     maximumPoolSize     = 150;
    
    public static final ExecutorService pool = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity));
    
	
}
