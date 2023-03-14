package com.pcy.feign;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.movieTag.MovieTag;
import com.pcy.feign.fallback.MovieTagFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 14:41
 * @Version 1.0
 */
@FeignClient(value = "movie-info-service", fallback = MovieTagFeignFallback.class)
public interface MovieTagFeignApi {

    @PostMapping("/movieTag/queryByIdList")
    ApiResponse<List<MovieTag>> queryByIdList(@RequestBody List<Integer> idList);
}
