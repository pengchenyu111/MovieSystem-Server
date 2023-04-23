package com.pcy.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MovieUserRatings)实体类
 *
 * @author PengChenyu
 * @since 2020-12-30 16:25:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieUserRatings implements Serializable {

    private static final long serialVersionUID = -39156122020235906L;

    private String reviewId;

    private Integer doubanId;

    private Integer userId;

    private Double userMovieRating;

    private String userMovieRatingTime;


}