package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom  {

    public List<ItemCommentVo> queryItemComments(@Param("paramsMap") Map<String,Object> map);

    public List<SearchItemsVo> searchItems(@Param("paramsMap") Map<String,Object> map);

    public List<SearchItemsVo> searchItemsByThirdCat(@Param("paramsMap") Map<String,Object> map);
}