package com.imooc.mapper;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.vo.MyOrdersVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2020-08-27-10:21
 */
public interface OrdersMapperCustom {

    /**
     * 查询我的订单信息
     * @param map
     * @return
     */
    public List<MyOrdersVo> queryMyOrders(@Param("paramsMap") Map<String,Object> map);


    /**
     * 查询用户评价数量
     * @param map
     * @return
     */
    public int getMyOrderStatusCounts(@Param("paramsMap") Map<String,Object> map);

    /**
     * 查询订单信息
     * @param map
     * @return
     */
    public  List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String,Object> map);
 }
