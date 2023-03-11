package com.pcy.commmon.redis;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * Created by wolfcode-lanxw
 */
@Getter
public enum CommonRedisKey {

    USER_TOKEN("userToken:", TimeUnit.MINUTES, 30);

    CommonRedisKey(String prefix, TimeUnit unit, int expireTime) {
        this.prefix = prefix;
        this.unit = unit;
        this.expireTime = expireTime;
    }

    public String getRealKey(String key) {
        return this.prefix + key;
    }

    private String prefix;
    private TimeUnit unit;
    private int expireTime;


}
