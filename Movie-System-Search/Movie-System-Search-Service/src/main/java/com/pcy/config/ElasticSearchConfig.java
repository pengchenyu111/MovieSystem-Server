package com.pcy.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * ElasticSearch配置类
 *
 * @author PengChenyu
 * @since 2020-12-24 20:42:15
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.url}")
    private String url;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(url)
                .withBasicAuth(username, password)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
