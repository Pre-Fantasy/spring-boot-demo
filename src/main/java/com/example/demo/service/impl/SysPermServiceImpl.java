package com.example.demo.service.impl;

import com.example.demo.dao.SysPermMapper;
import com.example.demo.model.SysPerm;
import com.example.demo.service.SysPermService;
import com.example.demo.core.universal.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @Description: SysPermService接口实现类
* @author Pre_fantasy
* @date 2018/06/30 17:03
*/
@Service
public class SysPermServiceImpl extends AbstractService<SysPerm> implements SysPermService {

    @Resource
    private SysPermMapper sysPermMapper;

}