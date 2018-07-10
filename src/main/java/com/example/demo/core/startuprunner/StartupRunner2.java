package com.example.demo.core.startuprunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author  Pre_fantasy
 * @create  2018-07-10 10:11
 * @desc    CommandLineRunner的启动方式，相较于ApplicationRunner启动方式而言，run()方法中的参数为String数组。
 *          如果多个实现类，而我需要按一定顺序执行的话，在实现类上加上@Order注解来指定执行顺序，这里数字越小，优先级越高。
 **/
@Component
@Order(value = -1)
public class StartupRunner2 implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupRunner2.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("服务器启动成功！<<<<使用CommandLineRunner接口");
    }
}
