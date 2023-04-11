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
@ApiModel(value = "com-pcy-domain-MailTemplate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailTemplate implements Serializable {
    /**
     * 邮件模板id
     */
    @ApiModelProperty(value = "邮件模板id")
    private Integer id;

    /**
     * 模板名
     */
    @ApiModelProperty(value = "模板名")
    private String templateName;

    /**
     * 模板类型，0文字模板，1HTML模板
     */
    @ApiModelProperty(value = "模板类型，0文字模板，1HTML模板")
    private String templateType;

    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容")
    private String templateBody;

    /**
     * 是否可用，0不可以，1可用
     */
    @ApiModelProperty(value = "是否可用，0不可以，1可用")
    private String status;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Long modifyBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @ApiModelProperty(value = "最后修改时间")
    private Date lastUpdateTime;

    private static final long serialVersionUID = 1L;
}