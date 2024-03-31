package com.bin.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.usercenter.model.domain.User;
import com.bin.usercenter.service.UserService;
import com.bin.usercenter.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bin.usercenter.model.constant.UserConstant.USER_LOGIN_STATE;


/**
* @author zhangbin
* @description 针对表【user(user table)】的数据库操作Service实现
* @createDate 2024-03-15 16:04:32
*/
@Service
@Slf4j // 打印日志来查看信息
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "BINANDBAI";
    // 用户登陆状态键

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
        if (!userPassword.equals(checkPassword)) {
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
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        int res = userMapper.insert(user);
        if (!(res > 0)){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // TODO: 出错将返回值修改为自定义异常
        // 校验用户名 密码 加密
        // 校验用户名、密码、确认密码是否为null
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            // 报错
            return null;
        }
        // 用户名长度是否小于4
        if (userAccount.length() < 4) {
            return null;
        }
        // 校验密码长度
        if (userPassword.length() < 8) {
            return null;
        }
        // 校验是否含有特殊字符
        String validPatter = "[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPatter).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 账号是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            log.info("User login failed, userAccount Cannot Match UserPassword");
            return null;
        }
        // 用户脱敏
        User safeUser = getSafeUser(user);
        // 记录用户登陆状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);
        // 记录用户登陆状态
        return safeUser;
    }

    /**
     *  获取脱敏用户
     * @return
     */
    @Override
    public User getSafeUser(User user) {
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUser(user.getUser());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setEmail(user.getEmail());
        safeUser.getUserRole();
        safeUser.setPhone(user.getPhone());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        return safeUser;
    }
}




