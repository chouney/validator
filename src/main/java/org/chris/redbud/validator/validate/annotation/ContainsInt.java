package org.chris.redbud.validator.validate.annotation;

import org.chris.redbud.validator.validate.ContainsIntValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 整形数组校验器
 * 判断元素是否存在该整形集合中
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/9 AM10:32
 **/
@Target({ ElementType.PARAMETER,ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ContainsIntValidator.class)
public @interface ContainsInt {
    String message() default "没有通过数组校验";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int[] value() default {};
}
