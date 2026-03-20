package com.remember_app.remember.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.remember_app.remember.entity.User;
import com.remember_app.remember.mapper.UserMapper;
import com.remember_app.remember.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
