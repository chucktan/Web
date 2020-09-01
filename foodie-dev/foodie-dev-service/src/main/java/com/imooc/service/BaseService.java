package com.imooc.service;

import com.github.pagehelper.PageInfo;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @author
 * @create 2020-09-01-15:13
 */
public class BaseService {


    public PagedGridResult setterPagedGrid(List<?> list, Integer page){

        PageInfo<?> pageList = new PageInfo<>(list);

        //重新封装
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageList.getPages());
        pagedGridResult.setRecords(pageList.getTotal());
        return pagedGridResult ;
    }
}
