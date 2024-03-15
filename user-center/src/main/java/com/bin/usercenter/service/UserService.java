package com.bin.usercenter.service;

import com.bin.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangbin
* @description 针对表【user(user table)】的数据库操作Service
* @createDate 2024-03-15 16:04:32
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册业务
     * @param userAccount userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
}
