package com.remember_app.remember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.remember_app.remember.entity.User;
import com.remember_app.remember.entity.dto.UserLoginDTO;
import com.remember_app.remember.entity.dto.UserRegisterDTO;
import com.remember_app.remember.mapper.UserMapper;
import com.remember_app.remember.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    @Transactional
    public void register(UserRegisterDTO registerDTO){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, registerDTO.getUsername());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("该账号(邮箱)已被注册，请直接登录。");
        }

        User user = new User();
        user.setUserAccount(registerDTO.getUsername());
        user.setUserEmail(registerDTO.getUsername());
        user.setUserPassword(registerDTO.getPassword());
        user.setUserName("用户_" + registerDTO.getUsername().split("@")[0]);
        user.setUserStatus(1);
        user.setUserCreatedAt(LocalDateTime.now());
        user.setUserUpdatedAt(LocalDateTime.now());

        if (!this.save(user)) {
            throw new RuntimeException("注册失败，请重新注册。");
        }
    }

    @Override
    public User login(UserLoginDTO loginDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, loginDTO.getUsername());
        User user = this.getOne(queryWrapper);

        if (user == null) {
            throw new RuntimeException("账号不存在，请重新输入。");
        }

        if ( !user.getUserAccount().equals(loginDTO.getUsername()) || !user.getUserPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("密码不正确，请重新输入。");
        }

        if (user.getUserStatus() == 0) {
            throw new RuntimeException("该账号已被禁用，请联系管理员。");
        }

        user.setUserUpdatedAt(LocalDateTime.now());
        this.updateById(user);

        user.setUserPassword(null);
        return user;
    }
}
