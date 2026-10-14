package com.hims.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 风险预警配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "hims.risk")
public class RiskConfig {
    
    // 体温阈值
    private Float tempHigh = 39.0f;
    private Float tempLow = 35.0f;
    
    // 收缩压阈值
    private Integer bpHighHigh = 160;
    private Integer bpHighLow = 90;
    
    // 舒张压阈值
    private Integer bpLowHigh = 100;
    private Integer bpLowLow = 60;
    
    // 心率阈值
    private Integer heartRateHigh = 120;
    private Integer heartRateLow = 50;
    
    // 血氧阈值
    private Float bloodOxygenLow = 90.0f;
}
