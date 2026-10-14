package com.hims.service;

import com.hims.domain.PatientCourse;
import com.hims.query.PatientCourseQuery;
import java.util.Map;

/**
 * 病程记录服务接口
 */
public interface PatientCourseService {
    
    /**
     * 分页查询病程记录
     */
    Map<String, Object> getPageList(PatientCourseQuery query);
    
    /**
     * 根据ID查询
     */
    PatientCourse getById(Integer courseId);
    
    /**
     * 根据患者ID查询病程记录
     */
    Map<String, Object> getByPatientId(String patientId);
    
    /**
     * 根据住院ID查询病程记录
     */
    Map<String, Object> getByInhospitalId(Integer inhospitalId);
    
    /**
     * 创建病程记录
     */
    PatientCourse create(PatientCourse course);
    
    /**
     * 更新病程记录
     */
    boolean update(PatientCourse course);
    
    /**
     * 删除病程记录
     */
    boolean delete(Integer courseId);
    
    /**
     * AI生成病程记录摘要
     */
    Map<String, Object> generateCourseSummary(String courseContent);
}
