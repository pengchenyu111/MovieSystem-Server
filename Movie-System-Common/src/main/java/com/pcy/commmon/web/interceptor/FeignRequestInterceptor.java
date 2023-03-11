package com.pcy.commmon.web.interceptor;

import com.pcy.commmon.constant.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by lanxw
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header(CommonConstants.FEIGN_REQUEST_KEY, CommonConstants.FEIGN_REQUEST_TRUE);
    }
}
