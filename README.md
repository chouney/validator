# validator
方法参数校验器，基于Hibernate-Validator进行二次开发，实现功能如下

1. 方法基本类型参数校验。
2. 参数异常时结果等功能进行统一封装
3. 提供自定义的校验规则扩展：枚举校验以及整型数组校验。

---
## 项目依赖
校验工具基于注解+切面实现，

基于**最少侵占原则**，工具依赖仅限编译和测试(scope为provided),不保证运行时。
因此工具依赖spring核心组件,**请确保主项目包含spring组件以及Aspect组件。**
## 使用说明

### 安装
1. 可以通过自行下载源码打包使用
2. //todo 通过maven打包

### 配置

添加配置:

`<import resource="classpath*:validation-configuration.xml"/>`

> 使用前需要确认进行参数校验的方法注入到Spring中

## DEMO

方法基本类型校验、Bean类型校验、校验结果获取DEMO如下：

```
	//1.HTTP 接口中,ValidResult不需要显示传,默认在参数的最后一位接收
    @MethodValidate
    public void testController1(
    //2.自定义枚举校验,校验传参枚举中的value字段是否包含该值
    @ContainsEnum(value = {EnumTest.class}) int a,
                           @Min(3)@NotNull Integer i, ValidResult validResult) {
        if(validResult.hasErrors()){
            System.out.println(validResult.getErrors());
        }
    }

    //3.如果没有显式声明ValidResult,将抛异常
    @MethodValidate
    public void testController2(@ContainsEnum(value = {EnumTest.class}) int a,
                               @Min(3)@NotNull Integer i) {
    }

    //4.集成了Hibernate-Validator的Bean校验,并且对校验结果自定义控制
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
```


> **另外validator也支持hibernate-validator的自带注解以及校验器自定义扩展**，详情见：
**[超时空传送门](http://hibernate.org/validator/documentation/)**
