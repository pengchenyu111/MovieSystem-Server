package com.pcy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.pcy.dao.MovieDetailDao;
import com.pcy.dao.MovieRatingDao;
import com.pcy.pojo.CalRatingsScoreResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author PengChenyu
 * @Date 2023/4/19 22:28
 * @Version 1.0
 */
@Component
@RefreshScope // 配置改变时动态刷新
@Getter
@Setter
@Slf4j
public class MovieRatingUpdateJob implements SimpleJob {

    public static final int SHARDING_COUNT = 4;

    @Value("${jobCron.movieRatingUpdateCorn}")
    private String cron;

    @Value("${shardingCount.movieRatingUpdateJob}")
    private int shardingCount;

    @Resource
    ThreadPoolExecutor jobExecutor;

    @Resource
    MovieDetailDao movieDetailDao;

    @Resource
    MovieRatingDao movieRatingDao;

    @Override
    public void execute(ShardingContext shardingContext) {
        // 首先查询当前分片下需要修改的douban_id
        // 处理方法：通过douban_id mod shardingTotalCount 找到每个分片需要处理的id
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        int shardingItem = shardingContext.getShardingItem();
        List<Integer> shardingIdToHandle = movieDetailDao.selectIdBySharding(shardingTotalCount, shardingItem);
        ConcurrentLinkedDeque<Integer>  doubanIdDeque = new ConcurrentLinkedDeque<>(shardingIdToHandle);

        // 每个分片开启4个线程去处理
        for (int i = 0; i < 4; i++) {
            jobExecutor.execute(() -> {
                while (!doubanIdDeque.isEmpty()) {
                    Integer doubanId = doubanIdDeque.pollFirst();
                    if (doubanId != null) {
                        CalRatingsScoreResult result = movieRatingDao.calRatingScore(doubanId);
                        movieDetailDao.updateRatingByCalResult(result);
                        log.info(Thread.currentThread().getName() + "=====>" + "电影id：{} 评分已更新成功", doubanId);
                    }
                }
            });
        }
    }
}
