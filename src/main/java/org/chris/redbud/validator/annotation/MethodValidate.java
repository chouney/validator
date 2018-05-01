package org.chris.redbud.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 注解验证入口
 * @author chriszhang
 * @version 1.0
 * @created 2017/6/2 AM11:26
 **/
@Target({METHOD})
@Retention(RUNTIME)
public @interface MethodValidate {
}
