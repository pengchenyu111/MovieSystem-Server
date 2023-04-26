package com.pcy.util;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author PengChenyu
 * @Date 2023/4/16 23:44
 * @Version 1.0
 */
@Component
public class BloomFilterUtil {

    @Resource
    RedissonClient redissonClient;

    public  void initializeBloomFilter(String bloomFilterName, long expectedInsertions, double falseProbability) {
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(bloomFilterName);
        bloomFilter.tryInit(expectedInsertions, falseProbability);
    }

    public void addElement(String bloomFilterName, Integer element) {
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(bloomFilterName);
        bloomFilter.add(element);
    }

    public boolean containsElement(String bloomFilterName, Integer element) {
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(bloomFilterName);
        return bloomFilter.contains(element);
    }
}
