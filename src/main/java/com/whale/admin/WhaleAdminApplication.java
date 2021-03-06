package com.whale.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author trendong
 */
@MapperScan(value = {"com.whale.framework.repository.mapper"})
@SpringBootApplication(scanBasePackages = {"com.whale.admin.web", "com.whale.admin.config"})
public class WhaleAdminApplication {

    private static final Logger logger = LoggerFactory.getLogger(WhaleAdminApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WhaleAdminApplication.class, args);
        logger.info("just do it");
    }

}
