package com.pcy.controller;


import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.domain.movieReviews.MovieUserRatings;
import com.pcy.service.MovieUserRatingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MovieUserRatings)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:25
 */
@Api(tags = "用户对电影评分接口")
@RestController
@RequestMapping("/movieUserRatings")
public class MovieUserRatingsController {
    /**
     * 服务对象
     */
    @Resource
    private MovieUserRatingsService movieUserRatingsService;

    /**
     * 通过主键查询单条数据
     *
     * @param reviewId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "reviewId", value = "用户评论id", required = true, dataType = "String")
    })
    @GetMapping("/{reviewId}")
    public ApiResponse<MovieUserRatings> selectOne(@PathVariable("reviewId") String reviewId) {
        MovieUserRatings movieUserRatings = this.movieUserRatingsService.queryById(reviewId);
        if (movieUserRatings == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieUserRatings);
    }

    /**
     * 获取用户最近的K次评分数据(简要信息)
     *
     * @param userId 用户id
     * @param k      数据量
     * @return 数据列表
     */
    @ApiOperation(value = "获取用户最近的K次评分数据(简要信息)", notes = "获取用户最近的K次评分数据(简要信息)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userId", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "k", value = "评论数据量", required = true, dataType = "Integer")
    })
    @GetMapping("recent/{userId}/{k}")
    public ApiResponse<List<MovieUserRatings>> kRecentRatingsShort(@PathVariable("userId") Integer userId, @PathVariable("k") Integer k) {
        List<MovieUserRatings> movieUserRatingsList = this.movieUserRatingsService.kRecentRatingsShort(userId, k);
        if (CollectionUtils.isEmpty(movieUserRatingsList)) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieUserRatingsList);
    }

    /**
     * 将用户最近的评分数据存入Redis
     *
     * @param userId 用户id
     * @return 是否成功信息
     */
    @ApiOperation(value = "将用户最近的评分数据存入Redis", notes = "将用户最近的评分数据存入Redis")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userId", value = "用户id", required = true, dataType = "Integer")
    })
    @GetMapping("loadIntoRedis/{userId}")
    public ApiResponse<Long> loadIntoRedis(@PathVariable("userId") Integer userId) {
        Long ratingCount = this.movieUserRatingsService.loadIntoRedis(userId);
        if (ratingCount == -1L) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.REQUEST_FAIL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, ratingCount);
    }

}