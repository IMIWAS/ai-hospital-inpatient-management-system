package com.hims.service;

import com.hims.domain.PatientInfo;
import com.hims.query.PatientInfoQuery;
import java.util.Map;

/**
 * 患者信息服务接口
 */
public interface PatientInfoService {
    
    /**
     * 分页查询患者列表
     */
    Map<String, Object> getPageList(PatientInfoQuery query);
    
    /**
     * 根据ID查询患者
     */
    PatientInfo getById(String patientId);
    
    /**
     * 根据身份证号查询患者
     */
    PatientInfo getByIdCard(String idCard);
    
    /**
     * 根据住院号查询患者
     */
    PatientInfo getByInhospitalNo(String inhospitalNo);
    
    /**
     * 创建患者（生成患者ID）
     */
    PatientInfo create(PatientInfo patient, Integer operatorId);
    
    /**
     * 更新患者信息
     */
    boolean update(PatientInfo patient);
    
    /**
     * 删除患者
     */
    boolean delete(String patientId);
    
    /**
     * 检查身份证号是否存在
     */
    boolean existsByIdCard(String idCard, String excludeId);
    
    /**
     * 生成患者ID
     */
    String generatePatientId();
}
