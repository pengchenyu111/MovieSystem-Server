package com.pcy.service.impl;

import com.pcy.domain.VerificationCodeSendRecord;
import com.pcy.mapper.VerificationCodeSendRecordMapper;
import com.pcy.service.VerificationCodeSendRecordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author PengChenyu
 * @since 2020-12-20 00:20:34
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeSendRecordService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private VerificationCodeSendRecordMapper verificationCodeSendRecordMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean save(VerificationCodeSendRecord record) {
        int saveFlag = verificationCodeSendRecordMapper.save(record);
        return saveFlag == 1;
    }

    @Override
    public boolean checkCode(String phoneNumber, String code) {
        logger.info("验证码验证===> 手机号[{}]，验证码[{}]", phoneNumber, code);
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        // 检查Redis中的验证码
        String key = "verificationCode:" + phoneNumber;
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(key))) {
            return false;
        }
        String codeInRedis = stringRedisTemplate.opsForValue().get(key);
        return code.equals(codeInRedis);
    }
}
