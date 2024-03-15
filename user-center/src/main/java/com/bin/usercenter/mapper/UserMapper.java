package com.bin.usercenter.mapper;

import com.bin.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zhangbin
* @description 针对表【user(user table)】的数据库操作Mapper
* @createDate 2024-03-15 16:04:32
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




