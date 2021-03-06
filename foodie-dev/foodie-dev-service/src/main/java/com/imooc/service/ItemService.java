package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ShopcartVo;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @author
 * @create 2020-07-02-11:06
 */
public interface ItemService {

    /**
     * 根据商品ID查询详情
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品ID查询商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品ID查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品ID查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品Id查询商品的评价等级数量
     * @param itemId
     * @return
     */
    public CommentLevelCountsVo queryCommentCounts(String itemId);

    /**
     * 根据商品Id,评价等级查询商品的评价列表(分页)
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);


    /**
     * 搜索商品列表(分页)
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据分类ID搜索商品列表(分页)
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     *根据规格Ids查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @param specIds
     * @return
     */
    public  List<ShopcartVo> queryItemsBySpecIds(String specIds);


    /**
     * 根据商品规格Id获取规格对象的具体信息
     * @param specId
     * @return
     */
    public  ItemsSpec queryItemSpecById(String specId);

    /**
     * 根据商品Id查询商品主图URl地址
     * @param itemId
     * @return
     */
    public String queryItemMainImgById(String itemId);


    /**
     * 减少库存
     * @param specId
     * @param buyCounts
     */
    public void decreaseItemSpecStock(String specId,int buyCounts);
 }
