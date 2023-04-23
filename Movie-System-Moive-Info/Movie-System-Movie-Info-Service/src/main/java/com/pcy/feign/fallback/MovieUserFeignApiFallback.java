package com.pcy.feign.fallback;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.domain.user.MovieUser;
import com.pcy.feign.MovieUserFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author PengChenyu
 * @Date 2023/3/13 22:32
 * @Version 1.0
 */
@Component
@Slf4j
public class MovieUserFeignApiFallback implements MovieUserFeignApi {
    @Override
    public ApiResponse<MovieUser> queryByUserUniqueName(String userUniqueName) {
        log.info("出错！");
        return null;
    }
}
