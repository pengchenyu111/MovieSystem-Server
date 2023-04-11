package com.pcy.constant;

/**
 * 短信服务常量
 *
 * @author PengChenyu
 * @since 2021-07-09 20:42:06
 */
public class SmsConstant {

    /**
     * 短信密钥
     */
    public static final String ACCESS_KEY_ID = "LTAI5tRjkHAak28hVdpEELvb";
    public static final String ACCESS_KEY_SECRET = "VgyuX4RWrkJH5l5upVefyZm7UVXhYy";

    /**
     * 短信服务端点
     */
    public static final String ENDPOINT = "dysmsapi.aliyuncs.com";

    /**
     * 短信签名
     */
    public static final String SIGN_NAME = "智慧黄山";

    /**
     * 短信模板id
     */
    public static final String TEMPLATE_CODE = "SMS_207520927";

    /**
     * group-id
     */
    public static final String MOVIE_SYSTEM_SMS_MAIL_MESSAGE_CONSUMER_GROUP_ID = "smsmail";

    /**
     * 短信验证码Kafka-topic
     */
    public static final String MOVIE_SYSTEM_SMS_VERIFICATION_CODE_TOPIC = "movie_sms_verification_code";


    /**
     * 短信过期时间
     */
    public static final int SMS_EXPIRE = 300;



}
