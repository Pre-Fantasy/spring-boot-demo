package com.example.demo.service.impl;

import com.example.demo.dao.SysRoleMapper;
import com.example.demo.model.SysRole;
import com.example.demo.service.SysRoleService;
import com.example.demo.core.universal.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @Description: SysRoleService接口实现类
* @author Pre_fantasy
* @date 2018/06/30 17:03
*/
@Service
public class SysRoleServiceImpl extends AbstractService<SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

}