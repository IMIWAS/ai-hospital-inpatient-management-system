package com.hims.domain;

import lombok.Data;
import java.util.Date;

/**
 * 患者基础信息实体类
 * 对应表：patient_info
 * 用途：AI自动建档，存储患者基础个人信息
 */
@Data
public class PatientInfo {
    
    /** 患者唯一就诊ID（主键） */
    private String patientId;
    
    /** 患者编号 */
    private String patientNo;
    
    /** 患者姓名 */
    private String name;
    
    /** 性别 */
    private String gender;
    
    /** 年龄 */
    private Integer age;
    
    /** 出生日期 */
    private Date birthDate;
    
    /** 身份证号（AI识别录入） */
    private String idCard;
    
    /** 联系电话 */
    private String phone;
    
    /** 住址 */
    private String address;
    
    /** 紧急联系人 */
    private String emergencyContact;
    
    /** 紧急联系电话 */
    private String emergencyPhone;
    
    /** 既往病史、过敏史 */
    private String medicalHistory;
    
    /** 过敏信息 */
    private String allergyInfo;
    
    /** 血型 */
    private String bloodType;
    
    /** 婚姻状况 */
    private String maritalStatus;
    
    /** 职业 */
    private String occupation;
    
    /** 国籍 */
    private String nationality;
    
    /** 医保类型 */
    private String insuranceType;
    
    /** 头像URL */
    private String avatarUrl;
    
    /** 建档人ID */
    private Integer createUserId;
    
    /** 建档时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 建档人姓名（非数据库字段） */
    private String createUserName;
    
    /** 住院状态：1在院 2已出院（非数据库字段） */
    private Integer inhospitalStatus;
    
    /** 当前住院记录ID（非数据库字段） */
    private Integer currentInhospitalId;
    
    /** 当前住院号（非数据库字段） */
    private String currentInhospitalNo;
}
