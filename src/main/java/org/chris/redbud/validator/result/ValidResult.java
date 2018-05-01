package org.chris.redbud.validator.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/2 PM2:35
 **/
public class ValidResult {
    private Integer errorCode;
    List<ValidError> errors;

    public ValidResult() {
        errors = new ArrayList<>(10);
    }

    public ValidResult(Integer errorCode) {
        this.errorCode = errorCode;
        errors = new ArrayList<>(10);
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public List<ValidError> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "ValidResult{" +
                "errorCode=" + errorCode +
                ", errors=" + errors +
                '}';
    }
}