package org.chris.redbud.validator.result;

/**
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/2 PM2:38
 **/
public class ValidError<T> {
    private String propertyPath;
    private String errorMessage;
    private Object invalidValue;

    public ValidError(String propertyPath, String errorMessage, Object invalidValue) {
        this.propertyPath = propertyPath;
        this.errorMessage = errorMessage;
        this.invalidValue = invalidValue;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    @Override
    public String toString() {
        return "提交参数未通过: " + errorMessage + " 请检查提交参数:" + invalidValue;
    }
}