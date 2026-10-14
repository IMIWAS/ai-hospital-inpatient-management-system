package com.hims.domain;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用药医嘱实体类
 * 对应表：patient_drug
 * 用途：管理患者住院用药、医嘱执行记录
 */
@Data
public class PatientDrug {
    
    /** 医嘱ID */
    private Integer drugId;
    
    /** 患者ID */
    private String patientId;
    
    /** 住院记录ID */
    private Integer inhospitalId;
    
    /** 处方号 */
    private String prescriptionNo;
    
    /** 药品名称 */
    private String drugName;
    
    /** 药品规格 */
    private String drugSpec;
    
    /** 用药剂量 */
    private String drugDose;
    
    /** 剂量单位 */
    private String drugUnit;
    
    /** 用药频次：每日1次/每日2次/每日3次/睡前服用等 */
    private String drugFrequency;
    
    /** 给药途径：口服/静脉注射/肌肉注射/皮下注射/外用 */
    private String drugRoute;
    
    /** 医嘱说明 */
    private String doctorAdvice;
    
    /** 开始时间 */
    private Date startTime;
    
    /** 结束时间 */
    private Date endTime;
    
    /** 用药天数 */
    private Integer days;
    
    /** 总量 */
    private String totalQuantity;
    
    /** 单价 */
    private BigDecimal price;
    
    /** 总价 */
    private BigDecimal totalPrice;
    
    /** 执行状态：0未执行 1已执行 2暂停 3完成 */
    private Integer executeStatus;
    
    /** 执行时间 */
    private Date executeTime;
    
    /** 执行人ID */
    private Integer executeUserId;
    
    /** 开嘱医生ID */
    private Integer doctorId;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 患者姓名（非数据库字段） */
    private String patientName;
    
    /** 住院号（非数据库字段） */
    private String inhospitalNo;
    
    /** 开嘱医生姓名（非数据库字段） */
    private String doctorName;
    
    /** 执行人姓名（非数据库字段） */
    private String executeUserName;
    
    /** 执行状态名称（非数据库字段） */
    private String executeStatusName;
}
