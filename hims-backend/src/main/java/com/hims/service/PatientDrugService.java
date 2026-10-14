package com.hims.service;

import com.hims.domain.PatientDrug;
import com.hims.query.PatientDrugQuery;
import java.util.Map;

/**
 * 用药医嘱服务接口
 */
public interface PatientDrugService {
    
    /**
     * 分页查询用药医嘱
     */
    Map<String, Object> getPageList(PatientDrugQuery query);
    
    /**
     * 根据ID查询
     */
    PatientDrug getById(Integer drugId);
    
    /**
     * 根据患者ID查询医嘱
     */
    Map<String, Object> getByPatientId(String patientId);
    
    /**
     * 根据住院ID查询医嘱
     */
    Map<String, Object> getByInhospitalId(Integer inhospitalId);
    
    /**
     * 查询患者待执行医嘱
     */
    Map<String, Object> getPendingDrugs(String patientId);
    
    /**
     * 创建医嘱
     */
    PatientDrug create(PatientDrug drug);
    
    /**
     * 更新医嘱
     */
    boolean update(PatientDrug drug);
    
    /**
     * 执行医嘱
     */
    boolean execute(Integer drugId, Integer executeUserId);
    
    /**
     * 暂停医嘱
     */
    boolean pause(Integer drugId);
    
    /**
     * 删除医嘱
     */
    boolean delete(Integer drugId);
    
    /**
     * 获取药品使用统计
     */
    Map<String, Object> getDrugUsageStats(String startDate, String endDate, String department);
}
