package com.example.demo.core.tasks;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author  Pre_fantasy
 * @create  2018-07-10 19:42
 * @desc    定时任务
 *          @Scheduled为设置定时任务周期的注解，参数常用的为两种：
 *          第一种fixedRate, 他表示以一种固定频率去执行，单位为毫秒，
 *          第二种为cron，他可以表达某种特定频率，例如每天晚上三点执行，每个新奇三中午十二点等
 **/
@Component
@EnableScheduling
public class Tasks {

    /**
     *  @author Pre_fantasys'z
     *  @create 2018/7/10 20:01
     *  @param
     *  @return
     *  @desc   每五秒执行一次
     */
    @Scheduled(fixedRate = 5000)
    public void job1() {
        System.out.println("定时任务1 : " + new Date());
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void job2() {
        System.out.println("定时任务2 ： " + new Date());
    }
}
