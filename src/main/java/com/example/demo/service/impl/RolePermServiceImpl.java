package com.example.demo.service.impl;

import com.example.demo.dao.RolePermMapper;
import com.example.demo.model.RolePerm;
import com.example.demo.service.RolePermService;
import com.example.demo.core.universal.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: RolePermService接口实现类
* @author Pre_fantasy
* @date 2018/06/30 17:03
*/
@Service
public class RolePermServiceImpl extends AbstractService<RolePerm> implements RolePermService {

    @Resource
    private RolePermMapper rolePermMapper;

    @Override
    public List<String> getPermsByUserId(String id) {
        return rolePermMapper.getPermsByUserId(id);
    }
}