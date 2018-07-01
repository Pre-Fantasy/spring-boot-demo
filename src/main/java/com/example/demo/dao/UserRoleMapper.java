package com.example.demo.dao;

import com.example.demo.core.universal.Mapper;
import com.example.demo.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRoleMapper extends Mapper<UserRole> {

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/30 17:19
     *  @param  {String userId}
     *  @return List<String>
     *  @desc   根据用户id查询用户的角色
     */
    List<String> getRolesByUserId(String userId);
}