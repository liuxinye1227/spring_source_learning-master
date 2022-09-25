package com.znv.controller.utils;

import com.znv.controller.exception.CommonControllerException;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * @author pengYongQiang
 * @date 2020/11/3 15:18
 */
public class AopTargetUtils {

    /**
     * 获取 目标对象
     * @param proxy 代理对象
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy) {
        if(!AopUtils.isAopProxy(proxy)) {
            return proxy; //不是代理对象
        }
        Object proxyTargetObject = null;
        try {
            if(AopUtils.isJdkDynamicProxy(proxy)) {
                proxyTargetObject = getJdkDynamicProxyTargetObject(proxy);
            } else { //cglib
                proxyTargetObject =  getCglibProxyTargetObject(proxy);
            }
            return proxyTargetObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonControllerException("获取目标对象时失败",e);
        }
    }


    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

}
