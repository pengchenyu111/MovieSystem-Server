package com.pcy.commmon.exception;

import com.pcy.commmon.web.CodeMsg;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wolfcode-lanxw
 */
@Setter
@Getter
public class BusinessException extends RuntimeException {
    private CodeMsg codeMsg;
    public BusinessException(CodeMsg codeMsg){
        this.codeMsg = codeMsg;
    }
}
