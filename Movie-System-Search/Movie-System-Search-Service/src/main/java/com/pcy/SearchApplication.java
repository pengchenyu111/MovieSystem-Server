package com.pcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author PengChenyu
 * @Date 2023/3/16 0:02
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class);
    }
}
