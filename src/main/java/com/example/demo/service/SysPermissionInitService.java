package com.example.demo.service;

import com.example.demo.model.SysPermissionInit;
import com.example.demo.core.universal.Service;

import java.util.List;

/**
* @Description: SysPermissionInitService接口
* @author Pre_fantasy
* @date 2018/07/02 20:52
*/
public interface SysPermissionInitService extends Service<SysPermissionInit> {

    List<SysPermissionInit> selectAllOrderBySort();
}