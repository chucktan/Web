package com.imooc.service;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBo;
import com.imooc.pojo.vo.OrderVo;

/**
 * @author
 * @create 2020-07-02-11:06
 */
public interface OrderService {


    /**
     * 创建订单相关信息
     * @param submitOrderBo
     */
    public OrderVo createOrder(SubmitOrderBo submitOrderBo);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void  updateOrderStatus(String orderId,Integer orderStatus);


    /**
     * 查询用户订单信息
     * @param orderId
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);
 }
