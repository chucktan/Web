package com.imooc.service.center;

import com.imooc.pojo.OrderItems;

import java.util.List;

/**
 * @author
 * @create 2020-08-28-15:58
 */
public interface MyCommentsService {

    /**
     *根据订单查询关联的商品
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);
}
