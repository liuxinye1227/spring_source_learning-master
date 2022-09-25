package com.znv.controller.spring;

import lombok.Data;

/**
 * @author pengYongQiang
 * @date 2020/11/4 16:11
 */
@Data
public class BaseServiceField {

    //属性的名称
    private String fieldName;
    //在IOC中的名称
    private String beanBeanDefinitionName;
    //实现类的接口
    private Class<?> baseServiceInterface;
    //实现类
    private Class<?> baseServiceImpl;
}
