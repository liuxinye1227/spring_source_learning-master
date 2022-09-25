package com.znv.controller.annotation;

import com.znv.controller.spring.InjectCommonServiceAnnotationBeanPostProcessor;
import com.znv.controller.spring.converter.AbstractControllerToServiceParamProxy;
import com.znv.controller.spring.converter.ControllerToServiceParamProxy;

import java.lang.annotation.*;

/**
 * @author pengYongQiang
 * @date 2020/11/9 11:14
 *
 * 用于在通用controller类中，注入通用service属性对象
 * 或代理 service 层，方便自动把 controller 层的Map参数直接转为Service层可操作的参数
 *
 * 前置条件：
 * @EnableCommonController(defaultCommonController = CommonController.class,defaultCommonService = BaseService.class)
 *
 * 例：
 * public class CommonController {
 *
 *     @InjectCommonService
 *     private BaseService<Object> baseService;
 *
 * }
 *
 * 1.往通用controller中注入通用service的bean后置处理器
 * @see InjectCommonServiceAnnotationBeanPostProcessor
 *
 * 2.controller 层的Map参数直接转为Service层可操作的参数
 * @see AbstractControllerToServiceParamProxy
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectCommonService {
    boolean required() default true;
    //开启转换代理
    boolean mapConvertProxy() default true;
    //自定义转换器
    Class<? extends AbstractControllerToServiceParamProxy> customConverter() default ControllerToServiceParamProxy.class;
    //开启验证代理 JSR-303
    boolean validateProxy() default true;

    Class<?>[] validateGroup() default {};
}
