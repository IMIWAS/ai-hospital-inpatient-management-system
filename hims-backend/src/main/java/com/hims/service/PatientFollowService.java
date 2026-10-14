package com.hims.service;

import com.hims.domain.PatientFollow;
import com.hims.query.PatientFollowQuery;
import java.util.Map;

/**
 * 出院随访服务接口
 */
public interface PatientFollowService {
    
    /**
     * 分页查询随访记录
     */
    Map<String, Object> getPageList(PatientFollowQuery query);
    
    /**
     * 根据ID查询
     */
    PatientFollow getById(Integer followId);
    
    /**
     * 根据患者ID查询随访记录
     */
    Map<String, Object> getByPatientId(String patientId);
    
    /**
     * 根据住院ID查询随访记录
     */
    Map<String, Object> getByInhospitalId(Integer inhospitalId);
    
    /**
     * 查询待随访列表
     */
    Map<String, Object> getPendingFollows();
    
    /**
     * 创建随访记录
     */
    PatientFollow create(PatientFollow follow);
    
    /**
     * 更新随访记录
     */
    boolean update(PatientFollow follow);
    
    /**
     * 完成随访
     */
    boolean complete(Integer followId, PatientFollow follow);
    
    /**
     * 标记失联
     */
    boolean markLost(Integer followId);
    
    /**
     * 取消随访
     */
    boolean cancel(Integer followId);
    
    /**
     * 删除随访记录
     */
    boolean delete(Integer followId);
    
    /**
     * 获取随访统计
     */
    Map<String, Object> getFollowStats();
    
    /**
     * 获取失联随访列表
     */
    Map<String, Object> getLostFollows();
    
    /**
     * AI生成随访建议
     */
    Map<String, Object> generateFollowSuggest(String patientId, Integer inhospitalId);
    
    /**
     * AI自动创建随访计划
     */
    boolean autoCreateFollowPlan(Integer inhospitalId);
}
