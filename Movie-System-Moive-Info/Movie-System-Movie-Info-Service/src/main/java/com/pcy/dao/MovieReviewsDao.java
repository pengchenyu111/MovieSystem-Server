package com.pcy.dao;

import com.pcy.domain.movieReviews.MovieReviews;
import com.pcy.domain.movieReviews.UserReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (MovieReviews)表数据库访问层
 *
 * @author PengChenyu
 * @since 2020-12-29 20:08:18
 */
@Repository
@Mapper
public interface MovieReviewsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param reviewId 主键
     * @return 实例对象
     */
    MovieReviews queryById(String reviewId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MovieReviews> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param movieReviews 实例对象
     * @return 对象列表
     */
    List<MovieReviews> queryAll(MovieReviews movieReviews);


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    int count();


    /**
     * 新增数据
     *
     * @param movieReviews 实例对象
     * @return 影响行数
     */
    int insert(MovieReviews movieReviews);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieReviews> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MovieReviews> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MovieReviews> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MovieReviews> entities);

    /**
     * 修改数据
     *
     * @param movieReviews 实例对象
     * @return 影响行数
     */
    int update(MovieReviews movieReviews);

    /**
     * 通过主键删除数据
     *
     * @param reviewId 主键
     * @return 影响行数
     */
    int deleteById(String reviewId);

    /**
     * 根据doubanId查询
     *
     * @param doubanId 豆瓣id
     * @return 分页数据
     */
    List<MovieReviews> queryByDoubanId(Integer doubanId);

    /**
     * 根据doubanId查询， 默认按赞同数（降序），时间（降序）排序
     *
     * @param doubanId 豆瓣id
     * @return 分页数据
     */
    List<MovieReviews> queryByDoubanIdWithSort(Integer doubanId);

    /**
     * 用户给评论点赞
     *
     * @param reviewId 评论id
     * @return 评论点赞是否成功
     */
    Boolean agree(String reviewId);

    /**
     * 获取用户最近的K次评分数据
     *
     * @param userId 用户id
     * @param k      数据量
     * @return 数据列表
     */
    List<MovieReviews> kRecentRatings(@Param("userId") Integer userId, @Param("k") Integer k);

    /**
     * 根据userId分页查询用户历史评论
     *
     * @param userId 用户id
     * @return 用户历史评论
     */
    List<UserReview> userHistoryReviews(Integer userId);
}