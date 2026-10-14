package com.hims.domain;

import lombok.Data;
import java.util.Date;

/**
 * 病程记录实体类
 * 对应表：patient_course
 * 用途：存储AI语音自动生成的病程记录、医生手动修改内容
 */
@Data
public class PatientCourse {
    
    /** 病程记录ID */
    private Integer courseId;
    
    /** 患者ID */
    private String patientId;
    
    /** 住院记录ID */
    private Integer inhospitalId;
    
    /** 记录医生ID */
    private Integer doctorId;
    
    /** 病程内容（AI语音自动生成+人工修正） */
    private String courseContent;
    
    /** 病程摘要 */
    private String courseSummary;
    
    /** 记录类型：日常病程/查房记录/抢救记录/手术记录/出院记录 */
    private String recordType;
    
    /** 是否AI生成：0否 1是 */
    private Integer isAiGenerated;
    
    /** AI生成置信度 */
    private Float aiConfidence;
    
    /** 附件（JSON格式存储文件路径） */
    private String attachments;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 患者姓名（非数据库字段） */
    private String patientName;
    
    /** 住院号（非数据库字段） */
    private String inhospitalNo;
    
    /** 记录医生姓名（非数据库字段） */
    private String doctorName;
    
    /** 记录类型名称（非数据库字段） */
    private String recordTypeName;
}
