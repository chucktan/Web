package com.imooc.enums;


/**
 * @DESC :商品评价 枚举
 */
public enum  CommentLevel {

    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");


    public  final  Integer type;
    public  final  String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
