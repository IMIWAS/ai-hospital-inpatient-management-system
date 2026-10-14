package com.hims.mapper;

import com.hims.domain.PatientInhospital;
import com.hims.query.PatientInhospitalQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 患者住院记录Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface PatientInhospitalMapper {

    /**
     * 分页查询列表
     */
    List<PatientInhospital> selectList(@Param("q") PatientInhospitalQuery query);

    /**
     * 查询总数
     */
    Long countList(@Param("q") PatientInhospitalQuery query);

    /**
     * 根据ID查询
     */
    PatientInhospital selectById(@Param("inhospitalId") Integer inhospitalId);

    /**
     * 根据住院号查询
     */
    PatientInhospital selectByInhospitalNo(@Param("inhospitalNo") String inhospitalNo);

    /**
     * 根据患者ID查询最新住院记录
     */
    PatientInhospital selectLatestByPatientId(@Param("patientId") String patientId);

    /**
     * 根据患者ID查询所有住院记录
     */
    List<PatientInhospital> selectByPatientId(@Param("patientId") String patientId);

    /**
     * 插入住院记录
     */
    int insert(PatientInhospital inhospital);

    /**
     * 更新住院记录
     */
    int update(PatientInhospital inhospital);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("inhospitalId") Integer inhospitalId);

    /**
     * 查询在院患者统计
     */
    List<Map<String, Object>> selectInhospitalStats(@Param("department") String department);

    /**
     * 查询床位使用情况
     */
    List<Map<String, Object>> selectBedUsageStats();

    /**
     * 查询科室住院人次统计
     */
    List<Map<String, Object>> selectDeptStats();

    /**
     * 查询住院费用统计
     */
    Map<String, Object> selectCostStats();
}
