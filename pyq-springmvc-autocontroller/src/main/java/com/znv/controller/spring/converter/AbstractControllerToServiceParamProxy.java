package com.znv.controller.spring.converter;

import com.znv.controller.spring.CommonControllerManager;
import com.znv.controller.spring.InjectCommonServiceAnnConfig;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pengYongQiang
 * @date 2020/11/9 16:45
 */
public abstract class AbstractControllerToServiceParamProxy implements InvocationHandler {
    private final Object target;
    protected CommonControllerManager commonControllerManager;
    protected InjectCommonServiceAnnConfig config;

    public AbstractControllerToServiceParamProxy(Object target, CommonControllerManager commonControllerManager, InjectCommonServiceAnnConfig config) {
        this.target = target;
        this.commonControllerManager = commonControllerManager;
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (AopUtils.isHashCodeMethod(method)) {
            return hashCode();
        } else if (AopUtils.isEqualsMethod(method)) {
            return equals(args[0]);
        } else if (AopUtils.isToStringMethod(method)) {
            return toString();
        }
        //参数转换
        if (args != null) {
            if (config.getMapConvertProxy()) {
                parameterConver(target, method, args);
            }
            if (config.getValidateProxy()) {
                parameterValidator(target, method, args);
            }
        }

        return method.invoke(target, args);
    }

    //参数验证
    protected abstract void parameterValidator(Object target, Method method, Object[] args);


    //参数转换
    public abstract void parameterConver(Object target, Method method, Object[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException;
}
