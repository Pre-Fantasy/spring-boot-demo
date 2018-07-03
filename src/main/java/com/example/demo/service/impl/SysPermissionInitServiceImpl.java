package com.example.demo.service.impl;

import com.example.demo.dao.SysPermissionInitMapper;
import com.example.demo.model.SysPermissionInit;
import com.example.demo.service.SysPermissionInitService;
import com.example.demo.core.universal.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: SysPermissionInitService接口实现类
* @author Pre_fantasy
* @date 2018/07/02 20:52
*/
@Service
public class SysPermissionInitServiceImpl extends AbstractService<SysPermissionInit> implements SysPermissionInitService {

    @Resource
    private SysPermissionInitMapper sysPermissionInitMapper;

    @Override
    public List<SysPermissionInit> selectAllOrderBySort() {
        return  sysPermissionInitMapper.selectAllOrderBySort();
    }

}