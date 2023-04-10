package com.pcy.service.impl;

import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.service.BaseElasticSearchService;
import com.pcy.service.SearchMovieService;
import com.pcy.vo.ElasticSearchVo;
import com.pcy.vo.MovieDetailSearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author PengChenyu
 * @Date 2023/3/14 22:10
 * @Version 1.0
 */
@Service
public class SearchMovieServiceImpl implements SearchMovieService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 电影搜索的字段：电影名，演员，导演
     */
    private final String[] SEARCH_MOVIE_FIELDS = {"title", "casts", "directors"};
    /**
     * 搜索的索引
     */
    private final String SEARCH_INDEX = "movie_detail";
    /**
     * 搜索超时
     */
    private final Long SEARCH_TIMEOUT = 1L;

    @Resource
    BaseElasticSearchService baseElasticSearchService;


    /**
     * douban_id精准查询
     *
     * @param doubanId 豆瓣id
     * @return 电影数据
     */
    @Override
    public MovieDetail searchMovieByDoubanId(int doubanId) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQuery = QueryBuilders.termQuery("doubanId", String.valueOf(doubanId));
        searchSourceBuilder.query(termQuery);
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(SEARCH_TIMEOUT));
        MovieDetail result = new MovieDetail();
        try {
            ElasticSearchVo<MovieDetail> response = baseElasticSearchService.search(this.SEARCH_INDEX, searchSourceBuilder, MovieDetail.class);
            logger.info("查询到的结果集大小:" + response.getResultList().size());
            result = response.getResultList().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 电影搜索
     *
     * @param keyword  搜索关键词
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @return ElasticSearchVo<MovieDetail>
     */
    @Override
    public ElasticSearchVo<MovieDetail> searchMovie(String keyword, int pageNum, int pageSize) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 多字段查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, this.SEARCH_MOVIE_FIELDS).minimumShouldMatch("70%").field("title", 5);
        searchSourceBuilder.query(multiMatchQueryBuilder);
        // 设置分页
        searchSourceBuilder.from(pageNum);
        searchSourceBuilder.size(pageSize);
        // 设置超时
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(SEARCH_TIMEOUT));
        // 开始搜索
        ElasticSearchVo<MovieDetail> result = new ElasticSearchVo<>();
        try {
            result = baseElasticSearchService.search(this.SEARCH_INDEX, searchSourceBuilder, MovieDetail.class);
        } catch (IOException e) {
            logger.error("搜索出错:" + e.getMessage());
        }
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        return result;
    }


    /**
     * 类豆瓣标签搜索
     * 这里返回的total不是实际的总数，具体应该以response.getResultList().size()为准
     *
     * @param movieDetailSearchRequest 请求条件实体
     * @return ES内电影数据
     */
    @Override
    public ElasticSearchVo<MovieDetail> searchByTags(MovieDetailSearchRequest movieDetailSearchRequest) {
        // 设置查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("types", movieDetailSearchRequest.getTypes()))
                .must(QueryBuilders.matchQuery("productionCountryArea", movieDetailSearchRequest.getProductionCountryArea()))
                .must(QueryBuilders.matchQuery("language", movieDetailSearchRequest.getLanguage()))
                .must(QueryBuilders.rangeQuery("ratingScore")
                        .gte(movieDetailSearchRequest.getRatingScoreLowerBound())
                        .lte(movieDetailSearchRequest.getRatingScoreUpperBound()));
        searchSourceBuilder.query(boolQueryBuilder);
        // 设置分页
        searchSourceBuilder.from(movieDetailSearchRequest.getPageNum() * movieDetailSearchRequest.getPageSize());
        searchSourceBuilder.size(movieDetailSearchRequest.getPageSize());
        // 发起请求并开始处理结果
        ElasticSearchVo<MovieDetail> result = new ElasticSearchVo<>();
        try {
            result = baseElasticSearchService.search(this.SEARCH_INDEX, searchSourceBuilder, MovieDetail.class);
        } catch (IOException e) {
            logger.error("搜索出错:" + e.getMessage());
        }
        result.setPageNum(movieDetailSearchRequest.getPageNum());
        result.setPageSize(movieDetailSearchRequest.getPageSize());
        logger.info("[ES标签搜索]===>" + result);
        return result;
    }
}
