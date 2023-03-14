package com.pcy.controller;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.domain.movieTag.MovieTag;
import com.pcy.service.MovieTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieTag)表控制层
 *
 * @author PengChenyu
 * @since 2021-02-01 14:34:51
 */
@Slf4j
@RestController
@RequestMapping("/movieTag")
@Api(tags = "电影标签接口")
public class MovieTagController {
    /**
     * 服务对象
     */
    @Resource
    private MovieTagService movieTagService;

    /**
     * 查询所有电影标签数据
     *
     * @return 所有电影标签数据
     */
    @ApiOperation(value = "查询所有", notes = "查询所有电影标签数据")
    @GetMapping()
    public ApiResponse<List<MovieTag>> queryAllTags() {
        List<MovieTag> movieTags = this.movieTagService.queryAllTags();
        if (CollectionUtils.isEmpty(movieTags)) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieTags);
    }

    /**
     * 根据电影标签id列表查询标签数据
     *
     * @return 所有电影标签数据
     */
    @ApiOperation(value = "根据电影标签查询", notes = "根据电影标签查询标签数据")
    @PostMapping("/queryByIdList")
    public ApiResponse<List<MovieTag>> queryByIdList(@RequestBody List<Integer> idList) {
        List<MovieTag> movieTags = this.movieTagService.queryByIdList(idList);
        if (CollectionUtils.isEmpty(movieTags)) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieTags);
    }

}