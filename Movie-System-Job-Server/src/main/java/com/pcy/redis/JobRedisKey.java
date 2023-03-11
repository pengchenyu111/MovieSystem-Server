package com.pcy.redis;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * Created by wolfcode-lanxw
 */
@Getter
public enum JobRedisKey {
    SECKILL_PRODUCT_HASH("seckillProductHash:"),
    SECKILL_STOCK_COUNT_HASH("seckillStockCount:"),
    USERLOGIN_HASH("userLoginHash"), USERINFO_HASH("userInfoHash"), USER_ZSET("userZset");

    JobRedisKey(String prefix, TimeUnit unit, int expireTime) {
        this.prefix = prefix;
        this.unit = unit;
        this.expireTime = expireTime;
    }

    JobRedisKey(String prefix) {
        this.prefix = prefix;
    }

    public String getRealKey(String key) {
        return this.prefix + key;
    }

    private String prefix;
    private TimeUnit unit;
    private int expireTime;
}
