package com.pcy.controller;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.domain.imdbRatings.FormatImdbRatings;
import com.pcy.domain.imdbRatings.ImdbRatings;
import com.pcy.service.ImdbRatingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (ImdbRatings)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-27 22:28:28
 */
@Api( tags = "IMDB网站电影评分接口")
@RestController
@RequestMapping("/imdbRatings")
public class ImdbRatingsController {
    /**
     * 服务对象
     */
    @Resource
    private ImdbRatingsService imdbRatingsService;

    /**
     * 通过主键查询单条数据
     *
     * @param imdbId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "imdbId", value = "imdbid", required = true, dataType = "String")
    })
    @GetMapping("/{imdbId}")
    public ApiResponse<ImdbRatings> queryByImdbId(@PathVariable("imdbId") String imdbId) {
        ImdbRatings imdbRatings = this.imdbRatingsService.queryById(imdbId);
        if (imdbRatings == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, imdbRatings);
    }

    /**
     * 通过豆瓣id查询单条数据
     *
     * @param doubanId 豆瓣id
     * @return 单条数据
     */
    @ApiOperation(value = "豆瓣id查询", notes = "通过豆瓣id查询查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "doubanId", value = "doubanId", required = true, dataType = "String")
    })
    @GetMapping("/doubanId/{doubanId}")
    public ApiResponse<FormatImdbRatings> queryByDouban(@PathVariable("doubanId") String doubanId) {
        FormatImdbRatings imdbRatings = this.imdbRatingsService.queryByDoubanIdFormat(doubanId);
        if (imdbRatings == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, imdbRatings);
    }

    /**
     * 通过豆瓣id查询评分人数总数
     *
     * @param doubanId 豆瓣id
     * @return 评分人数总数
     */
    @ApiOperation(value = "通过豆瓣id查询评分人数总数", notes = "通过豆瓣id查询评分人数总数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "doubanId", value = "doubanId", required = true, dataType = "Integer")
    })
    @GetMapping("/vote/doubanId/{doubanId}")
    public ApiResponse<Integer> queryVotes(@PathVariable("doubanId") Integer doubanId) {
        Integer votes = this.imdbRatingsService.queryVotes(doubanId);
        if (votes == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, votes);
    }


}