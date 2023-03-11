package com.pcy.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lanxw
 */
@Setter
@Getter
public class LoginLog implements Serializable {
    public static Boolean LOGIN_SUCCESS = Boolean.TRUE;
    public static Boolean LOGIN_FAIL = Boolean.FALSE;

    public LoginLog() {
        super();
    }

    public LoginLog(Long phone, String loginIp, Date loginTime) {
        this.phone = phone;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
    }

    private Long id;//自增id
    private Long phone;//登陆用户的手机号码
    private String loginIp;//登录IP
    private Date loginTime;///登录时间
    private Boolean state = LOGIN_SUCCESS;//登录状态
}
