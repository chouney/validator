// Copyright (C) 2017 Meituan
// All rights reserved

import org.chris.redbud.validator.annotation.MethodValidate;
import org.chris.redbud.validator.annotation.Validate;
import org.chris.redbud.validator.result.ValidResult;
import org.chris.redbud.validator.validate.annotation.ContainsEnum;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import validate.model.CustomModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/6/9 PM1:50
 **/
public class Service {

    //1.HTTP 接口中,ValidResult不需要显示传,默认在参数的最后一位接收
    @MethodValidate
    public void testController1(@ContainsEnum(value = {EnumTest.class}) int a,
                           @Min(3)@NotNull Integer i, ValidResult validResult) {
        if(validResult.hasErrors()){
            System.out.println(validResult.getErrors());
        }
    }

    //2.如果没有显式声明ValidResult,将抛异常
    @MethodValidate
    public void testController2(@ContainsEnum(value = {EnumTest.class}) int a,
                               @Min(3)@NotNull Integer i) {
    }

    //3.集成了Hibernate-Validator的Bean校验,并且对校验结果自定义控制
    @MethodValidate
    public void testController3(@Validate
                                CustomModel customModel,ValidResult validResult) {
        if(validResult.hasErrors()){
            System.out.println(validResult.getErrors());
        }
    }

    @Test
    public void showCase(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Service app = context.getBean("service",Service.class);
        //HTTP 接口中,ValidResult不需要显示传,默认在参数的最后一位接收
        app.testController1(7,1,new ValidResult());
        //如果没有显式声明ValidResult,将抛异常
        try{
            app.testController2(7,1);
        }catch (Exception e){
            e.printStackTrace();
        }

        CustomModel customModel = new CustomModel();
        customModel.setAge(11);
        app.testController3(customModel,new ValidResult());
    }

}