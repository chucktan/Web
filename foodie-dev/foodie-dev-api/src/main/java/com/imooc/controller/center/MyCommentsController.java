package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBo;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @create 2020-08-25-16:09
 */
@Api(value = "用户中心-评论列表",tags = {"用户中心-评论列表相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;


    @ApiOperation(value = "评论信息列表查询",tags = "评论信息列表查询",httpMethod = "POST")
    @PostMapping("/pending")
    public IMOOCJSONResult pending(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            String userId,
            @ApiParam(name = "orderId",value = "订单ID",required = true)
            String orderId){

        if (StringUtils.isBlank(userId)||StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        //判断用户和订单是否关联
        IMOOCJSONResult checkResult =  checkUserOrder(orderId,userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return  checkResult;
        }


        //判断该笔订单是否已经评价过，评价过了就不再继续
        Orders myOrder = (Orders)checkResult.getData();
        if (myOrder.getIsComment() == YesOrNo.YES.type){
            return IMOOCJSONResult.errorMsg("该笔订单已经评价！");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return  IMOOCJSONResult.ok(list);
    }


    @ApiOperation(value = "保存评论列表",tags = "保存评论列表",httpMethod = "POST")
    @PostMapping("/saveList")
    public IMOOCJSONResult saveList(
            @ApiParam(name = "userId",value = "用户ID",required = true)
                    String userId,
            @ApiParam(name = "orderId",value = "订单ID",required = true)
                    String orderId,
            List<OrderItemsCommentBo> commentList){

        if (StringUtils.isBlank(userId)||StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        //判断用户和订单是否关联
        IMOOCJSONResult checkResult =  checkUserOrder(orderId,userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return  checkResult;
        }

        //判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty()||commentList.size()==0){
            return  IMOOCJSONResult.errorMsg("评论内容不能为空！");
        }

        myCommentsService.saveComments(orderId,userId,commentList);
        return  IMOOCJSONResult.ok();
    }

}
