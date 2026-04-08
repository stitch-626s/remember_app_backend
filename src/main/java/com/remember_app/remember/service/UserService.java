package com.remember_app.remember.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.remember_app.remember.entity.User;
import com.remember_app.remember.entity.dto.UserLoginDTO;
import com.remember_app.remember.entity.dto.UserRegisterDTO;

public interface UserService extends IService<User> {
    void register(UserRegisterDTO userRegisterDTO);
    User login(UserLoginDTO userLoginDTO);
}
