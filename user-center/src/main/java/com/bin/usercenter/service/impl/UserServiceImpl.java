package com.bin.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.usercenter.model.domain.User;
import com.bin.usercenter.service.UserService;
import com.bin.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author zhangbin
* @description 针对表【user(user table)】的数据库操作Service实现
* @createDate 2024-03-15 16:04:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 校验用户名、密码、确认密码是否为null
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            // 报错
            return -1;
        }
        // 用户名长度是否小于4
        if (userAccount.length() < 4) {
            return -1;
        }
        // 校验密码长度
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        // 校验是否含有特殊字符
        String validPatter = "[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPatter).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码是否相同
        if (userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账号是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        // 密码加密
        final String SALT = "BINANDBAI";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 插入数据
        User user = new User();
        user.setUserAccount("2662728664");
        user.setUserPassword(encryptPassword);
        int res = userMapper.insert(user);
        if (!(res > 0)){
            return -1;
        }
        return user.getId();
    }
}




