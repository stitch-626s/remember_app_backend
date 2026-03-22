package com.remember_app.remember;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.remember_app.remember.entity.User;
import com.remember_app.remember.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RememberApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

}
