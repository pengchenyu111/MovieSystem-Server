package com.pcy.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcy.constant.MovieConstant;
import com.pcy.dao.MovieDetailDao;
import com.pcy.domain.movieDetail.MovieDetail;
import com.pcy.service.MovieDetailService;
import com.pcy.util.BloomFilterUtil;
import com.pcy.util.RedisUtil;
import org.apache.ibatis.ognl.Ognl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * (MovieDetail)表服务实现类
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:53
 */
@Service("movieDetailService")
public class MovieDetailServiceImpl implements MovieDetailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private static final int MOVIE_EXPIRE = 30 * 24 * 60 * 60;

    private static final String REDIS_MOVIE_DETAIL_LOCK = "lock:movieDetail:";

    @Resource
    private MovieDetailDao movieDetailDao;

    @Resource
    RedisUtil redisUtil;

    @Resource
    BloomFilterUtil bloomFilterUtil;

    @Resource
    StringRedisTemplate stringRedisTemplate;



    /**
     * 获取互斥锁
     * @return
     */
    private Boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(flag);
    }

    /**
     * 释放锁
     * @param key
     */
    private void unLock(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param doubanId 主键
     * @return 实例对象
     */
    @Override
    public MovieDetail queryById(Integer doubanId) {
        // 添加布隆过滤器 ===> 防止缓存穿透
        if (!bloomFilterUtil.containsElement(MovieConstant.REDIS_MOVIE_DETAIL_BLOOM, doubanId)) {
            return null;
        }
        String key = "movieDetail:" + doubanId;
        if (redisUtil.exists(key)) {
            String movieDetailJson = redisUtil.get(key);
            logger.info("从Redis中读取" + key);
            return JSON.parseObject(movieDetailJson, MovieDetail.class);
        }
        // 缓存中没有数据，使用互斥锁，从数据库中获取数据，多线程情况下，只有一个线程能够获取到锁
        // 防止缓存击穿
        MovieDetail movieDetail = null;
        String lockKey = REDIS_MOVIE_DETAIL_LOCK + doubanId;
        Boolean lockFlag = tryLock(lockKey);
        try {
            if(!lockFlag){
                // 获锁失败，等待100ms后重试
                Thread.sleep(100);
                return queryById(doubanId);
            }
            // 获锁成功，从数据库中获取数据
            movieDetail = this.movieDetailDao.queryById(doubanId);
            if (movieDetail == null) {
                return null;
            }
            String json = JSON.toJSONString(movieDetail);
            redisUtil.setex(key, MOVIE_EXPIRE, json);
        }catch (Exception e){
            throw new RuntimeException();
        }finally {
            unLock(lockKey);
        }
        return movieDetail;
    }

    /**
     * 通过多个ID查询数据
     *
     * @param doubanIdList 主键列表
     * @return 对象列表
     */
    @Override
    public List<MovieDetail> queryByIdList(List<Integer> doubanIdList) {
        return this.movieDetailDao.queryByIdList(doubanIdList);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MovieDetail> queryAllByLimit(int offset, int limit) {
        return this.movieDetailDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询所有数据
     *
     * @return 对象列表
     */
    @Override
    public List<MovieDetail> queryAllMovieDetails() {
        return this.movieDetailDao.queryAllMovieDetails();
    }


    /**
     * 查询总数据数
     *
     * @return 数据总数
     */
    @Override
    public int count() {
        return this.movieDetailDao.count();
    }


    /**
     * 新增数据
     *
     * @param movieDetail 实例对象
     * @return 实例对象
     */
    @Override
    public MovieDetail insert(MovieDetail movieDetail) {
        this.movieDetailDao.insert(movieDetail);
        return movieDetail;
    }

    /**
     * 修改数据
     *
     * @param movieDetail 实例对象
     * @return 实例对象
     */
    @Override
    public MovieDetail update(MovieDetail movieDetail) {
        this.movieDetailDao.update(movieDetail);
        return this.queryById(movieDetail.getDoubanId());
    }

    /**
     * 通过主键删除数据
     *
     * @param doubanId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer doubanId) {
        return this.movieDetailDao.deleteById(doubanId) > 0;
    }

    /**
     * 分页获取电影详情
     *
     * @param pageNum  当前页
     * @param pageSize 每页多少数据
     * @return 分页数据
     */
    @Override
    public PageInfo<MovieDetail> queryPageMovie(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MovieDetail> movieDetailList = movieDetailDao.queryAllMovieDetails();
        return new PageInfo<>(movieDetailList);
    }

    /**
     * 分页查询
     *
     * @param pageNum     当前页
     * @param pageSize    每页多少数据
     * @param movieDetail 查询条件
     * @return 分页数据
     */
    @Override
    public PageInfo<MovieDetail> queryPage(int pageNum, int pageSize, MovieDetail movieDetail) {
        PageHelper.startPage(pageNum, pageSize);
        List<MovieDetail> movieDetailList = movieDetailDao.queryAll(movieDetail);
        return new PageInfo<>(movieDetailList);
    }
}