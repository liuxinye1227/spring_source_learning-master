package com.znv.controller.annotation;

import java.lang.annotation.*;

/**
 * @author pengYongQiang
 * @date 2020/10/28 16:06
 *
 * 启用 Controller层 自动化
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonController {

    /**
     * 可以指定多个 commonController
     * 如果指定了多个commonController，并且都有相同的请求路径，那么定义在前面的类优先级更高，会覆盖掉后面的
     * @return
     */
    Class<?>[] commonControllers() default {};
    /**
     * 现在只支持一个泛型
     */
    Class<?> genericType();
}
