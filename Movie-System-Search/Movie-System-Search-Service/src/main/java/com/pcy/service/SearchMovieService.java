package com.pcy.service;

import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.vo.ElasticSearchVo;
import com.pcy.vo.MovieDetailSearchRequest;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 22:03
 * @Version 1.0
 */
public interface SearchMovieService {

    /**
     * douban_id精准查询
     *
     * @param doubanId 豆瓣id
     * @return 电影数据
     */
    MovieDetail searchMovieByDoubanId(int doubanId);


    /**
     * 电影搜索
     *
     * @param keyword  搜索关键词
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @return ElasticSearchVo<MovieDetail>
     */
    ElasticSearchVo<MovieDetail> searchMovie(String keyword, int pageNum, int pageSize);

    /**
     * 类豆瓣标签搜索
     *
     * @param movieDetailSearchRequest 请求条件实体
     * @return ES内电影数据
     */
    ElasticSearchVo<MovieDetail> searchByTags(MovieDetailSearchRequest movieDetailSearchRequest);
}
