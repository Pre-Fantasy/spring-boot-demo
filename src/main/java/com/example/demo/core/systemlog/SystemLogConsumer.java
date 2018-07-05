package com.example.demo.core.systemlog;/**
 * @author Pre_fantasy
 * @create 2018-07-05 15:39
 * @desc
 **/

import com.example.demo.model.SystemLog;
import com.example.demo.service.SystemLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.jdbc.object.BatchSqlUpdate.DEFAULT_BATCH_SIZE;

/**

 * @author dell

 * @create 2018-07-05 15:39

 * @desc 日志消费列队

 **/
@Component
public class SystemLogConsumer implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SystemLogConsumer.class);
    public static final int DEDAULT_BATCH_SIZE = 64;
    private SystemLogQueue auditLogQueue;
    private SystemLogService systemLogService;
    private int batchSize = DEFAULT_BATCH_SIZE;
    private boolean active = true;
    private Thread thread;

    @PostConstruct
    public void init() {
        thread = new Thread(this);
        thread.start();
    }

    @PreDestroy
    public void close() {
        active = false;
    }

    @Override
    public void run() {

        while (active) {
            execute();
        }
    }

    private void execute() {
        List<SystemLog> systemLogs = new ArrayList<>();
        try {
            int size = 0;
            while (size < batchSize) {
                SystemLog systemLog = auditLogQueue.poll();
                if (systemLog == null) {
                    break;
                }
                systemLogs.add(systemLog);
                size++;
            }
        } catch (InterruptedException e) {
            logger.info(e.getMessage(), e);
            e.printStackTrace();
        }
        if (! systemLogs.isEmpty()) {
            try {
                Thread.sleep(10000);
                systemLogService.insertByBatch(systemLogs);
            } catch (InterruptedException e) {
                logger.error("异常信息：{}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Resource
    public void setAuditLogQueue(SystemLogQueue auditLogQueue) {
        this.auditLogQueue = auditLogQueue;
    }

    @Resource
    public void setAuditLogService(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
