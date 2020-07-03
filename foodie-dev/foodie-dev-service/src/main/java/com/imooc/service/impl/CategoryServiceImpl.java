package com.imooc.service.impl;

import com.imooc.mapper.CategoryMapper;
import com.imooc.pojo.Category;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author
 * @create 2020-07-03-14:47
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryAllRootLevelCat() {
        Example categoryExample = new Example(Category.class);

        Example.Criteria criteria = categoryExample.createCriteria();
        criteria.andEqualTo("type",1);

        List<Category> result = categoryMapper.selectByExample(categoryExample);

        return result;

    }
}
