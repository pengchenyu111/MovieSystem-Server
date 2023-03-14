package com.pcy.commmon.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据基本类型转化类
 *
 * @author PengChenyu
 * @since 2020-12-19 16:01:42
 */
public class ObjectUtil {

    /**
     * String转成列表
     *
     * @param str      源字符串
     * @param division 分隔符
     * @return List<String>列表
     */
    public static List<String> transforString2List(String str, String division) {
        if (!StringUtils.isEmpty(str)) {
            String[] strArr = str.split(division);
            if (strArr.length != 0) {
                return Arrays.asList(strArr);
            }
        }
        return new ArrayList<>();
    }

}
