package com.imooc.controller;

import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVo;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

@Controller
public class BaseController {

    @Autowired
    private RedisOperator redisOperator;

    public  static  final  String FOODIE_SHOPCRAT = "shopcrat";
    public  static  final  Integer COMMON_PAGE_SIZE = 10;
    public  static  final  Integer PAGE_SIZE = 20;

    public  static  final  String REDIS_USER_TOKEN = "redis_user_token";

    //支付中心调用地址
    public  static  final  String PAYMENTURL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功 ->支付中心->天天吃货平台
    //                      |->回调通知的url
    public  static  final  String PAYRETURNURL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

//    public  static  final  String PAYRETURNURL = "http://api.btrade.cloud:8088/orders/notifyMerchantOrderPaid";

    //支付中心账号密码
    public  static  final  String IMOOCUSERID = "7295794-2316581614";
    public  static  final  String PASSWORD = "3t59-g0jg-oikt-rpio";

    //用户头像位置
    public  static  final  String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces"+ File.separator+"images"+ File.separator+"foodie"+File.separator+"faces";


    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法调用
     * @param orderId
     * @param userId
     * @return
     */
    public IMOOCJSONResult checkUserOrder(String orderId, String userId){

        Orders order = myOrdersService.queryMyOrder(orderId,userId);

        if (order == null){
            return IMOOCJSONResult.errorMsg("订单不存在");
        }

        return  IMOOCJSONResult.ok(order);
    }

    /**
     * 实现用户的redis会话
     * @param userResult
     * @return
     */
    public UsersVo convertUsersVo(Users userResult){

        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN+":"+userResult.getId(),uniqueToken);

        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(userResult,usersVo);
        usersVo.setUserUniqueToken(uniqueToken);

        return  usersVo;
    }
}
