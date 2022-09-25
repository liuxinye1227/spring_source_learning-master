package com.znv.controller.annotation;

import com.znv.controller.spring.CommonControllerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author pengYongQiang
 * @date 2020/10/31 13:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CommonControllerConfig.class)
public @interface EnableCommonController {

    Class<?> defaultCommonController();

    Class<?>[] injectCommonControllerService();
}
