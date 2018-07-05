package com.example.demo.dao;

import com.example.demo.core.aop.AnnotationLog;
import com.example.demo.core.universal.Mapper;
import com.example.demo.model.SystemLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SystemLogMapper extends Mapper<SystemLog> {
    Integer insertByBatch(List<SystemLog> list);
}