package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBo;

import java.util.List;

/**
 * @author
 * @create 2020-07-02-11:06
 */
public interface AddressService {

    /**
     * 根据用户ID查询用户收货地址列表
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);


    /**
     * 用户新增地址
     */
    public void addNewUserAddress(AddressBo addressBo);
 }
