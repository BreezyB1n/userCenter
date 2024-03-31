package com.bin.usercenter.service;

import com.bin.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author zhangbin
* @description 针对表【user(user table)】的数据库操作Service
* @createDate 2024-03-15 16:04:32
*/
public interface UserService extends IService<User> {
    // 放在UserService既可以被外部访问，也可以被实现类获取


    /**
     * 用户注册业务
     * @param userAccount userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登陆
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param servletRequest
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest servletRequest);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafeUser(User user);
}
