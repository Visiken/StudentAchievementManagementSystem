package com.ken.stuscoremanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = "com.ken.stuscoremanager.dao")
public class StuScoreManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StuScoreManagerApplication.class, args);
    }

}
