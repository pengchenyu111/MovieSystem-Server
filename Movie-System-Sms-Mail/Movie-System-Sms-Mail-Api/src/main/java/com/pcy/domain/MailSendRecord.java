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
@ApiModel(value = "com-pcy-domain-MailSendRecord")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailSendRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮件id
     */
    @ApiModelProperty(value = "邮件id")
    private Long id;

    /**
     * 发信地址
     */
    @ApiModelProperty(value = "发信地址")
    private String accountName;

    /**
     * 目标收件人地址
     */
    @ApiModelProperty(value = "目标收件人地址")
    private String toAddress;

    /**
     * 邮件主题
     */
    @ApiModelProperty(value = "邮件主题")
    private String subject;

    /**
     * 邮件标签
     */
    @ApiModelProperty(value = "邮件标签")
    private String tagName;

    /**
     * 邮件 html 正文，限制28K，即最多9557个汉字
     */
    @ApiModelProperty(value = "邮件 html 正文，限制28K，即最多9557个汉字")
    private String mailHtmlBody;

    /**
     * 邮件 text 正文，限制28K，即最多9557个汉字
     */
    @ApiModelProperty(value = "邮件 text 正文，限制28K，即最多9557个汉字")
    private String mailTextBody;

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


}