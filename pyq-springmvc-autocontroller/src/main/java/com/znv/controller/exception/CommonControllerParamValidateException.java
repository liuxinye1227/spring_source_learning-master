package com.znv.controller.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author pengYongQiang
 * @date 2020/11/19 21:20 validate
 */

public class CommonControllerParamValidateException extends RuntimeException {

    private Set<ConstraintViolation<Object>> constraintViolations;

    public CommonControllerParamValidateException(String message,Set<ConstraintViolation<Object>> constraintViolations) {
        super(message);
        this.constraintViolations=constraintViolations;
    }

    public CommonControllerParamValidateException(String message) {
        super(message);
    }

    public CommonControllerParamValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonControllerParamValidateException(Throwable cause) {
        super(cause);
    }

    public Set<ConstraintViolation<Object>> getConstraintViolations() {
        return constraintViolations;
    }
}
