package com.imooc.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author
 * @create 2020-08-27-10:21
 */
public interface ItemsCommentsMapperCustom {

    /**
     *保存评价列表
     * @param map
     */
    public void saveComments(@Param("paramsMap") Map<String,Object> map);
 }
