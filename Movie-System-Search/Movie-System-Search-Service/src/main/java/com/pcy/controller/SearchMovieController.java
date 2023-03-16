package com.pcy.controller;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.service.SearchMovieService;
import com.pcy.vo.ElasticSearchVo;
import com.pcy.vo.MovieDetailSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 22:13
 * @Version 1.0
 */
@Api(tags = "电影搜索接口")
@RestController
@RequestMapping("/search_movie")
public class SearchMovieController {

    @Resource
    SearchMovieService searchMovieService;


    /**
     * 输入搜索，电影名/导演/演员
     *
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @param keyword  用户搜索的关键字
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "当前页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "pageSize", value = "每页的数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "body", name = "keyword", value = "用户搜索的关键字", required = true, dataType = "String")
    })
    @GetMapping("/input_search/{pageNum}/{pageSize}/keyword/{keyword}")
    public ApiResponse<ElasticSearchVo<MovieDetail>> searchMovie(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @PathVariable("keyword") String keyword) {
        ElasticSearchVo<MovieDetail> movieDetailElasticSearchVo = searchMovieService.searchMovie(keyword, pageNum, pageSize);
        if (movieDetailElasticSearchVo.getTotal() == 0) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetailElasticSearchVo);
    }

    /**
     * 根据 douban_id 精准搜索
     * 基于ES
     *
     * @param doubanId 豆瓣id
     * @return ES内电影数据
     */
    @ApiOperation(value = "douban_id精准搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "doubanId", value = "doubanId", required = true, dataType = "int")
    })
    @GetMapping("/search/{doubanId}")
    public ApiResponse<MovieDetail> searchMovieByDoubanId(@PathVariable("doubanId") int doubanId) {
        MovieDetail movieDetail = searchMovieService.searchMovieByDoubanId(doubanId);
        if (movieDetail == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetail);
    }


    /**
     * 类豆瓣标签搜索
     * 基于ES
     *
     * @param movieDetailSearchRequest 请求条件实体
     * @return ES内电影数据
     */
    @ApiOperation(value = "类豆瓣标签搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "movieDetailSearchRequest", value = "doubanId", required = true, dataType = "MovieDetailSearchRequest")
    })
    @PostMapping("/searchByTags")
    public ApiResponse<ElasticSearchVo<MovieDetail>> searchByTags(@RequestBody MovieDetailSearchRequest movieDetailSearchRequest) {
        ElasticSearchVo<MovieDetail> result = searchMovieService.searchByTags(movieDetailSearchRequest);
        if (CollectionUtils.isEmpty(result.getResultList())) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, result);
    }

}
