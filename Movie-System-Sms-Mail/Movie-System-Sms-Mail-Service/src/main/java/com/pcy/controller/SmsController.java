package com.pcy.controller;

import com.pcy.commmon.web.ApiResponse;
import com.pcy.commmon.web.ErrorMessages;
import com.pcy.constant.SmsConstant;
import com.pcy.service.VerificationCodeSendRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author PengChenyu
 * @since 2021-07-08 11:45:16
 */
@Api(tags = "验证码接口")
@RestController
@RequestMapping("sms")
public class SmsController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private VerificationCodeSendRecordService verificationCodeSendRecordService;

    /**
     * 发送验证码
     * @param phoneNumber 用户手机号
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "phoneNumber", value = "用户手机号", required = true, dataType = "Integer")
    })
    @GetMapping("/send/vericode/{phoneNumber}")
    public ApiResponse<Boolean> send(@PathVariable("phoneNumber") String phoneNumber) throws Exception {
        String txId = UUID.randomUUID().toString();
        String msg = txId + "=" + phoneNumber;
        kafkaTemplate.send(SmsConstant.MOVIE_SYSTEM_SMS_VERIFICATION_CODE_TOPIC, msg);
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.REQUEST_SUCCESS, null);
    }


    @ApiOperation(value = "验证码", notes = "检查验证码是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "phoneNumber", value = "用户手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "code", value = "用户填写的验证码", required = true, dataType = "String")
    })
    @GetMapping("/check/{phoneNumber}/{code}")
    public ApiResponse<Boolean>  checkCode(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("code") String code) {
        boolean isCorrect = verificationCodeSendRecordService.checkCode(phoneNumber, code);
        if (!isCorrect) {
            return new ApiResponse<>(Boolean.FALSE, ErrorMessages.VERIFICATION_WRONG, null);
        }
        return new ApiResponse<>(Boolean.TRUE, ErrorMessages.VERIFICATION_CORRECT, null);
    }

}
