package com.example.demo.service;

import com.example.demo.model.UserRole;
import com.example.demo.core.universal.Service;

import java.util.List;

/**
* @Description: UserRoleService接口
* @author Pre_fantasy
* @date 2018/06/30 17:07
*/
public interface UserRoleService extends Service<UserRole> {

    List<String> getRolesByUserId(String id);
}