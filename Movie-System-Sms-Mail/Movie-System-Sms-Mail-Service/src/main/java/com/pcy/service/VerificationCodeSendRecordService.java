package com.pcy.service;

import com.pcy.domain.VerificationCodeSendRecord;


/**
 * @author PengChenyu
 * @since 2021-10-09 20:31:45
 */
public interface VerificationCodeSendRecordService {

    /**
     * 插入验证码记录
     *
     * @param record
     * @return 是否成功
     */
    boolean save(VerificationCodeSendRecord record);


    boolean checkCode(String phoneNumber, String code);
}
