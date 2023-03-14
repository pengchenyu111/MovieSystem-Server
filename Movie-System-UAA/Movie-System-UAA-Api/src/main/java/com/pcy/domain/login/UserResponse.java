package com.pcy.domain.login;

import com.pcy.domain.user.MovieUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户在登录阶段返回给前端的对象
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserResponse", description = "用户在登录阶段返回给前端的对象")
public class UserResponse {
    @ApiModelProperty(value = "Token")
    private String token;
    @ApiModelProperty(value = "用户详细信息")
    private MovieUser userInfo;
}
