package com.pcy.commmon.exception;

import com.pcy.commmon.web.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wolfcode-lanxw
 */
public class CommonControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiResponse handleBusinessException(BusinessException ex) {
        return ApiResponse.failed(ex.getCodeMsg());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse handleDefaultException(Exception ex) {
        ex.printStackTrace();//在控制台打印错误消息.
        return ApiResponse.defaultError();
    }
}
