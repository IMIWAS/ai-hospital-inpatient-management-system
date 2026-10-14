package com.hims.mapper;

import com.hims.domain.PatientCourse;
import com.hims.query.PatientCourseQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 病程记录Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface PatientCourseMapper {

    /**
     * 分页查询列表
     */
    List<PatientCourse> selectList(@Param("q") PatientCourseQuery query);

    /**
     * 查询总数
     */
    Long countList(@Param("q") PatientCourseQuery query);

    /**
     * 根据ID查询
     */
    PatientCourse selectById(@Param("courseId") Integer courseId);

    /**
     * 根据患者ID查询病程记录
     */
    List<PatientCourse> selectByPatientId(@Param("patientId") String patientId);

    /**
     * 根据住院ID查询病程记录
     */
    List<PatientCourse> selectByInhospitalId(@Param("inhospitalId") Integer inhospitalId);

    /**
     * 插入病程记录
     */
    int insert(PatientCourse course);

    /**
     * 更新病程记录
     */
    int update(PatientCourse course);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("courseId") Integer courseId);
}
