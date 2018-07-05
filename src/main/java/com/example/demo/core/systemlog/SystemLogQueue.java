package com.example.demo.core.systemlog;

import com.example.demo.model.SystemLog;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author  Pre_fantasy
 * @create  2018-07-05 15:24
 * @desc    日志的存放列队
 **/
@Component
public class SystemLogQueue {

    private BlockingQueue<SystemLog> blockingQueue = new LinkedBlockingQueue<>();

    public void add(SystemLog systemLog) {
        blockingQueue.add(systemLog);
    }
    public SystemLog poll() throws InterruptedException {
        return blockingQueue.poll(1, TimeUnit.SECONDS);
    }
}
