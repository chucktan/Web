package com.imooc.controller;

import com.imooc.pojo.bo.ShopcartBo;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api(value = "购物车接口Controller",tags = {"购物车接口相关的api"})
@RequestMapping("shopcart")
@RestController
public class ShopcatController extends  BaseController {

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车",httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
           @RequestParam String userId,
           @RequestBody ShopcartBo shopcartBo,
           HttpServletRequest request,
           HttpServletResponse response
    ){
        if (StringUtils.isBlank(userId)){
            return  IMOOCJSONResult.errorMsg("");
        }

        System.out.println(shopcartBo);
        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        // 需要判断当前购物车中是否包含已经存在的商品，如果存在则累加购买数量
         String shopCartStr = redisOperator.get(FOODIE_SHOPCRAT + ":" +userId);
         List<ShopcartBo> shopcartList = null;
         if (StringUtils.isNotBlank(shopCartStr)) {
             //redis中已经有购物车了
             shopcartList = JsonUtils.jsonToList(shopCartStr, ShopcartBo.class);
             //判断购物车中是否存在已有商品，如果有的话counts累加
             boolean isHaving = false;
             for (ShopcartBo sc : shopcartList) {
                 String tmpSpecId = sc.getSpecId();
                 if (tmpSpecId.equals(shopcartBo.getSpecId())) {
                     sc.setBuyCounts(sc.getBuyCounts() + shopcartBo.getBuyCounts());
                     isHaving = true;
                 }
             }
             if (!isHaving) {
                 shopcartList.add(shopcartBo);
             }

         }else {
             //redis中没有购物车
             shopcartList = new ArrayList<>();
             //直接添加到购物车中
             shopcartList.add(shopcartBo);
         }

         //覆盖现有redis中的购物车
        redisOperator.set(FOODIE_SHOPCRAT+":"+userId,JsonUtils.objectToJson(shopcartList));

        return  IMOOCJSONResult.ok();

   }

    @ApiOperation(value = "从购物车中删除商品",notes = "从购物车中删除商品",httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if (StringUtils.isBlank(userId)|| StringUtils.isBlank(itemSpecId)){
            return  IMOOCJSONResult.errorMsg("参数不能为空");
        }

        //用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的数据
        List<ShopcartBo> shopcartList = null;
        String shopCartStr = redisOperator.get(FOODIE_SHOPCRAT + ":" +userId);
        if (StringUtils.isNotBlank(shopCartStr)){
            //redis中已经有购物车了
            shopcartList = JsonUtils.jsonToList(shopCartStr,ShopcartBo.class);
            //判断购物车中是否有该商品，如果有则删除
            for (ShopcartBo sc: shopcartList){
                String tmpSpId = sc.getSpecId();
                if(tmpSpId.equals(itemSpecId)){
                    shopcartList.remove(sc);
                    break;
                }
            }
        }

        //覆盖现有的redis中的购物车
        redisOperator.set(FOODIE_SHOPCRAT+":"+userId,JsonUtils.objectToJson(shopCartStr));

        return  IMOOCJSONResult.ok();

    }

}
