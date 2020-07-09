package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemInfoVo;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @create 2020-07-02-11:23
 */
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController  extends  BaseController{

    @Autowired
    private ItemService itemService;


    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);

        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(item);
        itemInfoVo.setItemImgList(itemImgList);
        itemInfoVo.setItemParams(itemsParam);
        itemInfoVo.setItemSpecList(itemsSpecList);

        return IMOOCJSONResult.ok(itemInfoVo);

    }

    @ApiOperation(value = "查询商品评论数量",notes = "查询商品评论数量",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public  IMOOCJSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @RequestParam  String itemId){
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        CommentLevelCountsVo commentLevelCountsVo = itemService.queryCommentCounts(itemId);

        return  IMOOCJSONResult.ok(commentLevelCountsVo);

    }

    @ApiOperation(value = "查询商品评论",notes = "查询商品评论",httpMethod = "GET")
    @GetMapping("/comments")
    public  IMOOCJSONResult comments(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @RequestParam  String itemId,
            @ApiParam(name = "level", value = "评论等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }

        if (pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId,level,page,pageSize);

        return  IMOOCJSONResult.ok(pagedGridResult);
    }
}