package com.imooc.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public  static  final  String FOODIE_SHOPCRAT = "shopcrat";
    public  static  final  Integer COMMENT_PAGE_SIZE = 10;
    public  static  final  Integer PAGE_SIZE = 20;

    //支付中心调用地址
    public  static  final  String PAYMENTURL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功 ->支付中心->天天吃货平台
    //                      |->回调通知的url
    public  static  final  String PAYRETURNURL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    //支付中心账号密码
    public  static  final  String IMOOCUSERID = "7295794-2316581614";
    public  static  final  String PASSWORD = "3t59-g0jg-oikt-rpio";


}
