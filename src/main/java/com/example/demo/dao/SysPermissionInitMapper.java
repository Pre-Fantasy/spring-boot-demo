package com.example.demo.dao;

import com.example.demo.core.universal.Mapper;
import com.example.demo.model.SysPermissionInit;

import java.util.List;

/**
 *  @author Pre_fantasy
 *  @create 2018/7/2 20:54
 *  @param
 *  @return 
 *  @desc   根据工具生成mapper，dao，service，controller并添加我们需要的查询方法
 */
public interface SysPermissionInitMapper extends Mapper<SysPermissionInit> {
    
    List<SysPermissionInit> selectAllOrderBySort();
}