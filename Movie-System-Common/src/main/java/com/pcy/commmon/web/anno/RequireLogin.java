package com.pcy.commmon.web.anno;

import java.lang.annotation.*;

/**
 * @author PengChenyu
 *
 */
@Target({ElementType.METHOD}) // 注解的使用范围
@Retention(RetentionPolicy.RUNTIME) // 运行级别保留，编译后的class文件中存在，在jvm运行时保留，可以被反射调用。
@Documented
public @interface RequireLogin {
}
