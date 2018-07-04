package com.example.demo.service.impl;

import com.example.demo.dao.SystemLogMapper;
import com.example.demo.model.SystemLog;
import com.example.demo.service.SystemLogService;
import com.example.demo.core.universal.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @Description: SystemLogService接口实现类
* @author Pre_fantasy
* @date 2018/06/30 16:28
*/
@Service
public class SystemLogServiceImpl extends AbstractService<SystemLog> implements SystemLogService {

    @Resource
    private SystemLogMapper systemLogMapper;

}