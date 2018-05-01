package org.chris.redbud.validator.validate;

import org.chris.redbud.validator.validate.annotation.ContainsInt;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 整形枚举校验器
 * 判断参数是否在value数组中
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/8 PM3:27
 **/
public class ContainsIntValidator implements ConstraintValidator<ContainsInt,Integer> {

    private int[] arrInt;

    @Override
    public void initialize(ContainsInt containsInt) {
        this.arrInt = containsInt.value();
    }

    /**
     * 集合类型判断
     * 1.是否为数组集合
     * @param integer
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if(integer == null){
            return false;
        }
        for (int anArrInt : arrInt) {
            if (integer.equals(anArrInt)) {
                return true;
            }
        }
        return false;
    }
}