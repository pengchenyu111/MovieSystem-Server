package com.pcy.dao;

import com.pcy.pojo.CalRatingsScoreResult;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;


/**
 * @Author PengChenyu
 * @Date 2023/4/21 22:00
 * @Version 1.0
 */
@Repository
@Mapper
public interface MovieRatingDao {

    CalRatingsScoreResult calRatingScore(int doubanId);
}
