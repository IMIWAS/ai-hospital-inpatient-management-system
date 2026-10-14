package com.hims.mapper;

import com.hims.domain.PatientVital;
import com.hims.query.PatientVitalQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 患者生命体征Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface PatientVitalMapper {

    /**
     * 分页查询列表
     */
    List<PatientVital> selectList(@Param("q") PatientVitalQuery query);

    /**
     * 查询总数
     */
    Long countList(@Param("q") PatientVitalQuery query);

    /**
     * 根据ID查询
     */
    PatientVital selectById(@Param("vitalId") Integer vitalId);

    /**
     * 根据患者ID查询最新体征
     */
    PatientVital selectLatestByPatientId(@Param("patientId") String patientId);

    /**
     * 根据患者ID查询体征历史
     */
    List<PatientVital> selectHistoryByPatientId(@Param("patientId") String patientId,
                                                 @Param("limit") Integer limit);

    /**
     * 插入体征记录
     */
    int insert(PatientVital vital);

    /**
     * 更新体征记录
     */
    int update(PatientVital vital);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("vitalId") Integer vitalId);

    /**
     * 查询高危患者列表
     */
    List<Map<String, Object>> selectHighRiskPatients();

    /**
     * 查询体征异常统计
     */
    List<Map<String, Object>> selectAbnormalStats(@Param("startDate") String startDate,
                                                   @Param("endDate") String endDate);
}
