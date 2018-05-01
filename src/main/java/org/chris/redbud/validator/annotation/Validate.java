package org.chris.redbud.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 模型验证注解
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/2 PM3:46
 **/
@Target({PARAMETER})
@Retention(RUNTIME)
public @interface Validate {
}