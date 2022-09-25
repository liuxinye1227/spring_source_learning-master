package com.znv.controller.spring;

import lombok.Data;
import org.springframework.core.GenericTypeResolver;

import java.util.List;

/**
 * @author pengYongQiang
 * @date 2020/11/4 14:00
 * <p>
 * 一个通用controller的描述信息
 */
@Data
public class CommonControllerInfo {

    //标准的controller Class
    private Class<?> standardControllerClass;
    //通用的controller Class
    private Class<?> commonControllerClass;
    //泛型类型
    private Class<?> genericTypeClass;
    //基于标准的controller Class，与通用controller 混合出来的Definition的Name
    private String mixedBeanDefinitionName;
    //通用controller 中需要被注入的baseService字段
    private List<BaseServiceField> baseServiceFields;


    public Class<?> getBaseServiceGenericType(Class<?> targetClass) {
        for (BaseServiceField baseServiceField : baseServiceFields) {
            Class<?> baseServiceInterface = baseServiceField.getBaseServiceInterface();

            if (baseServiceInterface.getClassLoader().equals(targetClass.getClassLoader()) &&
                    baseServiceInterface.isAssignableFrom(targetClass)
            ) {
                Class<?> currentGenericTypeClass = GenericTypeResolver.resolveTypeArgument(targetClass, baseServiceInterface);
                if (currentGenericTypeClass != null && currentGenericTypeClass.equals(genericTypeClass)) {
                    return genericTypeClass;
                }
            }
        }
       return null;
    }
}
