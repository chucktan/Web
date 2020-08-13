package com.imooc.service.center;


import com.imooc.pojo.Users;

public interface CenterUserService {

    /**
     * 根据用户ID,查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);
}
