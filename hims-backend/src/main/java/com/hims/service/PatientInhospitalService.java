package com.hims.service;

import com.hims.domain.PatientInhospital;
import com.hims.query.PatientInhospitalQuery;
import java.util.Map;

/**
 * 住院记录服务接口
 */
public interface PatientInhospitalService {
    
    /**
     * 分页查询住院记录
     */
    Map<String, Object> getPageList(PatientInhospitalQuery query);
    
    /**
     * 根据ID查询
     */
    PatientInhospital getById(Integer inhospitalId);
    
    /**
     * 根据住院号查询
     */
    PatientInhospital getByInhospitalNo(String inhospitalNo);
    
    /**
     * 根据患者ID查询最新住院记录
     */
    PatientInhospital getLatestByPatientId(String patientId);
    
    /**
     * 患者入院
     */
    PatientInhospital admit(PatientInhospital inhospital);
    
    /**
     * 患者转科
     */
    boolean transfer(PatientInhospital inhospital, String newDepartment, Integer newDoctorId);
    
    /**
     * 患者出院
     */
    boolean discharge(Integer inhospitalId, Integer outhospitalDays, String remark);
    
    /**
     * 患者请假
     */
    boolean leave(Integer inhospitalId, String remark);
    
    /**
     * 更新住院记录
     */
    boolean update(PatientInhospital inhospital);
    
    /**
     * 结算
     */
    boolean settle(Integer inhospitalId);
    
    /**
     * 删除住院记录
     */
    boolean delete(Integer inhospitalId);
    
    /**
     * 获取在院患者统计
     */
    Map<String, Object> getInhospitalStats(String department);
    
    /**
     * 获取床位使用统计
     */
    Map<String, Object> getBedStats();
    
    /**
     * 获取科室统计
     */
    Map<String, Object> getDeptStats();
    
    /**
     * 获取费用统计
     */
    Map<String, Object> getCostStats();
    
    /**
     * 生成住院号
     */
    String generateInhospitalNo();
}
