package com.morjo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.morjo.model.dao")
public class DBConfig {
    public static void main(String[] args) {

    }
}
