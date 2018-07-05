package com.example.demo.service;

import com.example.demo.model.SystemLog;
import com.example.demo.core.universal.Service;

import java.util.List;

/**
* @Description: SystemLogService接口
* @author Pre_fantasy
* @date 2018/06/30 16:28
*/
public interface SystemLogService extends Service<SystemLog> {

    /*异步执行记录日志操作*/
    Integer insertByBatch(List<SystemLog> list);

}