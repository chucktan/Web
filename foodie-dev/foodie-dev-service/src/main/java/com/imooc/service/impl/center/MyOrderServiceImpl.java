package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.OrdersMapperCustom;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.MyOrdersVo;
import com.imooc.pojo.vo.OrderStatusCountsVo;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrderServiceImpl implements MyOrdersService {


    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        if (orderStatus !=null){
            map.put("orderStatus",orderStatus);
        }

        PageHelper.startPage(page,pageSize);
        List<MyOrdersVo> list = ordersMapperCustom.queryMyOrders(map);

        return setterPagedGrid(list,page);

    }

    private  PagedGridResult setterPagedGrid(List<?> list,Integer page){

        PageInfo<?> pageList = new PageInfo<>(list);

        //重新封装
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageList.getPages());
        pagedGridResult.setRecords(pageList.getTotal());
        return pagedGridResult ;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        criteria.andEqualTo("orderStatus",OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder,example);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String orderId, String userId) {
        Orders order = new Orders();
        order.setId(orderId);
        order.setUserId(userId);
        order.setIsDelete(YesOrNo.NO.type);

        return  ordersMapper.selectOne(order);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        orderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        criteria.andEqualTo("orderStatus",OrderStatusEnum.WAIT_RECEIVE.type);

        int result =   orderStatusMapper.updateByExampleSelective(orderStatus,example);

        return  result == 1?true:false;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {

        Orders order = new Orders();
        order.setIsDelete(YesOrNo.YES.type);
        order.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",orderId);
        criteria.andEqualTo("userId",userId);

        int result = ordersMapper.updateByExampleSelective(order,example);

        return result == 1?true:false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVo getOrderStatusCounts(String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        map.put("orderStatus",OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus",OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus",OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus",OrderStatusEnum.SUCCESS.type);
        map.put("isComment",YesOrNo.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(map);


        OrderStatusCountsVo countsVo = new OrderStatusCountsVo(waitPayCounts,waitDeliverCounts,waitReceiveCounts,waitCommentCounts);

        return countsVo;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        PageHelper.startPage(page,pageSize);
        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(map);

        return setterPagedGrid(list,page);

    }
}
