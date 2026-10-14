package com.hims.service;

import com.hims.domain.PatientVital;
import com.hims.query.PatientVitalQuery;
import java.util.Map;

/**
 * 生命体征服务接口
 */
public interface PatientVitalService {
    
    /**
     * 分页查询生命体征记录
     */
    Map<String, Object> getPageList(PatientVitalQuery query);
    
    /**
     * 根据ID查询
     */
    PatientVital getById(Integer vitalId);
    
    /**
     * 根据患者ID查询最新体征
     */
    PatientVital getLatestByPatientId(String patientId);
    
    /**
     * 根据患者ID查询体征历史
     */
    Map<String, Object> getHistoryByPatientId(String patientId, Integer limit);
    
    /**
     * 记录生命体征（包含AI风险评估）
     */
    PatientVital recordVital(PatientVital vital, Integer recordUserId);
    
    /**
     * 更新生命体征
     */
    boolean update(PatientVital vital);
    
    /**
     * 删除记录
     */
    boolean delete(Integer vitalId);
    
    /**
     * 获取高危患者列表
     */
    Map<String, Object> getHighRiskPatients();
    
    /**
     * AI风险评估
     */
    Map<String, Object> aiRiskAssessment(PatientVital vital);
    
    /**
     * 获取体征异常统计
     */
    Map<String, Object> getAbnormalStats(String startDate, String endDate);
}
