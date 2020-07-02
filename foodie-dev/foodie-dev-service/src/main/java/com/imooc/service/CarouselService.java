package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * @author
 * @create 2020-07-02-11:06
 */
public interface CarouselService {

    /**
     * 查询所有的轮播图列表
     * @param isShow
     * @return
     */

    public List<Carousel> queryAll(Integer isShow);
 }
