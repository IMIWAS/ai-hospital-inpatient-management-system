package com.hims.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "hims.upload")
public class UploadConfig {
    
    /**
     * 本地存储路径
     */
    private String path;
    
    /**
     * 数据库中存储的访问前缀
     */
    private String urlPrefix;
    
    /**
     * 单文件最大大小
     */
    private Long maxSize;
}
