package com.hims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HIMS 住院信息管理系统 启动类
 */
@SpringBootApplication
@MapperScan("com.hims.mapper")
public class HimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HimsApplication.class, args);
        System.out.println("======= HIMS 后端服务启动成功 =======");
        System.out.println("接口前缀: /hims/");
    }
}
