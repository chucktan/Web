package com.imooc.service;

import org.apache.catalina.User;

public interface UserService {
    /**
     * 判断用户是否存在
    */
    public  boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     */
    public User createUser(UserBO userBO);
}
