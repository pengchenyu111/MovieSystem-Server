package com.pcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author PengChenyu
 * @Date 2023/3/10 18:21
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MovieInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieInfoApplication.class);
    }
}
