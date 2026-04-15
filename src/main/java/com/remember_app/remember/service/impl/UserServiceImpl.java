package com.remember_app.remember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.remember_app.remember.entity.Question;
import com.remember_app.remember.entity.QuestionBank;
import com.remember_app.remember.entity.User;
import com.remember_app.remember.entity.dto.UserLoginDTO;
import com.remember_app.remember.entity.dto.UserRegisterDTO;
import com.remember_app.remember.mapper.QuestionBankMapper;
import com.remember_app.remember.mapper.QuestionMapper;
import com.remember_app.remember.mapper.UserMapper;
import com.remember_app.remember.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private QuestionBankMapper questionBankMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    @Transactional
    public void register(UserRegisterDTO registerDTO){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, registerDTO.getUserAccount());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("该账号(邮箱)已被注册，请直接登录。");
        }

        User user = new User();
        user.setUserAccount(registerDTO.getUserAccount());
        user.setUserEmail(registerDTO.getUserAccount());

        String encodePassword = passwordEncoder.encode(registerDTO.getPassword());
        user.setUserPassword(encodePassword);

        if (registerDTO.getUserAccount() == null || registerDTO.getUserAccount().isEmpty()) {
            throw new RuntimeException("用户账号不能为空");
        }
        user.setUserName("用户_" + registerDTO.getUserAccount().split("@")[0]);
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
        queryWrapper.eq(User::getUserAccount, loginDTO.getUserAccount());
        User user = this.getOne(queryWrapper);

        if (user == null) {
            throw new RuntimeException("账号不存在，请重新输入。");
        }

        String storeHash = user.getUserPassword();
        String inputHash = loginDTO.getPassword();
        boolean isPasswordMatch = BCrypt.checkpw(inputHash, storeHash);

        if (!isPasswordMatch) {
            throw new RuntimeException("账号或密码不正确，请重新输入。");
        }

        if (user.getUserStatus() == 0) {
            throw new RuntimeException("该账号已被禁用，请联系管理员。");
        }

        user.setUserLastLoginAt(LocalDateTime.now());
        this.updateById(user);

        user.setUserPassword(null);
        return user;
    }

    @Override
    public boolean removeById(Serializable id) {
        LambdaQueryWrapper<Question> questionWrapper = new LambdaQueryWrapper<>();
        questionWrapper.eq(Question::getUserId, id);
        questionMapper.delete(questionWrapper);

        LambdaQueryWrapper<QuestionBank> qbWrapper = new LambdaQueryWrapper<>();
        qbWrapper.eq(QuestionBank::getUserId, id);
        questionBankMapper.delete(qbWrapper);

        return super.removeById(id);
    }
}
