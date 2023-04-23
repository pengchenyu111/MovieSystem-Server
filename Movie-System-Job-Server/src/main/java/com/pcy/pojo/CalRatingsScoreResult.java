package com.pcy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author PengChenyu
 * @Date 2023/4/21 21:56
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalRatingsScoreResult implements Serializable {

    private static final long serialVersionUID = -351431287640173590L;

    private Integer doubanId;

    private Double ratingScore;

    private Integer ratingStar;

    private Integer ratingNum;

    private String rating5StarWeight;

    private String rating4StarWeight;

    private String rating3StarWeight;

    private String rating2StarWeight;

    private String rating1StarWeight;


}
