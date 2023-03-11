package com.pcy.commmon.web;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author PengChenyu
 * @Date 2023/3/5 23:35
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 请求返回描述
     */
    private String message;

    /**
     * 具体的返回数据
     */
    private T data;

    /**
     * 请求成功
     *
     * @param data 数据
     * @return 请求成功的message + 数据data
     */
    public static <T> ApiResponse success(T data) {
        return ApiResponse.success(ErrorMessages.REQUEST_SUCCESS, data);
    }

    /**
     * 请求成功
     *
     * @param message 自定义message
     * @return 请求成功的message
     */
    public static ApiResponse success(String message) {
        return new ApiResponse<>(Boolean.TRUE, message, null);
    }

    /**
     * 请求成功
     *
     * @param message 自定义message
     * @param data    数据
     * @return 成功bool值 + 请求成功的message + 数据data
     */
    public static <T> ApiResponse success(String message, T data) {
        return new ApiResponse<>(Boolean.TRUE, message, data);
    }

    /**
     * 请求失败
     *
     * @return 请求失败的message
     */
    public static ApiResponse failed() {
        return ApiResponse.failed(ErrorMessages.REQUEST_FAIL);
    }

    /**
     * 请求失败
     *
     * @param message 自定义message
     * @return 失败bool值 + 请求失败的message + 空数据data
     */
    public static ApiResponse failed(String message) {
        return new ApiResponse<>(Boolean.FALSE, message, null);
    }

    /**
     * 请求失败
     *
     * @param codeMsg 自定义message
     * @return 失败bool值 + 请求失败的message + 空数据data
     */
    public static ApiResponse failed(CodeMsg codeMsg) {
        return new ApiResponse<>(Boolean.FALSE, codeMsg.getMsg(), null);
    }

    /**
     * 请求失败
     *
     * @return 失败bool值 + 请求失败的message + 空数据data
     */
    public static ApiResponse defaultError() {
        return new ApiResponse<>(Boolean.FALSE, ErrorMessages.SYSTEM_ERROR_MESSAGE, null);
    }
}