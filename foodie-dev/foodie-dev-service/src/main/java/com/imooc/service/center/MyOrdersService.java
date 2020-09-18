package com.imooc.service.center;


import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVo;
import com.imooc.utils.PagedGridResult;

public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
   public PagedGridResult queryMyOrders(String userId,Integer orderStatus,Integer page,Integer pageSize);

    /**
     * 更新订单状态,订单状态-->商家发货
     * @param orderId
     */
    public  void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单（验证用户和订单是否有关联关系）
     * @param orderId
     * @param userId
     */
    public Orders queryMyOrder(String orderId, String userId);


    /**
     * 更新订单状态->确认收货
     * @param orderId
     * @return
     */
    public boolean updateReceiveOrderStatus(String orderId);


    /**
     * 删除订单（逻辑删除）
     * @param userId
     * @param orderId
     * @return
     */
    public boolean deleteOrder(String userId,String orderId);

    /**
     * 查询订单对应状态数量
     * @param userId
     */
    public OrderStatusCountsVo getOrderStatusCounts(String userId);

 /**
  * 获得分页的订单动向
  * @param userId
  * @param page
  * @param pageSize
  * @return
  */
 public PagedGridResult getOrdersTrend(String userId,Integer page,Integer pageSize);
}
