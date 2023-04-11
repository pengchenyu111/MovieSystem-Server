package com.pcy.feign.fallback;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.movieTag.MovieTag;
import com.pcy.feign.MovieTagFeignApi;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 14:41
 * @Version 1.0
 */
@Component
public class MovieTagFeignApiFallback implements MovieTagFeignApi {
    @Override
    public ApiResponse<List<MovieTag>> queryByIdList(List<Integer> idList) {
        return null;
    }
}
