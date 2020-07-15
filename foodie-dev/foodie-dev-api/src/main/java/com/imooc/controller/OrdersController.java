package com.imooc.controller;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.bo.SubmitOrderBo;
import com.imooc.service.OrderService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Controller
@Api(value = "订单相关",tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends  BaseController{

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单",notes = "创建下单",httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(
            @RequestBody SubmitOrderBo submitOrderBo,
            HttpServletRequest request,
            HttpServletResponse response){

        if (submitOrderBo.getPayMethod()!= PayMethod.WEIXIN.type && submitOrderBo.getPayMethod()!= PayMethod.ALIPAY.type){
            return  IMOOCJSONResult.errorMsg("支付方式不能为空");
        }
        //1.创建订单
        String orderId = orderService.createOrder(submitOrderBo);

        //2.创建订单后，移除购物车中已经结算（已提交）的商品
        //TODO 整合redis后，完善购物车中的已结算商品，并且同步到前端的cookie
        CookieUtils.setCookie(request,response,FOODIE_SHOPCRAT,"",true);
        //3.向支付中心发送当前订单，用于保存支付中心的订单数据

        return  IMOOCJSONResult.ok(orderId);
    }

    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){

        orderService.updateOrderStatus(merchantOrderId,OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }
}
