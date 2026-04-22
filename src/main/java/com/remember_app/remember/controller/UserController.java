package com.remember_app.remember.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.remember_app.remember.common.JwtUtils;
import com.remember_app.remember.common.Result;
import com.remember_app.remember.entity.User;
import com.remember_app.remember.entity.dto.UserLoginDTO;
import com.remember_app.remember.entity.dto.UserRegisterDTO;
import com.remember_app.remember.entity.dto.UserUpdateDTO;
import com.remember_app.remember.exception.UserException;
import com.remember_app.remember.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 新增用户
     */
    @PostMapping
    public Result save(@RequestBody User user) {
        try {
            userService.save(user);
        }catch (UserException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 更新用户
     */
    @PutMapping
    public Result update(@RequestBody UserUpdateDTO dto) {
        try {
            if (dto.getUserId() == null) {
                return Result.error("用户ID不能为空");
            }

            User dbUser = userService.getById(dto.getUserId());
            if (dbUser == null) {
                return Result.error("用户不存在");
            }

            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.eq("user_id", dto.getUserId());

            if (dto.getUserName() != null) {
                wrapper.set("user_name", dto.getUserName());
            }
            if (dto.getUserEmail() != null) {
                wrapper.set("user_email", dto.getUserEmail());
            }
            if (dto.getUserPhoneNum() != null) {
                wrapper.set("user_phone_num", dto.getUserPhoneNum());
            }
            if (dto.getUserAvatar() != null) {
                wrapper.set("user_avatar", dto.getUserAvatar());
            }

            // 密码更新逻辑
            if (dto.getUserPassword() != null && !dto.getUserPassword().isEmpty()) {
                if (dto.getOldPassword() == null || dto.getOldPassword().isEmpty()) {
                    return Result.error("请输入原密码");
                }
                if (!passwordEncoder.matches(dto.getOldPassword(), dbUser.getUserPassword())) {
                    return Result.error("原密码错误");
                }
                wrapper.set("user_password", passwordEncoder.encode(dto.getUserPassword()));
            }

            userService.update(wrapper);

            User updatedUser = userService.getById(dto.getUserId());
            return Result.success(updatedUser);

        } catch (UserException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询指定 id 的用户
     */
    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id) {
        try {
            User user = userService.getById(id);
            return Result.success(user);
        }catch (UserException e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有用户
     */
    @GetMapping
    public Result list() {
        try {
            List<User> list = userService.list();
            return Result.success(list);
        }catch (UserException e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除用户数据
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            userService.removeById(id);
        }catch (UserException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO registerDTO) {
        try {
            userService.register(registerDTO);
            return Result.success();
        } catch (UserException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO loginDTO) {
        try {
            User user = userService.login(loginDTO);

            if (user != null) {
                String token = jwtUtils.createToken(user);

                HashMap<String, Object> res = new HashMap<>();
                res.put("user", user);
                res.put("token", token);
                return Result.success(res);
            }
        } catch (UserException e) {
            return Result.error(e.getMessage());
        }
        return Result.error("登录失败");
    }
}
