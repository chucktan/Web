package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBo;
import com.imooc.pojo.vo.MyCommentVo;
import com.imooc.service.BaseService;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2020-08-28-15:58
 */
@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return  orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBo> commentList) {
        //1.保存评价 item_comments

        for (OrderItemsCommentBo oic:commentList){
            oic.setCommentId(sid.nextShort());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("commentList",commentList);
        itemsCommentsMapperCustom.saveComments(map);

        //2.修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setUpdatedTime(new Date());
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        //3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page,Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        PageHelper.startPage(page,pageSize);

        List<MyCommentVo> list  = itemsCommentsMapperCustom.queryMyComments(map);
        return setterPagedGrid(list,page);


    }
}
