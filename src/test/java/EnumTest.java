// Copyright (C) 2017 Meituan
// All rights reserved

/**
 * @author manatea
 * @version 1.0
 * @created 2017/6/9 AM10:41
 **/
public enum EnumTest {
    INTERGER(0),
    STRING(5);

    private int value;

    private EnumTest(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int code) {
        this.value = value;
    }

    public String toString() {
        return "EnumTest{value=" + this.value + '}';
    }
}
