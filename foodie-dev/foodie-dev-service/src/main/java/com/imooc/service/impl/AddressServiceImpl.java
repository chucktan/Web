package com.imooc.service.impl;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBo;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper  userAddressMapper;
    @Autowired
    private  Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBo addressBo) {
        //1.判断当前用户是否存在地址，如果没有，则新增为“默认地址”
        Integer isDefault = YesOrNo.NO.type;
        List<UserAddress> addressList = queryAll(addressBo.getUserId());
        if (addressList == null||addressList.isEmpty()||addressList.size()==0){
            isDefault = YesOrNo.YES.type;
        }
        String addressId = sid.nextShort();
        //2.保存地址到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo,newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBo addressBo) {
        String addressId = addressBo.getAddressId();

        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo,pendingAddress);

        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);

        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        //1.查找默认地址，设置为不默认
            UserAddress queryAddress = new UserAddress();
            queryAddress.setUserId(userId);
            queryAddress.setIsDefault(YesOrNo.YES.type);
            List<UserAddress> list = userAddressMapper.select(queryAddress);
            for (UserAddress ua:list){
                ua.setIsDefault(YesOrNo.NO.type);
                userAddressMapper.updateByPrimaryKeySelective(ua);
            }

        //2.根据地址id修改为默认的地址
            UserAddress defaultAddress = new UserAddress();
            defaultAddress.setUserId(userId);
            defaultAddress.setId(addressId);
            defaultAddress.setIsDefault(YesOrNo.YES.type);
            userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }
}
