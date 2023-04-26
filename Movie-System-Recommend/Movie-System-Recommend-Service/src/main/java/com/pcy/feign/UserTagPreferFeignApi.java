package com.pcy.feign;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.movieUserTagPrefer.UserTagPrefer;
import com.pcy.feign.fallback.UserTagPreferFeignApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 17:35
 * @Version 1.0
 */
@FeignClient(value = "uaa-service", contextId = "UserTagPreferFeignApiBean", fallback = UserTagPreferFeignApiFallback.class)
public interface UserTagPreferFeignApi {

    @GetMapping("/tagPrefer/{userId}")
    ApiResponse<UserTagPrefer> queryById(@PathVariable("userId") Integer userId);
}
