package validate.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/5/3
 */
public class CustomModel {

    @NotEmpty
    private String reg;

    @Max(value = 6,message = "最大不能超过${value}")
    private int age ;

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
