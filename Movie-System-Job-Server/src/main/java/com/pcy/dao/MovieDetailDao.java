package com.pcy.dao;

import com.pcy.pojo.CalRatingsScoreResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author PengChenyu
 * @Date 2023/4/21 19:59
 * @Version 1.0
 */
@Repository
@Mapper
public interface MovieDetailDao {

    //查询当前片下对数据
    List<Integer> selectIdBySharding(@Param("shardingTotalCount") Integer count, @Param("shardingItem") Integer item);

    int updateRatingByCalResult(CalRatingsScoreResult result);
}
