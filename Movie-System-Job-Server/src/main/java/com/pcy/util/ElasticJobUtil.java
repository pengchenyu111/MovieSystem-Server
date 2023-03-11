package com.pcy.util;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import org.apache.commons.lang.StringUtils;

/**
 * Created by lanxw
 * 工具类，用于创建LiteJobConfiguration对象
 */
public class ElasticJobUtil {
    public static LiteJobConfiguration createJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                              final String cron,
                                                              final int shardingTotalCount,
                                                              final String shardingItemParameters,
                                                              boolean dataflowType) {
        // 定义作业核心配置
        JobCoreConfiguration.Builder jobCoreConfigurationBuilder = JobCoreConfiguration.newBuilder(jobClass.getSimpleName(), cron, shardingTotalCount);
        if (!StringUtils.isEmpty(shardingItemParameters)) {
            jobCoreConfigurationBuilder.shardingItemParameters(shardingItemParameters);
        }
        JobTypeConfiguration jobConfig = null;
        if (dataflowType) {
            jobConfig = new DataflowJobConfiguration(jobCoreConfigurationBuilder.build(), jobClass.getCanonicalName(), true);
        } else {
            // 定义SIMPLE类型配置
            jobConfig = new SimpleJobConfiguration(jobCoreConfigurationBuilder.build(), jobClass.getCanonicalName());
        }
        // 定义Lite作业根配置
        return LiteJobConfiguration.newBuilder(jobConfig).overwrite(true).build();
    }

    public static LiteJobConfiguration createDefaultSimpleJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron) {
        return createJobConfiguration(jobClass, cron, 1, null, false);
    }

    public static LiteJobConfiguration createDefaultDataFlowJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron) {
        return createJobConfiguration(jobClass, cron, 1, null, true);
    }
}
