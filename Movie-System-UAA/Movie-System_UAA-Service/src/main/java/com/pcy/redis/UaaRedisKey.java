package com.pcy.redis;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * Created by wolfcode-lanxw
 */
@Getter
public enum UaaRedisKey {
    USERLOGIN_HASH("userLoginHash"), USERINFO_HASH("userInfoHash"), USER_ZSET("userZset");

    UaaRedisKey(String prefix) {
        this.prefix = prefix;
    }

    UaaRedisKey(String prefix, TimeUnit unit, int expireTime) {
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
