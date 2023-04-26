package com.pcy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (MovieDetail)实体类
 *
 * @author PengChenyu
 * @since 2020-12-21 21:41:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetail implements Serializable {

    private static final long serialVersionUID = -42725455283108651L;

    private Integer doubanId;

    private String title;

    private String briefInstruction;

    private String directors;

    private String screenwriters;

    private String casts;

    private String types;

    private String productionCountryArea;

    private String language;

    private String publishDate;

    private String runtime;

    private Double ratingScore;

    private Integer ratingStar;

    private Integer ratingNum;

    private String rating5StarWeight;

    private String rating4StarWeight;

    private String rating3StarWeight;

    private String rating2StarWeight;

    private String rating1StarWeight;

    private String betterThan;

    private String doubanUrl;

    private String coverUrl;

    private String imdbUrl;

    private String imgList;


}