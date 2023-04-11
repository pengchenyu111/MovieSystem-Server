package com.pcy.mq;

import cn.hutool.core.date.DateUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.pcy.constant.SmsConstant;
import com.pcy.domain.VerificationCodeSendRecord;
import com.pcy.mapper.VerificationCodeSendRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author PengChenyu
 * @Date 2023/4/10 22:35
 * @Version 1.0
 */
@Component
@Slf4j
public class SmsMessageListener {

    @Resource
    private VerificationCodeSendRecordMapper verificationCodeSendRecordMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @KafkaListener(topics = {SmsConstant.MOVIE_SYSTEM_SMS_VERIFICATION_CODE_TOPIC}, groupId = SmsConstant.MOVIE_SYSTEM_SMS_MAIL_MESSAGE_CONSUMER_GROUP_ID)
    public void onMessage(String message) throws Exception {
        String[] split = message.split("=");
        String txid = split[0];
        String phoneNumber = split[1];
        sendVerificationTo(phoneNumber, txid);
    }

    /**
     * 发送验证码短信到用户
     *
     * @param phoneNumber 用户手机号
     * @return
     */
    @Transactional
    void sendVerificationTo(String phoneNumber, String txId) {
        // 发送验证码短信
        Client client = null;
        try {
            client = createClient(SmsConstant.ACCESS_KEY_ID, SmsConstant.ACCESS_KEY_SECRET);
            String verificationCode = generateVerifyCode();
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber)
                    .setSignName(SmsConstant.SIGN_NAME)
                    .setTemplateCode(SmsConstant.TEMPLATE_CODE)
                    .setTemplateParam("{code:" + verificationCode + "}");
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            boolean isSuccess = "OK".equals(sendSmsResponse.getBody().getCode());
            log.info("目标用户 => {}，验证码 => {}，信息发送是否发送成功 => {}", phoneNumber, verificationCode, isSuccess);
            // 数据库存入记录
            VerificationCodeSendRecord record = VerificationCodeSendRecord.builder()
                    .phoneNumber(phoneNumber)
                    .verificationCode(verificationCode)
                    .sendTime(DateUtil.parse(DateUtil.now()))
                    .successFlag(isSuccess ? "1" : "0")
                    .requestId(sendSmsResponse.getBody().getRequestId())
                    .transactionId(txId)
                    .build();
            verificationCodeSendRecordMapper.save(record);
            // redis
            writeIntoRedis(phoneNumber, verificationCode);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常时Redis不会回滚，这里直接删除相关key
            deleteFromRedis(phoneNumber);
        }
    }

    /**
     * 将验证码写入Redis
     *
     * @param phoneNumber 电话号码
     * @param sixNum      验证码
     */
    private boolean writeIntoRedis(String phoneNumber, String sixNum) {
        String key = "verificationCode:" + phoneNumber;
        stringRedisTemplate.opsForValue().set(key, sixNum, SmsConstant.SMS_EXPIRE, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    /**
     * 出现异常时
     * 删除Redis中的验证码
     *
     * @param phoneNumber 电话号码
     */
    private boolean deleteFromRedis(String phoneNumber) {
        String key = "verificationCode:" + phoneNumber;
        if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))){
            stringRedisTemplate.delete(key);
        }
        return Boolean.FALSE.equals(stringRedisTemplate.hasKey(key));
    }


    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    private Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = SmsConstant.ENDPOINT;
        return new Client(config);
    }

    /**
     * 生成验证码
     *
     * @return 六位验证码
     */
    private String generateVerifyCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }
}
