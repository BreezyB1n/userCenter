package com.bin.usercenter;
import java.util.Date;

import com.bin.usercenter.mapper.UserMapper;
import com.bin.usercenter.model.domain.User;
import com.bin.usercenter.service.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = UserCenterApplication.class)
class UserCenterApplicationTests {
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Test
    void test() {
        User user = new User();
        user.setUser("bin");
        user.setUserAccount("224523");
        user.setAvatarUrl("asd");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setEmail("zzz");
        user.setPhone("12345677890");

        boolean res = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(res);
    }

}
