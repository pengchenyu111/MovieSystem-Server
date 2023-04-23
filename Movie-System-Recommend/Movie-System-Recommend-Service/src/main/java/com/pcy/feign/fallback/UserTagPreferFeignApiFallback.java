package com.pcy.feign.fallback;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.movieUserTagPrefer.UserTagPrefer;
import com.pcy.feign.UserTagPreferFeignApi;
import org.springframework.stereotype.Component;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 17:35
 * @Version 1.0
 */
@Component
public class UserTagPreferFeignApiFallback implements UserTagPreferFeignApi {
    @Override
    public ApiResponse<UserTagPrefer> queryById(Integer userId) {
        return null;
    }
}
