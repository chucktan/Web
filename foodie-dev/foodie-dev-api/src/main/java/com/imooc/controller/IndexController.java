package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVo;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2020-07-02-11:23
 */
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {

        List<Carousel> carouselList = new ArrayList<>();
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)){
            carouselList = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel",JsonUtils.objectToJson(carouselList));
        } else {
            carouselList = JsonUtils.jsonToList(carouselStr,Carousel.class);
        }

        return IMOOCJSONResult.ok(carouselList);

        /**
         * 1. 后台运营系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
         * 2. 定时重置，每天3点重置
         * 3. 每个轮播图都有可能是一个广告，每个广告都有一个过期时间，过期了，再重置
         */


    }

    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {

        List<Category> categoryList = new ArrayList<>();
        String categoryStr = redisOperator.get("cats");
        if (StringUtils.isBlank(categoryStr)){
            categoryList = categoryService.queryAllRootLevelCat();
            redisOperator.set("cats",JsonUtils.objectToJson(categoryList));
        }else {
            categoryList = JsonUtils.jsonToList(categoryStr,Category.class);
        }

        return IMOOCJSONResult.ok(categoryList);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<CategoryVo> categoryVoList = new ArrayList<>();
        String CategoryVoStr = redisOperator.get("subCat:" + rootCatId);
        if (StringUtils.isBlank(CategoryVoStr)){
            categoryVoList = categoryService.getSubCatList(rootCatId);
            /**
             * 查询的key在redis中不存在
             * 对应的id在数据库也不存在，
             * 此时被非法用户攻击，大量的请求会直接打在db上，造成宕机，从而影响整个系统
             * 这种现象称之为缓存穿透。
             * 解决办法:把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             */
            if (categoryVoList != null && categoryVoList.size() > 0){
                redisOperator.set("subCat:"+rootCatId,JsonUtils.objectToJson(categoryVoList));
            }else {
                redisOperator.set("subCat:"+rootCatId,JsonUtils.objectToJson(categoryVoList),5*60);
            }

        }else {
            categoryVoList = JsonUtils.jsonToList(CategoryVoStr,CategoryVo.class);
        }


        return IMOOCJSONResult.ok(categoryVoList);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVo> newItemsVoList = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(newItemsVoList);
    }
}
