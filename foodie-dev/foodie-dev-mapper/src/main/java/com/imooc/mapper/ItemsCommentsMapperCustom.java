package com.imooc.mapper;

import com.imooc.pojo.vo.MyCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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
    public void saveComments(Map<String,Object> map);

    /**
     *查询评价列表
     * @param map
     * @return
     */
    public List<MyCommentVo> queryMyComments(@Param("paramsMap") Map<String,Object> map);
 }
