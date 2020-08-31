package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.bo.center.OrderItemsCommentBo;

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

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public  void saveComments(String orderId,String userId,List<OrderItemsCommentBo> commentList);
}
