package com.pcy.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PengChenyu
 * @since 2021-10-09 20:31:45
 */
@ApiModel(value = "com-pcy-domain-VerificationCodeSendRecord")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeSendRecord implements Serializable {
    /**
     * 验证码短信id
     */
    @ApiModelProperty(value = "验证码短信id")
    private Long id;

    /**
     * 发送目标电话号码
     */
    @ApiModelProperty(value = "发送目标电话号码")
    private String phoneNumber;

    /**
     * 六位验证码
     */
    @ApiModelProperty(value = "六位验证码")
    private String verificationCode;

    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    /**
     * 发送是否成功标志，1成功，0失败
     */
    @ApiModelProperty(value = "发送是否成功标志，1成功，0失败")
    private String successFlag;

    /**
     * 阿里云请求id
     */
    @ApiModelProperty(value = "阿里云请求id")
    private String requestId;

    /**
     * 事务id
     */
    @ApiModelProperty(value = "事务id")
    private String transactionId;

    private static final long serialVersionUID = 1L;
}