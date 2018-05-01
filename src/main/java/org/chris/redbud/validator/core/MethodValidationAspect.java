package org.chris.redbud.validator.core;

import org.chris.redbud.validator.annotation.MethodValidate;
import org.chris.redbud.validator.annotation.Validate;
import org.chris.redbud.validator.exception.InvalidParameterException;
import org.chris.redbud.validator.result.ValidError;
import org.chris.redbud.validator.result.ValidResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

/**
 * 方法验证切面,实现：
 * 1.方法的基本类型参数校验
 * 2.model类型参数校验。
 * 3.参数校验异常的结果返回
 * 4.参数校验异常的异常抛出
 * @author chriszhang
 * @version 1.0
 * @created 2017/5/31 PM6:57
 **/
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodValidationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodValidationAspect.class);

    @Autowired
    private Validator validator;


    /**
     * 匹配所有带@MethodValidate
     */
    @Pointcut(value = "execution(@org.chris.redbud.validator.annotation.MethodValidate * *(..))")
    public void point() {

    }


    /**
     * 进入切点之后，保存Detail信息
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("point()")
    public Object doValidate(ProceedingJoinPoint joinPoint) throws Throwable {

        Object target = joinPoint.getTarget();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //getMethod 获取所有public方法
        Method targetMethod = methodSignature.getMethod();
        //getDeclaredMethod 获取所有的public private protected方法
        Method realMethod = target.getClass().getDeclaredMethod(signature.getName(),
                targetMethod.getParameterTypes());

        MethodValidate fdMethodValidate = realMethod.getAnnotation(MethodValidate.class);
        //获取不到，则直接返回
        if (fdMethodValidate == null) {
            LOGGER.warn("errors encountered when the Annotation MethodValidate could not be found, will not do the validate process");
            return joinPoint.proceed();
        }
        Class[] groups = new Class[0];
        LocalValidatorFactoryBean localValidatorFactoryBean = (LocalValidatorFactoryBean) validator;
        ValidatorImpl validatorImpl = (ValidatorImpl) localValidatorFactoryBean.getValidator();
        ValidResult validResult = new ValidResult(400);
        Parameter[] parameters = realMethod.getParameters();
        Object[] parameterValues = joinPoint.getArgs();
        if (parameters.length != parameterValues.length) {
            //日志报错
            LOGGER.warn("errors encountered when parameter`s count isn`t equal to arg`s count, will not do the validate process");
            return joinPoint.proceed();
        }
        boolean isExpectException = true;
        //如果有校验结果，则绑定校验结果
        if (parameterValues.length > 0 && ValidResult.class.isAssignableFrom(parameterValues[parameterValues.length - 1].getClass())) {
            int index = parameterValues.length - 1;
            parameterValues[index] = validResult;
            isExpectException = false;
        }
        for (int i = 0; i < parameterValues.length; i++) {
            Parameter parameter = parameters[i];
            Object parameterValue = parameterValues[i];
            //找出model类型进行校验
            Validate validate = parameter.getAnnotation(Validate.class);
            if (validate != null) {
                Set<ConstraintViolation<Object>> violations = validatorImpl.validate(parameterValue, groups);
                buildFdValidResult(violations, validResult);
            }
        }
        Set<ConstraintViolation<Object>> violations = validatorImpl.validateParameters(target, realMethod, parameterValues, groups);
        buildFdValidResult(violations, validResult);
        if (isExpectException && validResult.hasErrors()) {
            LOGGER.warn("errors encountered when parameter ValidResult is not found, will process the exception-expect strategy");
            throw new InvalidParameterException(validResult);
        }
        return joinPoint.proceed(parameterValues);
    }

    private void buildFdValidResult(Set<ConstraintViolation<Object>> violations, ValidResult validResult) {
        if (!violations.isEmpty()) {
            List<ValidError> errors = validResult.getErrors();
            for (ConstraintViolation violation : violations) {
                String propertyPath = getErrorPropertyPathInformation(violation);
                errors.add(new ValidError(propertyPath, violation.getMessage(), violation.getInvalidValue()));
            }
        }
    }

    private String getErrorPropertyPathInformation(ConstraintViolation violation) {
        StringBuilder stringBuilder = new StringBuilder();
        Path path = violation.getPropertyPath();
        path.forEach((Path.Node node) ->
                stringBuilder.append(node.getName()).append("."));
        return stringBuilder.length() == 0 ? stringBuilder.toString() : stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length()).toString();
    }

}