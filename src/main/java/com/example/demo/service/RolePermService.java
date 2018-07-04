package com.example.demo.service;

import com.example.demo.model.RolePerm;
import com.example.demo.core.universal.Service;

import java.util.List;

/**
* @Description: RolePermService接口
* @author       Pre_fantasy
* @date         2018/06/30 17:03
*/
public interface RolePermService extends Service<RolePerm> {

    List<String> getPermsByUserId(String id);
}