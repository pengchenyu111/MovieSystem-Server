package com.pcy.service;


import com.pcy.domain.MovieUser;
import com.pcy.domain.UserResponse;

/**
 * Created by wolfcode-lanxw
 */
public interface IUserService {
    /**
     * 登录功能
     * @param phone 用户的账号/用户的手机号码
     * @param password 用户的密码
     * @param ip 客户端IP
     * @return
     */
    UserResponse login(Long phone, String password, String ip);

    /**
     * 根据手机获取UserInfo
     * @param phone
     * @return
     */
    MovieUser getUserInfo(Long phone);
}
