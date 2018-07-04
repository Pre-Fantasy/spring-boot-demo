package com.example.demo.dao;

import com.example.demo.core.universal.Mapper;
import com.example.demo.model.RolePerm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  @author Pre_fantasy
 *  @create 2018/6/30 17:31
 *  @desc   角色权限Mapper
 */
public interface RolePermMapper extends Mapper<RolePerm> {

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/30 17:33
     *  @param  {String userId}
     *  @return List<String>
     *  @desc   通过用户id获取用户权限值
     */
    List<String> getPermsByUserId(String userId);
}