package com.example.bootdemo.WebControl;

import com.example.bootdemo.utils.function.concurrent.*;
import com.example.bootdemo.utils.log.LoggerUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 并发控制拦截器，实例化时，先读取需要做控制的方法，既控制点
 * @Author panpan
 * @Date 2018/8/15 15:39
 * @Vrerison 1.0
 *
 * 注意: 这里控制到方法级别, 但不能是内部类!
 **/
@Component
public class ConcurrentRequestIntercepter implements HandlerInterceptor, ApplicationContextAware {

    private static final Map<String, ConcurrentBean> concurrentBeanMap = new HashMap<>();

    /**
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (concurrentBeanMap.size() == 0) {
            LoggerUtil.info(ConcurrentRequestIntercepter.class,"没有要拦截的");
            return true;
        }
        ConcurrentErrorResponse accessResult = ConcurrentInterceptorUtil.validateAccess(concurrentBeanMap, (HandlerMethod) o);
        if (!accessResult.isAccess()) {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print( accessResult.getResponseContent());
        }
        return accessResult.isAccess();
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LoggerUtil.info(ConcurrentRequestIntercepter.class, "并发访问控制拦截器加载各个控制点(method)信息--------start");
        Map<String, Object> currentBeanMap = applicationContext.getBeansWithAnnotation(LimitControllerAnnotation.class);
        if (currentBeanMap != null && currentBeanMap.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("concurrent action[");
            for (Object currentBean : currentBeanMap.values()) {
                buffer.append("class:");
                Class clazz = currentBean.getClass().getAnnotation(LimitControllerAnnotation.class).value();
                String trueClassName = clazz.getName();
                buffer.append(trueClassName).append(" [method:");
                Method[] methods = clazz.getMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(ConcurrentParams.class)) {
                            ConcurrentParams annotation = method.getAnnotation(ConcurrentParams.class);
                            String methodName = method.getName();
                            String key = trueClassName + "." + methodName;
                            buffer.append(methodName + ",");
                            int timer = annotation.timer();
                            int count = annotation.count();
                            ConcurrentBean bean = new ConcurrentBean(key, timer, count);
                            concurrentBeanMap.put(key, bean);
                        }
                    }
                    buffer.append("];    ");
                }
            }
            buffer.append("]");
            LoggerUtil.info(ConcurrentRequestIntercepter.class, buffer.toString());
        } else {
            LoggerUtil.info(ConcurrentRequestIntercepter.class, "没有需要做并发控制的接口");
        }
    }
}
