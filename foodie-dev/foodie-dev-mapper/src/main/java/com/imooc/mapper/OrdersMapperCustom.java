package com.imooc.mapper;

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
 }
