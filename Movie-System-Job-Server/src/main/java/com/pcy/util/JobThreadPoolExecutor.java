package com.pcy.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author PengChenyu
 * @Date 2023/4/21 11:00
 * @Version 1.0
 */

@Configuration
@EnableAsync
public class JobThreadPoolExecutor {
    @Value("${threadPool.coreSize}")
    private Integer coreSize;
    @Value("${threadPool.maxSize}")
    private Integer maxSize;
    @Value("${threadPool.keepLive}")
    private long keepLive;
    @Value("${threadPool.queueSize}")
    private Integer queueSize;

    @Bean(name = "jobExecutor")
    public Executor jobThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                coreSize,
                maxSize,
                keepLive,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueSize),
                new ThreadPoolExecutor.AbortPolicy()
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
}
