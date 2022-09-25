package com.znv.controller.spring.converter;

import com.znv.controller.exception.CommonControllerParamValidateException;
import com.znv.controller.spring.CommonControllerManager;
import com.znv.controller.spring.InjectCommonServiceAnnConfig;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author pengYongQiang
 * @date 2020/11/9 20:02
 *
 * 做参数解析和参数验证
 */
public class ControllerToServiceParamProxy extends AbstractControllerToServiceParamProxy {


    public ControllerToServiceParamProxy(Object target,
                                         CommonControllerManager commonControllerManager,
                                         InjectCommonServiceAnnConfig config) {
        super(target, commonControllerManager,config);
    }


    private static final Validator VALIDATOR;

    static {
        Configuration<?> configuration = Validation.byDefaultProvider().configure();
        VALIDATOR = configuration.buildValidatorFactory().getValidator();
    }

    @Override
    public void parameterConver(Object target, Method method, Object[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> entityClass = commonControllerManager.getGenericTypeClassForService(target);
        Object arg = args[0];
        if (arg instanceof Map) {
            Map<String, Object> entityMap = (Map<String, Object>) arg;
            Object entityObj = entityClass.newInstance();
            BeanUtils.populate(entityObj,entityMap);
            args[0] = entityObj;
        }
    }
    @Override
    protected void parameterValidator(Object target, Method method, Object[] args) {
        Object entityObj = args[0];
        Set<ConstraintViolation<Object>> validate = VALIDATOR.validate(entityObj,config.getValidateGroup());
        if (validate.size() != 0) {
            throw new CommonControllerParamValidateException("参数验证失败:"+validate.toString(),validate);
        }
    }

}
