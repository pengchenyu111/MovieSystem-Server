package com.pcy.controller;

import com.pcy.commmon.constant.CommonConstants;
import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.domain.MovieUser;
import com.pcy.domain.UserLogin;
import com.pcy.domain.UserResponse;
import com.pcy.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lanxw
 * 登录操作
 */
@RestController
@RequestMapping("/login")
@Api(tags = "用户登录接口")
public class LoginController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "用户登录接口", notes = "用户登录接口，返回用户的详细信息和Token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userLogin", value = "登录参数，含手机号和密码", required = true, dataTypeClass = UserLogin.class)
    })
    @PostMapping
    public ApiResponse<UserResponse> login(@RequestBody UserLogin userLogin, HttpServletRequest request) {
        /**
         * 获取用户IP,因为微服务的请求是网关转发过来的.
         * 所以request.getRemoteAddr()获取到的是网关的IP
         * 我们需要在网关中获取到真实IP,然后放入到请求头中。
         * 在微服务中通过获取请求头从而获取到真实的客户端IP
         */
        String ip = request.getHeader(CommonConstants.REAL_IP);
        //进行登录，并将这个token返回给前台
        UserResponse userResponse = userService.login(userLogin.getPhone(), userLogin.getPassword(), ip);
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.LOGIN_SUCCESS, userResponse);
    }
}
