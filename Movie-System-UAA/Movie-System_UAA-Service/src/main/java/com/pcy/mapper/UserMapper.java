package com.pcy.mapper;


import com.pcy.domain.login.LoginLog;
import com.pcy.domain.user.MovieUser;
import com.pcy.domain.login.UserLogin;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by wolfcode-lanxw
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户手机号码查询用户登录信息对象
     *
     * @param phone
     * @return
     */
    UserLogin selectUserLoginByPhone(Long phone);

    /**
     * 根据用户手机号码查询用户的基础信息
     *
     * @param phone
     * @return
     */
    MovieUser selectUserInfoByPhone(Long phone);

    /**
     * 插入登录日志
     *
     * @param loginLog
     * @return
     */
    int insertLoginLog(LoginLog loginLog);
}
