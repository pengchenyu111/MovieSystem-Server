package com.pcy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.pcy.redis.JobRedisKey;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by lanxw
 * 用于处理用户缓存的定时任务
 * 为了保证Redis中的内存的有效使用。
 * 我们默认保留7天内的用户缓存数据，每天凌晨的时候会把7天前的用户登录缓存数据删除掉
 */
@Component
@Setter
@Getter
@RefreshScope
@Slf4j
public class UserCacheJob implements SimpleJob {

    @Value("${jobCron.userCache}")
    private String cron;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void execute(ShardingContext shardingContext) {
//        System.out.println("hahahhahah" + new Date());
        doWork();
    }

    private void doWork() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        //获取7天前的日期
        long max = calendar.getTime().getTime();
        String userZSetKey = JobRedisKey.USER_ZSET.getRealKey("");
        String userLoginHashKey = JobRedisKey.USERLOGIN_HASH.getRealKey("");
        String userInfoHashKey = JobRedisKey.USERINFO_HASH.getRealKey("");
        //查询7天前的用户ID集合
        Set<String> ids = redisTemplate.opsForZSet().rangeByScore(userZSetKey, 0, max);
        //删除7天前的用户缓存数据
        if (Objects.requireNonNull(ids).size() > 0) {
            redisTemplate.opsForHash().delete(userLoginHashKey, ids.toArray());
            redisTemplate.opsForHash().delete(userInfoHashKey, ids.toArray());
        }
        redisTemplate.opsForZSet().removeRangeByScore(userZSetKey, 0, calendar.getTime().getTime());
    }
}
