package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @create 2020-08-25-16:09
 */
@Api(value = "用户中心-订单列表",tags = {"用户中心-订单列表相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

//    @Autowired
//    private MyOrdersService myOrdersService;


    @ApiOperation(value = "查询订单列表",tags = "查询订单列表",httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(
            @ApiParam(name = "userId",value = "用户ID",required = true)
            @RequestParam  String userId,
            @ApiParam(name = "orderStatus",value = "订单状态",required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize){

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }

        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = myOrdersService.queryMyOrders(userId,orderStatus,page,pageSize);

        return  IMOOCJSONResult.ok(pagedGridResult);
    }


    //商家发货后没有后端，所以这个接口仅仅只用于模拟
    @ApiOperation(value = "商家发货",tags = "商家发货",httpMethod = "POST")
    @PostMapping("/deliver")
    public IMOOCJSONResult deliver(
            @ApiParam(name = "orderId",value = "订单Id",required = true)
            @RequestParam String orderId){

        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        myOrdersService.updateDeliverOrderStatus(orderId);


        return  IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户确认订单",tags = "用户确认订单",httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(
            @ApiParam(name = "orderId",value = "订单Id",required = true)
            @RequestParam    String orderId,
            @ApiParam(name = "userId",value = "用户Id",required = true)
            @RequestParam    String userId){

        IMOOCJSONResult checkResult = checkUserOrder(orderId,userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return  checkResult;
        }

        boolean resultFlag = myOrdersService.updateReceiveOrderStatus(orderId);

        if (!resultFlag){
            return  IMOOCJSONResult.errorMsg("订单确认收货失败!");
        }

        return  IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "用户删除订单",tags = "用户删除订单",httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(
            @ApiParam(name = "orderId",value = "订单Id",required = true)
            @RequestParam    String orderId,
            @ApiParam(name = "userId",value = "用户Id",required = true)
            @RequestParam     String userId){

        IMOOCJSONResult checkResult = checkUserOrder(orderId,userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return  checkResult;
        }

        boolean resultFlag =  myOrdersService.deleteOrder(userId,orderId);
        if (!resultFlag){
            return  IMOOCJSONResult.errorMsg("订单删除失败!");
        }
        return  IMOOCJSONResult.ok();
    }


//    /**
//     * 用于验证用户和订单是否有关联关系，避免非法调用
//     * @param orderId
//     * @param userId
//     * @return
//     */
//    private  IMOOCJSONResult checkUserOrder(String orderId,String userId){
//
//        Orders order = myOrdersService.queryMyOrder(orderId,userId);
//
//        if (order == null){
//            return IMOOCJSONResult.errorMsg("订单不存在");
//        }
//
//        return  IMOOCJSONResult.ok();
//    }
}
