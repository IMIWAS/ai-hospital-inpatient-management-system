package com.hims.domain;

import lombok.Data;
import java.util.Date;

/**
 * 出院随访实体类
 * 对应表：patient_follow
 * 用途：出院患者AI智能随访、康复跟踪管理
 */
@Data
public class PatientFollow {
    
    /** 随访记录ID */
    private Integer followId;
    
    /** 患者ID */
    private String patientId;
    
    /** 出院住院记录ID */
    private Integer inhospitalId;
    
    /** 随访类型：常规随访/术后随访/慢病随访/康复随访 */
    private String followType;
    
    /** 计划随访时间（AI自动生成） */
    private Date followTime;
    
    /** 实际随访时间 */
    private Date followActualTime;
    
    /** 随访方式：电话随访/门诊随访/上门随访/视频随访 */
    private String followWay;
    
    /** 随访内容、患者康复情况 */
    private String followContent;
    
    /** 随访结果 */
    private String followResult;
    
    /** 症状描述 */
    private String symptoms;
    
    /** 用药依从性：良好/一般/差 */
    private String medicationCompliance;
    
    /** 恢复状态：好转/稳定/恶化 */
    private String recoveryStatus;
    
    /** 下次随访时间 */
    private Date nextFollowTime;
    
    /** AI康复指导建议 */
    private String aiSuggest;
    
    /** 随访人ID */
    private Integer followUserId;
    
    /** 随访状态：0待随访 1已完成 2失联 3取消 */
    private Integer followStatus;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 患者姓名（非数据库字段） */
    private String patientName;
    
    /** 住院号（非数据库字段） */
    private String inhospitalNo;
    
    /** 随访人姓名（非数据库字段） */
    private String followUserName;
    
    /** 随访状态名称（非数据库字段） */
    private String followStatusName;
}
