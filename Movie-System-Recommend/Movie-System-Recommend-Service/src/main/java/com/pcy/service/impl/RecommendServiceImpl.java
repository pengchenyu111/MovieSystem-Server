package com.pcy.service.impl;

import com.pcy.commmon.util.ObjectUtil;
import com.pcy.constant.DBConstant;
import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.domain.movieTag.MovieTag;
import com.pcy.domain.movieUserTagPrefer.UserTagPrefer;
import com.pcy.domain.recommend.*;
import com.pcy.feign.MovieDetailFeignApi;
import com.pcy.feign.MovieTagFeignApi;
import com.pcy.feign.UserTagPreferFeignApi;
import com.pcy.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐服务
 *
 * @author PengChenyu
 * @since 2021-02-02 15:16:27
 */
@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final long MAX_REC_SIZE = 20;
    private static final long PROPER_REC_SIZE = 12;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private MovieDetailFeignApi movieDetailFeignApi;

    @Resource
    private UserTagPreferFeignApi userTagPreferFeignApi;

    @Resource
    private MovieTagFeignApi movieTagFeignApi;

    @Override
    public List<MovieDetail> historyTop20() {
        List<MovieDetail> movieDetails = mongoTemplate.findAll(MovieDetail.class, DBConstant.MONGO_COLLECTION_HISTORY_TOP_20);
        logger.info("【MongoDB查询-历史热门Top20】:" + movieDetails.toString());
        return movieDetails;
    }

    @Override
    public List<MovieDetail> recentlyTop20() {
        // 从MongoDB中查询出近期的前20条电影
        Query query = new Query(new Criteria()).limit(20);
        List<RecentlyTop> recentlyTops = mongoTemplate.find(query, RecentlyTop.class, DBConstant.MONGO_COLLECTION_RECENTLY_TOP);
        logger.info("【MongoDB查询-近期热门Top20】:" + recentlyTops.toString());
        // 提取出其douban_id
        List<Integer> doubanIdList = recentlyTops.stream().map(RecentlyTop::getDoubanId).collect(Collectors.toList());
        // 去数据库查询得出最终详细结果
        return movieDetailFeignApi.queryByIdList(doubanIdList).getData();
    }

    @Override
    public List<MovieDetail> genreTop10(String genre) {
        // 从MongoDB中找出该类别
        Query query = Query.query(Criteria.where("genre").is(genre));
        GenreTop genreTop = mongoTemplate.findOne(query, GenreTop.class, DBConstant.MONGO_COLLECTION_GENRE_TOP);
        // 判空
        if (genreTop == null) {
            return new ArrayList<MovieDetail>();
        }
        logger.info("【MongoDB查询-类别Top10】:" + genreTop.toString());
        // 取出doubanId
        List<Integer> doubanIdList = genreTop.getRecommendations().stream().map(BaseRecommendation::getId).collect(Collectors.toList());
        return movieDetailFeignApi.queryByIdList(doubanIdList).getData();
    }

    @Override
    public List<MovieDetail> genreCompositeTop10(List<String> genreList) {
        // 从MongoDB中找出该类别
        Query query = Query.query(Criteria.where("genre").in(genreList));
        List<GenreTop> genreTopList = mongoTemplate.find(query, GenreTop.class, DBConstant.MONGO_COLLECTION_GENRE_TOP);
        logger.info("【MongoDB查询-多类别Top10】:" + genreTopList.toString());
        // 合并内部list，去重，按照score排序,取前10个
        List<BaseRecommendation> baseRecommendationList = genreTopList.stream()
                .map(GenreTop::getRecommendations)
                .flatMap(Collection::stream)
                .distinct()
                .sorted(Comparator.comparing(BaseRecommendation::getScore).reversed())
                .collect(Collectors.toList());
        baseRecommendationList = baseRecommendationList.size() <= 10 ? baseRecommendationList : baseRecommendationList.subList(0, 10);
        List<Integer> doubanIdList = baseRecommendationList.stream().map(BaseRecommendation::getId).collect(Collectors.toList());
        // 根据id列表查询
        return movieDetailFeignApi.queryByIdList(doubanIdList).getData();
    }

    @Override
    public List<MovieDetail> userPreferGenreTop10(Integer userId) {
        // 获取用户的喜好列表
        UserTagPrefer userTagPrefer = userTagPreferFeignApi.queryById(userId).getData();
        String tags = userTagPrefer.getTagList();
        List<Integer> tagList = ObjectUtil.transforString2List(tags, ",").stream().map(Integer::parseInt).collect(Collectors.toList());
        // 获取中文分类名
        List<MovieTag> movieTagList = movieTagFeignApi.queryByIdList(tagList).getData();
        List<String> tagNameList = movieTagList.stream().map(MovieTag::getTagName).collect(Collectors.toList());
        logger.info("[用户喜好分类查询]-userId:" + userId + "-tagList:" + tagNameList.toString());
        // 调用多分类综合Top10算法
        return this.genreCompositeTop10(tagNameList);
    }

    @Override
    public List<MovieDetail> contentTFIDF(Integer doubanId) {
        // 从MongoDB中找出该douban_id,取前10个
        Query query = Query.query(Criteria.where("douban_id").is(doubanId));
        List<MovieRecs> movieRecsList = mongoTemplate.find(query, MovieRecs.class, DBConstant.MONGO_COLLECTION_CONTENT);
        logger.info("【MongoDB查询-内容推荐contentTop】:" + movieRecsList.toString());
        // 判空
        if (CollectionUtils.isEmpty(movieRecsList)) {
            return new ArrayList<MovieDetail>();
        }
        // 取出doubanId列表，去数据库查询
        List<Integer> doubanIdList = movieRecsList.stream()
                .map(MovieRecs::getRecommendations)
                .flatMap(Collection::stream)
                .map(BaseRecommendation::getId)
                .limit(PROPER_REC_SIZE)
                .collect(Collectors.toList());
        // 查询数据库
        List<MovieDetail> result = movieDetailFeignApi.queryByIdList(doubanIdList).getData();
        // 结果按照电影评分降序排序
        result.sort(Comparator.comparing(MovieDetail::getRatingScore).reversed());
        return result;
    }

    /**
     * 基于ALS的用户电影推荐
     *
     * @param userId 用户id
     * @return 推荐列表
     */
    @Override
    public List<MovieDetail> alsUserRecs(Integer userId) {
        // 从MongoDB中找出该用户对应的推荐列表
        Query query = Query.query(Criteria.where("user_id").is(userId));
        MovieUserRecs movieUserRecs = mongoTemplate.findOne(query, MovieUserRecs.class, DBConstant.MONGO_COLLECTION_ALS_USER_RECS);
        // 判空
        if (movieUserRecs == null) {
            return new ArrayList<MovieDetail>();
        }
        logger.info("【MongoDB查询-基于ALS的用户电影推荐】:" + movieUserRecs.toString());
        // 取出doubanId列表，去数据库查询
        List<Integer> doubanIdList = movieUserRecs.getRecommendations().stream()
                .map(BaseRecommendation::getId)
                .collect(Collectors.toList());
        return movieDetailFeignApi.queryByIdList(doubanIdList).getData();
    }

    /**
     * 基于ALS的电影相似度推荐
     *
     * @param doubanId 豆瓣id
     * @return 推荐列表
     */
    @Override
    public List<MovieDetail> alsMovieSimRecs(Integer doubanId) {
        // 从MongoDB中找出该douban_id
        Query query = Query.query(Criteria.where("douban_id").is(doubanId));
        MovieRecs movieRecs = mongoTemplate.findOne(query, MovieRecs.class, DBConstant.MONGO_COLLECTION_ALS_MOVIE_SIM);
        // 判空
        if (movieRecs == null) {
            return new ArrayList<MovieDetail>();
        }
        logger.info("【MongoDB查询-基于ALS的电影相似度推荐】:" + movieRecs.toString());
        // 取出doubanId列表，去数据库查询
        List<Integer> doubanIdList = movieRecs.getRecommendations().stream()
                .map(BaseRecommendation::getId)
                .limit(MAX_REC_SIZE)
                .collect(Collectors.toList());
        return movieDetailFeignApi.queryByIdList(doubanIdList).getData();
    }

    /**
     * 基于ItemCF的电影相似度推荐
     *
     * @param doubanId 豆瓣id
     * @return 推荐列表
     */
    @Override
    public List<MovieDetail> itemCFRecs(Integer doubanId) {
        // 从MongoDB中找出该douban_id
        Query query = Query.query(Criteria.where("douban_id").is(doubanId));
        MovieRecs movieRecs = mongoTemplate.findOne(query, MovieRecs.class, DBConstant.MONGO_COLLECTION_ITEM_CF_MOVIE_RECS);
        // 判空
        if (movieRecs == null) {
            return new ArrayList<MovieDetail>();
        }
        logger.info("【MongoDB查询-基于ItemCF的电影相似度推荐】:" + movieRecs.toString());
        // 取出doubanId列表，去数据库查询
        List<Integer> doubanIdList = movieRecs.getRecommendations().stream()
                .map(BaseRecommendation::getId)
                .limit(PROPER_REC_SIZE)
                .collect(Collectors.toList());
        List<MovieDetail> result = movieDetailFeignApi.queryByIdList(doubanIdList).getData();
        result.sort(Comparator.comparing(MovieDetail::getRatingScore).reversed());
        return result;
    }

    /**
     * 基于实时评分的电影推荐
     *
     * @param userId 用户id
     * @return 推荐列表
     */
    @Override
    public List<MovieDetail> ratingRecs(Integer userId) {
        // 从MongoDB中找出该用户对应的推荐列表
        Query query = Query.query(Criteria.where("user_id").is(userId));
        MovieUserRecs movieUserRecs = mongoTemplate.findOne(query, MovieUserRecs.class, DBConstant.MONGO_COLLECTION_STREAMING_RATING_USER_RECS);
        // 判空
        if (movieUserRecs == null) {
            return new ArrayList<MovieDetail>();
        }
        logger.info("【MongoDB查询-基于实时评分的电影推荐】:" + movieUserRecs.toString());
        // 取出doubanId列表，去数据库查询
        List<Integer> doubanIdList = movieUserRecs.getRecommendations().stream()
                .map(BaseRecommendation::getId)
                .collect(Collectors.toList());
        return movieDetailFeignApi.queryByIdList(doubanIdList).getData();
    }


}
