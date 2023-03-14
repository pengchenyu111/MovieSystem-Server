package com.pcy.controller;

import com.github.pagehelper.PageInfo;
import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.service.MovieDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (MovieDetail)表控制层
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:53
 */
@Api(tags = "电影详情接口")
@RestController
@RequestMapping("/movieDetail")
public class MovieDetailController {
    /**
     * 服务对象
     */
    @Resource
    private MovieDetailService movieDetailService;

    /**
     * 通过主键查询单条数据
     *
     * @param doubanId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "主键查询", notes = "通过主键查询单条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "doubanId", value = "豆瓣id", required = true, dataType = "Integer")
    })
    @GetMapping("/{doubanId}")
    public ApiResponse<MovieDetail> selectOne(@PathVariable("doubanId") Integer doubanId) {
        MovieDetail movieDetail = this.movieDetailService.queryById(doubanId);
        if (movieDetail == null) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetail);
    }

    /**
     * 通过doubanId集合查询多条电影数据
     *
     * @param doubanIdList 主键
     * @return 多条电影数据
     */
    @ApiOperation(value = "通过doubanId集合查询多条电影数据", notes = "通过doubanId集合查询多条电影数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "doubanId", value = "豆瓣id", required = true, dataType = "Integer")
    })
    @PostMapping("/queryByIdList")
    public ApiResponse<List<MovieDetail>> queryByIdList(@RequestBody List<Integer> doubanIdList) {
        List<MovieDetail> movieDetails = this.movieDetailService.queryByIdList(doubanIdList);
        if (CollectionUtils.isEmpty(movieDetails)) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetails);
    }


    /**
     * 分页获取电影详情
     *
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    @ApiOperation(value = "分页获取电影详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, dataType = "int")
    })
    @GetMapping("/page/{pageNum}/{pageSize}")
    public ApiResponse<PageInfo<MovieDetail>> queryPageMovie(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        PageInfo<MovieDetail> movieDetailPageInfo = movieDetailService.queryPageMovie(pageNum, pageSize);
        if (movieDetailPageInfo.getTotal() == 0L) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetailPageInfo);
    }


    /**
     * 分页查询
     *
     * @param pageNum     当前页
     * @param pageSize    每页多少数据
     * @param movieDetail 查询条件
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "movieDetail", value = "查询条件", required = true, dataType = "MovieDetail")
    })
    @PostMapping("/page/{pageNum}/{pageSize}")
    public ApiResponse<PageInfo<MovieDetail>> queryPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody MovieDetail movieDetail) {
        PageInfo<MovieDetail> movieDetailPageInfo = movieDetailService.queryPage(pageNum, pageSize, movieDetail);
        if (movieDetailPageInfo.getTotal() == 0L) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetailPageInfo);
    }


//    /**
//     * 搜索，电影名/导演/演员
//     *
//     * @param pageNum  当前页
//     * @param pageSize 每页多少数据
//     * @param map      keyword 用户搜索的关键字
//     * @return 分页数据
//     */
//    @ApiOperation(value = "分页查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "int"),
//            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, dataType = "int"),
//            @ApiImplicitParam(paramType = "query", name = "map", value = "用户搜索的关键字", required = true, dataType = "Map")
//    })
//    @PostMapping("/search/{pageNum}/{pageSize}")
//    public ApiResponse<ElasticSearchVo<MovieDetail>> searchMovie(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody Map<String, String> map) {
//        String keyword = map.get("keyword");
//        ElasticSearchVo<MovieDetail> movieDetailElasticSearchVo = movieDetailService.searchMovie(keyword, pageNum, pageSize);
//        if (movieDetailElasticSearchVo.getTotal() == 0) {
//            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
//        }
//        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetailElasticSearchVo);
//    }

//    /**
//     * 根据 douban_id 精准搜索
//     * 基于ES
//     *
//     * @param doubanId 豆瓣id
//     * @return ES内电影数据
//     */
//    @ApiOperation(value = "douban_id精准搜索")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "doubanId", value = "doubanId", required = true, dataType = "int")
//    })
//    @GetMapping("/search/{doubanId}")
//    public ApiResponse<MovieDetail> searchMovieByDoubanId(@PathVariable("doubanId") int doubanId) {
//        MovieDetail movieDetail = movieDetailService.searchMovieByDoubanId(doubanId);
//        if (movieDetail == null) {
//            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
//        }
//        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, movieDetail);
//    }

//
//    /**
//     * 类豆瓣标签搜索
//     * 基于ES
//     *
//     * @param movieDetailSearchRequest 请求条件实体
//     * @return ES内电影数据
//     */
//    @ApiOperation(value = "类豆瓣标签搜索")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "body", name = "movieDetailSearchRequest", value = "doubanId", required = true, dataType = "MovieDetailSearchRequest")
//    })
//    @PostMapping("/searchByTags")
//    public ApiResponse<ElasticSearchVo<MovieDetail>> searchByTags(@RequestBody MovieDetailSearchRequest movieDetailSearchRequest) {
//        ElasticSearchVo<MovieDetail> result = movieDetailService.searchByTags(movieDetailSearchRequest);
//        if (CollectionUtils.isEmpty(result.getResultList())) {
//            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.QUERY_NULL, null);
//        }
//        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, result);
//    }

}