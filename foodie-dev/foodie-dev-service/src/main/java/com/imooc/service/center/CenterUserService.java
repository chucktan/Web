package com.imooc.service.center;


import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBo;

public interface CenterUserService {

    /**
     * 根据用户ID,查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);


    /**
     * 修改用户信息
     * @param userId
     * @param  centerUserBo
     * @return
     */
    public Users updateUserInfo(String userId, CenterUserBo centerUserBo);
}
