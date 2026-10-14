package com.hims.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 豆包AI配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "hims.doubao")
public class DoubaoConfig {
    
    /**
     * API密钥
     */
    private String apiKey;
    
    /**
     * API基础URL
     */
    private String baseUrl;
    
    /**
     * 模型ID
     */
    private String modelId;
    
    /**
     * 超时时间（秒）
     */
    private Integer timeoutSeconds;
}
