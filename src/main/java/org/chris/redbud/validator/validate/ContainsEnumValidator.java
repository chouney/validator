package org.chris.redbud.validator.validate;

import org.chris.redbud.validator.validate.annotation.ContainsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * 枚举类型校验器
 * 判断校验参数是否被包含在枚举类的value值中
 * 校验要求：
 * 该枚举类必须声明一个value字段，校验器会通过字段判断参数是否包含在其中
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/8 PM3:27
 **/
public class ContainsEnumValidator implements ConstraintValidator<ContainsEnum,Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainsEnumValidator.class);

    private Class<? extends Enum>[] classes;

    @Override
    public void initialize(ContainsEnum containsEnum) {
        this.classes = containsEnum.value();
    }

    /**
     * 枚举类
     * 获取枚举常量并与其value属性进行比较
     * @param obj
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if(obj == null){
            return false;
        }
        for(Class<? extends Enum> clazz : classes){
            for(Enum constant : clazz.getEnumConstants()) {
                try {
                    Field field = clazz.getDeclaredField("value");
                    field.setAccessible(true);
                    Object value = field.get(constant);
                    if(obj.equals(value)){
                        return true;
                    }
                } catch (NoSuchFieldException e) {
                    LOGGER.warn("Field 'value' Not Found in Enum Class", e);
                } catch (IllegalAccessException e) {
                    LOGGER.warn("Field 'value' Not Accessable in Enum Class", e);
                }
            }
        }
        return false;
    }
}