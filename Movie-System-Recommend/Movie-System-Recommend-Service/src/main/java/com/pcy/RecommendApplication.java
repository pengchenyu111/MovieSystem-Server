package com.pcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 19:50
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RecommendApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecommendApplication.class);
    }
}
