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
     * @param addressBo
     */
    public void addNewUserAddress(AddressBo addressBo);

    /**
     * 修改用户地址
     * @param addressBo
     */
    public void updateUserAddress(AddressBo addressBo);

    /**
     *
     * 根据用户id,地址id删除用户地址
     * @param userId
     * @param addressId
     */
    public  void  deleteUserAddress(String userId,String addressId);

    /**
     * 根据用户id,地址id设置默认地址
     * @param userId
     * @param addressId
     */
    public  void  updateUserAddressToBeDefault(String userId,String addressId);


    /**
     * 根据用户id,地址id查询地址信息
     * @param userId
     * @param addressId
     */
    public  UserAddress  queryUserAddress(String userId,String addressId);

 }
