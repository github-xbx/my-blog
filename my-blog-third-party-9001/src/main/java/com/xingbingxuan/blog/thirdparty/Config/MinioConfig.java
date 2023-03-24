package com.xingbingxuan.blog.thirdparty.Config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minIO 文件服务配置
 * @author : xbx
 * @date : 2023/3/23 22:04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucketName;


    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(url).credentials(accessKey,secretKey).build();
    }

}
