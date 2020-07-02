package com.imooc.enums;

/**
 * @DESC :是否 枚举
 */
public enum  YesOrNo {

    YES(1,"是"),
    NO(0,"否");


    public  final  Integer type;
    public  final  String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
