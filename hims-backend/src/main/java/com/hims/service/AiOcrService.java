package com.hims.service;

import com.hims.domain.AiOcrRecord;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

/**
 * AI OCR识别服务接口
 */
public interface AiOcrService {
    
    /**
     * OCR识别身份证
     */
    Map<String, Object> recognizeIdCard(MultipartFile file, Integer operatorId);
    
    /**
     * OCR识别医疗单据
     */
    Map<String, Object> recognizeMedicalDocument(MultipartFile file, String ocrType, Integer operatorId);
    
    /**
     * 获取识别记录
     */
    AiOcrRecord getRecordById(Integer ocrId);
    
    /**
     * 根据患者ID查询识别记录
     */
    Map<String, Object> getRecordsByPatientId(String patientId);
    
    /**
     * 分页查询识别记录
     */
    Map<String, Object> getPageList(Integer pageNum, Integer pageSize, String ocrType, Integer status);
    
    /**
     * 更新识别记录状态
     */
    boolean updateStatus(Integer ocrId, Integer status, String patientId);
    
    /**
     * 删除识别记录
     */
    boolean deleteRecord(Integer ocrId);
}
