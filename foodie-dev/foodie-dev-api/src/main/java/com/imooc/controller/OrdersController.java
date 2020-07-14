package com.imooc.controller;

import com.imooc.enums.PayMethod;
import com.imooc.pojo.bo.SubmitOrderBo;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@Api(value = "订单相关",tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController {



    @ApiOperation(value = "用户下单",notes = "创建下单",httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(
            @RequestBody SubmitOrderBo submitOrderBo
    ){

        if (submitOrderBo.getPayMethod()!= PayMethod.WEIXIN.type && submitOrderBo.getPayMethod()!= PayMethod.ALIPAY.type){
            return  IMOOCJSONResult.errorMsg("支付方式不能为空");
        }
        //1.创建订单
        //2.创建订单后，移除购物车中已经结算（已提交）的商品
        //3.向支付中心发送当前订单，用于保存支付中心的订单数据

        return  IMOOCJSONResult.ok();
    }


}
