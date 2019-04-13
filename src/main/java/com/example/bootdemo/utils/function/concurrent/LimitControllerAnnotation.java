package com.example.bootdemo.utils.function.concurrent;


import java.lang.annotation.*;

/**
 * @Description 用来声明哪些类下有方法需要考虑并发
 * @Author panpan
 * @Date 2018/8/15 17:26
 * @Vrerison 1.0
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface LimitControllerAnnotation {
    Class<?> value();
}
