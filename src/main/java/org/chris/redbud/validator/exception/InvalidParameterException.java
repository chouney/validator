package org.chris.redbud.validator.exception;


import org.chris.redbud.validator.result.ValidResult;

/**
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/1 PM5:40
 **/
public class InvalidParameterException extends RuntimeException {

    private ValidResult validResult;

    public InvalidParameterException(ValidResult validResult) {
        super(validResult.toString());
        this.validResult = validResult;
    }

    public InvalidParameterException(ValidResult validResult, Throwable cause) {
        super(validResult.toString(), cause);
        this.validResult = validResult;
    }

    public ValidResult getValidResult() {
        return validResult;
    }
}