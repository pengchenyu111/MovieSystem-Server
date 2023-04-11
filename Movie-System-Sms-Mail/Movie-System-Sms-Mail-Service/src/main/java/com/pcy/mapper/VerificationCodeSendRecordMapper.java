package com.pcy.mapper;

import com.pcy.domain.VerificationCodeSendRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author PengChenyu
 * @since 2021-10-09 20:31:45
 */
@Repository
@Mapper
public interface VerificationCodeSendRecordMapper{

    int save(VerificationCodeSendRecord record);
}