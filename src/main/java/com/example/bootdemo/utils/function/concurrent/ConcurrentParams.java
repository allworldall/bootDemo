package com.example.bootdemo.utils.function.concurrent;



import java.lang.annotation.*;

/**
 * @Description 声明在方法上的并发控制注解,指明需要控制的参数
 * @Author panpan
 * @Date 2018/8/15 17:13
 * @Vrerison 1.0
 *
 * eg:@ConcurrentParams(timer=60, count=2)表示60秒只允许访问2次，如果60秒内有两次被访问，要求第三次与第二次的间隔时间超过60秒
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface ConcurrentParams {

    //时间粒度,单位为妙
    int timer();

    //该时间粒度下可执行的次数
    int count() default 1;

}
