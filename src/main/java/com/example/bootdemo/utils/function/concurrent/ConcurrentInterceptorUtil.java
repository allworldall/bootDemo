package com.example.bootdemo.utils.function.concurrent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description 用于并发控制拦截器的一些工具方法
 * @Author panpan
 * @Date 2018/8/16 14:32
 * @Vrerison 1.0
 **/
public class ConcurrentInterceptorUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentInterceptorUtil.class);

    /**
     * 校验本次访问是否被允许
     * <p>
     * 注意: 由于是按照$符号进行截取, 不要使用内部类
     *
     * @param handler
     * @return
     */
    public static ConcurrentErrorResponse validateAccess(Map<String, ConcurrentBean> concurrentBeanMap, HandlerMethod handler) throws Exception {
        try {
            String className = handler.getBean().getClass().getName();
            Method method = handler.getMethod();
            String methodName = method.getName();
            String key = className + "." + methodName;
            ConcurrentBean concurrentBean = concurrentBeanMap.get(key);
            boolean access = analyzeConcurrentBean(concurrentBean);
            ConcurrentErrorResponse response = new ConcurrentErrorResponse(access);
            if (!access) {
                response.setResponseContent(concurrentBean.getTimer());
            }
            return response;
        } catch (Exception e) {
            logger.error("并发控制拦截器异常，请通知开发人员,error info: " + e.toString());
            return new ConcurrentErrorResponse(true);
        }

    }

    /**
     * 分析当前访问控制实例
     *
     * @param concurrentBean
     * @return 返回false为不允许访问状态，返回true为允许访问
     * 有如下情况需要考虑，以访问时间为主线索
     * 分两个大类
     * 1、当前访问时间距上次访问时间小于时间粒度：
     * A、未达到设定次数：
     * A1、将访问次数加1
     * A2、将最后访问时间设置成当前时间
     * B、达到设定次数：
     * B1、重置当前访问次数为0，返回false
     * <p>
     * 2、当前访问时间距上次访问时间大于时间粒度：
     * C、重置当前访问时间，设置访问次数为1次
     */
    public static boolean analyzeConcurrentBean(ConcurrentBean concurrentBean) throws Exception {
        if (concurrentBean == null) {
            return true;
        }
        long now = System.currentTimeMillis() / 1000;

        // barking一下.
        if ((now - concurrentBean.getCurrentTime() < concurrentBean.getTimer()) && (concurrentBean.getRequestCount() >= concurrentBean.getCount())) {
            return false;
        }
        synchronized (concurrentBean) {
            if (now - concurrentBean.getCurrentTime() < concurrentBean.getTimer()) {
                if (concurrentBean.getRequestCount() >= concurrentBean.getCount()) {
                    // 这句可以注释，因为达到访问次数下次要访问，时间间隔必须要查过设置的粒度，那时的访问，会重新set该值
                    // concurrentBean.setRequestCount(0);
                    return false;
                } else {
                    concurrentBean.setRequestCount(concurrentBean.getRequestCount() + 1);
                    concurrentBean.setCurrentTime(now);
                }
            } else {
                concurrentBean.setCurrentTime(now);
                concurrentBean.setRequestCount(1);
            }
            return true;
        }
    }
}
