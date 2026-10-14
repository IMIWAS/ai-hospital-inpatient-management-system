package com.hims.domain;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 患者住院记录实体类
 * 对应表：patient_inhospital
 * 用途：记录患者入院、在院、出院全流程住院信息
 */
@Data
public class PatientInhospital {
    
    /** 住院记录ID（主键） */
    private Integer inhospitalId;
    
    /** 关联患者ID */
    private String patientId;
    
    /** 住院号（唯一） */
    private String inhospitalNo;
    
    /** 住院科室 */
    private String department;
    
    /** 关联床位ID */
    private Integer bedId;
    
    /** 入院诊断 */
    private String diagnosis;
    
    /** ICD诊断编码 */
    private String diagnosisIcd;
    
    /** 入院时间 */
    private Date inhospitalTime;
    
    /** 主管医生ID */
    private Integer doctorId;
    
    /** 责任护士ID */
    private Integer nurseId;
    
    /** 出院时间（为空代表在院） */
    private Date outhospitalTime;
    
    /** 住院天数 */
    private Integer inhospitalDays;
    
    /** 总费用 */
    private BigDecimal totalCost;
    
    /** 结算状态：0未结算 1已结算 */
    private Integer settlementStatus;
    
    /** 结算时间 */
    private Date settlementTime;
    
    /** 住院状态：1在院 2已出院 3转科 4请假 */
    private Integer inhospitalStatus;
    
    /** 备注 */
    private String remark;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 患者姓名（非数据库字段） */
    private String patientName;
    
    /** 性别（非数据库字段） */
    private String patientGender;
    
    /** 年龄（非数据库字段） */
    private Integer patientAge;
    
    /** 床位号（非数据库字段） */
    private String bedNo;
    
    /** 病房号（非数据库字段） */
    private String roomNo;
    
    /** 主管医生姓名（非数据库字段） */
    private String doctorName;
    
    /** 责任护士姓名（非数据库字段） */
    private String nurseName;
    
    /** 状态名称（非数据库字段） */
    private String statusName;
}
