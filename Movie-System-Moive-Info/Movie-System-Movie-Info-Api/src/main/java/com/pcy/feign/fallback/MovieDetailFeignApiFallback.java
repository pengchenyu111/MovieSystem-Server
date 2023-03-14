package com.pcy.feign.fallback;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.feign.MovieDetailFeignApi;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 17:04
 * @Version 1.0
 */
@Component
public class MovieDetailFeignApiFallback implements MovieDetailFeignApi {
    @Override
    public ApiResponse<List<MovieDetail>> queryByIdList(List<Integer> doubanIdList) {
        return null;
    }
}
