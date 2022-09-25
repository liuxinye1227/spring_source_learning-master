package com.znv.controller.spring;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author pengYongQiang
 * @date 2020/10/31 11:09
 */
@Slf4j
@Data
@Import({InjectCommonServiceAnnotationBeanPostProcessor.class, CommonControllerManager.class})
public class CommonControllerConfig {


    @Bean
    public ExtensionRequestMappingHandlerMapping extensionRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping,
                                                                                       CommonControllerBeanDefinitionRegistrar controllerBeanDefinitionRegistrar,
                                                                                       CommonControllerManager commonControllerManager) {
        ExtensionRequestMappingHandlerMapping extensionRequestMappingHandlerMapping =
                new ExtensionRequestMappingHandlerMapping( requestMappingHandlerMapping,controllerBeanDefinitionRegistrar,commonControllerManager);
        //位置靠后
        extensionRequestMappingHandlerMapping.setOrder(Integer.MAX_VALUE);
        return extensionRequestMappingHandlerMapping;
    }

    @Bean
    public CommonControllerBeanDefinitionRegistrar commonControllerBeanDefinitionRegistrar(CommonControllerManager commonControllerManager) {
        return new CommonControllerBeanDefinitionRegistrar(commonControllerManager);
    }

}
