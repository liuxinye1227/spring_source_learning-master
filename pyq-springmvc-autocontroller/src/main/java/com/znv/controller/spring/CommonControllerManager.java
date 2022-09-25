package com.znv.controller.spring;

import com.znv.controller.exception.CommonControllerException;
import com.znv.controller.utils.AopTargetUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author pengYongQiang
 * @date 2020/11/3 16:57
 *
 * 1.维护了通用Controller层信息
 * 2.通用Controller层的beanName生成器
 * 3.一些获取信息的工具方法
 */
public class CommonControllerManager {

    //通用controller上下文
    private List<CommonControllerInfo> commonControllerInfoContext;
    //通用 Controller 的beanName生成器
    private CommonControllerBeanNameGenerator commonControllerBeanNameGenerator;

    private Class<?> defaultCommonController;
    private Set<Class<?>> injectCommonControllerServices;


    public CommonControllerManager(CommonControllerBeanNameGenerator commonControllerBeanNameGenerator) {
        this.commonControllerBeanNameGenerator = commonControllerBeanNameGenerator;
        commonControllerInfoContext = new ArrayList<>();
    }

    public CommonControllerManager() {
        commonControllerBeanNameGenerator = new CommonControllerBeanNameGenerator(){};
        commonControllerInfoContext = new ArrayList<>();
    }

    public boolean containsBeanName(String beanName) {
        for (CommonControllerInfo commonControllerInfo : commonControllerInfoContext) {
            String mixedBeanDefinitionName = commonControllerInfo.getMixedBeanDefinitionName();
            if (mixedBeanDefinitionName.equals(beanName)) {
                return true;
            }
        }
        return false;
    }


    public CommonControllerInfo getCommonControllerInfo(String mixedBeanDefinitionName) {
        for (CommonControllerInfo commonControllerInfo : commonControllerInfoContext) {
            if (commonControllerInfo.getMixedBeanDefinitionName().equals(mixedBeanDefinitionName)) {
                return commonControllerInfo;
            }
        }
        return null;
    }


    public Class<?> getGenericTypeClassForService(Object baseService) {
        Class<?> baseServiceGenericType = null;
        Object target = AopTargetUtils.getTarget(baseService);
        Class<?> targetClass = target.getClass();
        for (CommonControllerInfo commonControllerInfo : commonControllerInfoContext) {
            baseServiceGenericType = commonControllerInfo.getBaseServiceGenericType(targetClass);
            if (baseServiceGenericType != null) {
                return baseServiceGenericType;
            }
        }
        return null;

    }

    //适配请求参数，使请求参数成为一个service可操作的对象
    public Object adapterRequestParamForService(Map map, Object baseService) {

        Class<?> baseServiceGenericType = getGenericTypeClassForService(baseService);
        Object target = AopTargetUtils.getTarget(baseService);
        Class<?> targetClass = target.getClass();
        Assert.notNull(baseServiceGenericType, "解析【" + targetClass.getName() + "】泛型失败");
        Object o = null;
        try {
            o = baseServiceGenericType.newInstance();
            BeanUtils.populate(o, map);
        } catch (Exception e) {
            throw new CommonControllerException("Map对象在转为一个Service层可操作的实体对象时失败", e);
        }
        return o;
    }

    public void addAutoControllerInfo(CommonControllerInfo commonControllerInfo) {
        commonControllerInfoContext.add(commonControllerInfo);
    }


    public void validateCommonService() {
        for (Class<?> aClass : injectCommonControllerServices) {
            if (!aClass.isInterface()) {
                throw new IllegalStateException("通用service【" + aClass.toString() + "】必须是一个接口");
            }
            ResolvableType resolvableType = ResolvableType.forType(aClass);
            ResolvableType[] generics = resolvableType.getGenerics();
            if (generics.length == 0) {
                throw new IllegalStateException("通用service接口【" + aClass.toString() + "】必须有一个泛型类型");
            }
        }
    }

    public List<CommonControllerInfo> getCommonControllerInfoContext() {
        return commonControllerInfoContext;
    }
    public CommonControllerBeanNameGenerator getCommonControllerBeanNameGenerator() {
        return commonControllerBeanNameGenerator;
    }

    public Class<?> getDefaultCommonController() {
        return defaultCommonController;
    }

    public void setDefaultCommonController(Class<?> defaultCommonController) {
        this.defaultCommonController = defaultCommonController;
    }

    public Set<Class<?>> getInjectCommonControllerServices() {
        return injectCommonControllerServices;
    }

    public void setInjectCommonControllerServices(Set<Class<?>> injectCommonControllerServices) {
        this.injectCommonControllerServices = injectCommonControllerServices;
    }


}
