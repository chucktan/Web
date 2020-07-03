package com.imooc.service;

import com.imooc.pojo.Category;

import java.util.List;

/**
 * @author
 * @create 2020-07-03-14:41
 */
public interface CategoryService {
    public List<Category> queryAllRootLevelCat();
}
