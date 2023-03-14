package com.pcy.domain.login;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lanxw
 */
@Setter
@Getter
public class UserLogin implements Serializable {
    private Long phone;//手机号码
    private String password;//密码
    private String salt;//加密使用的盐
}
