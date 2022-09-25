package com.znv.controller.spring;

import com.znv.controller.spring.converter.AbstractControllerToServiceParamProxy;

/**
 * @author pengYongQiang
 * @date 2020/11/19 23:16
 *
 * 映射InjectCommonService注解参数配置的对象
 */
public class InjectCommonServiceAnnConfig {

    //是否必须注入
    boolean required;
    //开启转换代理
    boolean mapConvertProxy;
    //自定义转换器
    Class<? extends AbstractControllerToServiceParamProxy> customConverter ;
    //开启验证代理 JSR-303
    boolean validateProxy;
    // JSR-303
    Class<?>[] validateGroup;


    public InjectCommonServiceAnnConfig(boolean required, boolean mapConvertProxy, Class<? extends AbstractControllerToServiceParamProxy> customConverter, boolean validateProxy, Class<?>[] validateGroup) {
        this.required = required;
        this.mapConvertProxy = mapConvertProxy;
        this.customConverter = customConverter;
        this.validateProxy = validateProxy;
        this.validateGroup = validateGroup;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getMapConvertProxy() {
        return mapConvertProxy;
    }

    public void setMapConvertProxy(boolean mapConvertProxy) {
        this.mapConvertProxy = mapConvertProxy;
    }

    public Class<? extends AbstractControllerToServiceParamProxy> getCustomConverter() {
        return customConverter;
    }

    public void setCustomConverter(Class<? extends AbstractControllerToServiceParamProxy> customConverter) {
        this.customConverter = customConverter;
    }

    public boolean getValidateProxy() {
        return validateProxy;
    }

    public void setValidateProxy(boolean validateProxy) {
        this.validateProxy = validateProxy;
    }

    public Class<?>[] getValidateGroup() {
        return validateGroup;
    }

    public void setValidateGroup(Class<?>[] validateGroup) {
        this.validateGroup = validateGroup;
    }
}
