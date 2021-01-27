package com.imooc.service.impl;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.ShopcartBo;
import com.imooc.pojo.bo.SubmitOrderBo;
import com.imooc.pojo.vo.MerchantOrdersVo;
import com.imooc.pojo.vo.OrderVo;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2020-07-14-14:16
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired

    private OrdersMapper ordersMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVo createOrder(List<ShopcartBo> shopcartBoList ,SubmitOrderBo submitOrderBo) {

        String userId = submitOrderBo.getUserId();
        String addressId = submitOrderBo.getAddressId();
        String itemSpecIds  = submitOrderBo.getItemSpecIds();
        Integer payMethod = submitOrderBo.getPayMethod();
        String leftMsg = submitOrderBo.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;
        UserAddress userAddress = addressService.queryUserAddress(userId,addressId);
        //1.新订单数据保存
        String orderId = sid.nextShort();
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(userAddress.getReceiver());
        newOrder.setReceiverMobile(userAddress.getMobile());
        newOrder.setReceiverAddress(userAddress.getProvince()+" "+userAddress.getCity()+" "+userAddress.getDistrict()+" "+userAddress.getDetail());
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        //2.循环根据itemSpecIds保存订单商品信息表
        String itemSpecIdArr[] = itemSpecIds.split(",");
        Integer totalAmount = 0;//商品原件累计
        Integer realPayAmount = 0;//优惠后的实际支付价格累计
        List<ShopcartBo> toBeRemovedShopcartList = new ArrayList<>();
        for (String itemSpecId:itemSpecIdArr){
            //整合redis后，商品购买的数量重新从redis的购物车中获取
            ShopcartBo cart = getBuyCountsFromShopcart(shopcartBoList,itemSpecId);
            int buyCount = cart.getBuyCounts();
            toBeRemovedShopcartList.add(cart);

            //2.1 根据规格id,查询规格的具体信息，主要获取价格
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemsSpec.getPriceNormal() * buyCount;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCount;

            //2.2 根据商品id,获取商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            //2.3 循环保存子订单数据到数据库
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCount);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);
            //2.4 在用户提交订单后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId,buyCount);

        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);

        //3.保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        //4.构建商户订单，用于传给支付中心
        MerchantOrdersVo merchantOrdersVo = new MerchantOrdersVo();
        merchantOrdersVo.setMerchantOrderId(orderId);
        merchantOrdersVo.setMerchantUserId(userId);
        merchantOrdersVo.setAmount(realPayAmount+postAmount);
        merchantOrdersVo.setPayMethod(payMethod);

        //5.构建自定义Vo
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        orderVo.setMerchantOrdersVo(merchantOrdersVo);
        orderVo.setToBeRemovedShopcartList(toBeRemovedShopcartList);

        return orderVo;
    }

    /**
     * 从redis中购物车获取商品，目的counts
     * @param shopcartBoList
     * @param itemSpecId
     * @return
     */
    private ShopcartBo  getBuyCountsFromShopcart(List<ShopcartBo> shopcartBoList,String itemSpecId){

        for ( ShopcartBo cart:shopcartBoList){
            if (cart.getSpecId().equals(itemSpecId)){
                return  cart;
            }
        }
        return  null;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return  orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {
        //查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        for (OrderStatus os:list){
            //获得订单创建时间
            Date createTime = os.getCreatedTime();
            //和当前时间做对比
            int days = DateUtil.daysBetween(createTime,new Date());

            if (days >= 1){
                //超过一天，关闭订单
                doCloseOrder(os.getOrderId());
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId){
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
