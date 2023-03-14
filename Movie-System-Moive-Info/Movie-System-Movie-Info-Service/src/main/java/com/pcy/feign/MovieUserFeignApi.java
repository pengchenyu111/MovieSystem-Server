package com.pcy.feign;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.user.MovieUser;
import com.pcy.feign.fallback.MovieUserFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author PengChenyu
 * @Date 2023/3/13 22:33
 * @Version 1.0
 */
@FeignClient(value = "uaa-service", fallback = MovieUserFeignFallback.class)
public interface MovieUserFeignApi {


    @GetMapping("/uaa/movieUser/uniqueName/{userUniqueName}")
    ApiResponse<MovieUser> queryByUserUniqueName(@PathVariable("userUniqueName") String userUniqueName);
}
