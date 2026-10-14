package com.hims.domain;

import lombok.Data;
import java.util.Date;

/**
 * 患者生命体征与风险数据实体类
 * 对应表：patient_vital
 * 用途：AI风险预警模型核心数据源，用于病情智能评估
 */
@Data
public class PatientVital {
    
    /** 体征记录ID */
    private Integer vitalId;
    
    /** 患者ID */
    private String patientId;
    
    /** 住院记录ID */
    private Integer inhospitalId;
    
    /** 体温（℃） */
    private Float temperature;
    
    /** 心率（次/分） */
    private Integer heartRate;
    
    /** 收缩压（mmHg） */
    private Integer bpHigh;
    
    /** 舒张压（mmHg） */
    private Integer bpLow;
    
    /** 血氧饱和度（%） */
    private Float bloodOxygen;
    
    /** 呼吸频率（次/分） */
    private Integer breathRate;
    
    /** 体重（kg） */
    private Float weight;
    
    /** 身高（cm） */
    private Float height;
    
    /** 血糖（mmol/L） */
    private Float bloodSugar;
    
    /** AI风险等级：1低危 2中危 3高危 */
    private Integer riskLevel;
    
    /** 风险评分 */
    private Float riskScore;
    
    /** AI预警提示内容 */
    private String riskTip;
    
    /** 记录人ID */
    private Integer recordUserId;
    
    /** 记录时间 */
    private Date recordTime;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 患者姓名（非数据库字段） */
    private String patientName;
    
    /** 住院号（非数据库字段） */
    private String inhospitalNo;
    
    /** 风险等级名称（非数据库字段） */
    private String riskLevelName;
    
    /** 记录人姓名（非数据库字段） */
    private String recordUserName;
}
