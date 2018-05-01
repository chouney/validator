package org.chris.redbud.validator.validate.annotation;

import org.chris.redbud.validator.validate.ContainsEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 集合校验器：判断enum元素是否存在该集合中
 * 规范约定：
 * 该枚举类必须声明一个value字段，校验器会通过字段判断参数是否包含在其中
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/9 AM10:32
 **/
@Target({ ElementType.PARAMETER,ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ContainsEnumValidator.class)
public @interface ContainsEnum {
    String message() default "没有通过枚举校验";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<? extends Enum>[] value() default {};
}
