package com.remember_app.remember.controller;

import com.remember_app.remember.common.JwtUtils;
import com.remember_app.remember.common.Result;
import com.remember_app.remember.entity.User;
import com.remember_app.remember.entity.dto.UserLoginDTO;
import com.remember_app.remember.entity.dto.UserRegisterDTO;
import com.remember_app.remember.exception.UserException;
import com.remember_app.remember.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
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
    public Result update(@RequestBody User user) {
        try {
            if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
                user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            } else {
                user.setUserPassword(null);
            }

            userService.updateById(user);
        }catch (UserException e){
            return Result.error(e.getMessage());
        }
        return Result.success();
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
