package com.remember_app.remember.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.remember_app.remember.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
