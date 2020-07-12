package com.imooc.controller;

import com.imooc.pojo.UserAddress;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@Api(value = "收货地址相关",tags = {"收货地址相关的api接口"})
@RequestMapping("address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;
    /**
     * 用户在确认订单页面，可以针对收货地址做一下操作：
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */

    @ApiOperation(value = "根据用户id查询收货地址列表",notes = "根据用户id查询收货地址列表",httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult list(
            @ApiParam(name = "userId",value = "用户Id",required = true)
            @RequestParam String userId
    ){
        if (StringUtils.isBlank(userId)){
            return  IMOOCJSONResult.errorMsg("用户id不能为空");
        }
        List <UserAddress> list = addressService.queryAll(userId);
        return  IMOOCJSONResult.ok();
    }

}
