package com.pcy.feign;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.feign.fallback.MovieDetailFeignApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 17:03
 * @Version 1.0
 */
@FeignClient(value = "movie-info-service", contextId = "MovieDetailFeignApiBean", fallback = MovieDetailFeignApiFallback.class)
public interface MovieDetailFeignApi {

    @PostMapping("/movieDetail/queryByIdList")
    ApiResponse<List<MovieDetail>> queryByIdList(@RequestBody List<Integer> doubanIdList);


}
