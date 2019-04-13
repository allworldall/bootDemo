package com.example.bootdemo.initbean;

import com.example.bootdemo.WebControl.ConcurrentRequestIntercepter;
import com.example.bootdemo.utils.log.LoggerUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 初始化视图解析器，用于jsp，暂时先全部注释掉
 */
@Configuration
@EnableWebMvc
public class MVCconfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         /*addPathPatterns 用于添加拦截规则
         excludePathPatterns 用户排除拦截*/
        LoggerUtil.info(MVCconfig.class, "拦截器添加成功");
        registry.addInterceptor(new ConcurrentRequestIntercepter()).addPathPatterns("/**")
                /*.excludePathPatterns("/index.html", "/", "/user/login")*/;
    }
}
