package com.example.demo.service.impl;

import com.example.demo.dao.UserRoleMapper;
import com.example.demo.model.UserRole;
import com.example.demo.service.UserRoleService;
import com.example.demo.core.universal.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: UserRoleService接口实现类
* @author Pre_fantasy
* @date 2018/06/30 17:07
*/
@Service
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public List<String> getRolesByUserId(String id) {
        return userRoleMapper.getRolesByUserId(id);
    }
}