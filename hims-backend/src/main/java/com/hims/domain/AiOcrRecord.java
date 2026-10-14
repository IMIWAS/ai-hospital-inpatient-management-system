package com.hims.domain;

import lombok.Data;
import java.util.Date;

/**
 * AI识别记录实体类
 * 对应表：ai_ocr_record
 * 用途：存储AI OCR识别的历史记录
 */
@Data
public class AiOcrRecord {
    
    /** OCR记录ID */
    private Integer ocrId;
    
    /** 关联患者ID（识别后可能创建或关联） */
    private String patientId;
    
    /** 识别类型：身份证/就诊卡/医保卡/检查报告/病历首页 */
    private String ocrType;
    
    /** 图片存储路径 */
    private String imageUrl;
    
    /** 图片Base64（可选存储） */
    private String imageBase64;
    
    /** OCR识别结果（JSON格式） */
    private String ocrResult;
    
    /** 识别置信度 */
    private Float ocrConfidence;
    
    /** 提取的关键字段（JSON格式） */
    private String extractedFields;
    
    /** 处理状态：0待处理 1已识别 2已建档 3识别失败 */
    private Integer status;
    
    /** 错误信息 */
    private String errorMsg;
    
    /** 操作人ID */
    private Integer operatorId;
    
    /** 识别时间 */
    private Date createTime;
    
    /** 患者姓名（非数据库字段） */
    private String patientName;
    
    /** 操作人姓名（非数据库字段） */
    private String operatorName;
    
    /** 状态名称（非数据库字段） */
    private String statusName;
}
