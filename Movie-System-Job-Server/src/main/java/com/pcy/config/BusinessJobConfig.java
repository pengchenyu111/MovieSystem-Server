package com.pcy.config;


import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.pcy.job.MovieRatingUpdateJob;
import com.pcy.job.UserCacheJob;
import com.pcy.util.ElasticJobUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lanxw
 */
@Configuration
public class BusinessJobConfig {
    /**
     * 开启定时清理用户登录信息缓存的任务
     *
     * @param registryCenter
     * @param userCacheJob
     * @return
     */
    @Bean(initMethod = "init")
    public SpringJobScheduler initUserCacheJob(CoordinatorRegistryCenter registryCenter, UserCacheJob userCacheJob) {
        LiteJobConfiguration jobConfiguration = ElasticJobUtil.createDefaultSimpleJobConfiguration(userCacheJob.getClass(), userCacheJob.getCron());
        return new SpringJobScheduler(userCacheJob, registryCenter, jobConfiguration);
    }

    /**
     * 开启定时更新电影评分的任务
     *
     * @param registryCenter
     * @param movieRatingUpdateJob
     * @return
     */
    @Bean(initMethod = "init")
    public SpringJobScheduler initMovieRatingUpdateJob(CoordinatorRegistryCenter registryCenter, MovieRatingUpdateJob movieRatingUpdateJob) {
        LiteJobConfiguration jobConfiguration = ElasticJobUtil.createJobConfiguration(
                movieRatingUpdateJob.getClass(),
                movieRatingUpdateJob.getCron(),
                movieRatingUpdateJob.getShardingCount(),
                null,
                false);
        return new SpringJobScheduler(movieRatingUpdateJob, registryCenter, jobConfiguration);
    }
}

