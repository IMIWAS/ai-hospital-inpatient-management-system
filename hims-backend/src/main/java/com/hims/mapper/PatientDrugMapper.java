package com.hims.mapper;

import com.hims.domain.PatientDrug;
import com.hims.query.PatientDrugQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 用药医嘱Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface PatientDrugMapper {

    /**
     * 分页查询列表
     */
    List<PatientDrug> selectList(@Param("q") PatientDrugQuery query);

    /**
     * 查询总数
     */
    Long countList(@Param("q") PatientDrugQuery query);

    /**
     * 根据ID查询
     */
    PatientDrug selectById(@Param("drugId") Integer drugId);

    /**
     * 根据患者ID查询医嘱
     */
    List<PatientDrug> selectByPatientId(@Param("patientId") String patientId);

    /**
     * 根据住院ID查询医嘱
     */
    List<PatientDrug> selectByInhospitalId(@Param("inhospitalId") Integer inhospitalId);

    /**
     * 查询患者待执行医嘱
     */
    List<PatientDrug> selectPendingDrugs(@Param("patientId") String patientId);

    /**
     * 插入医嘱
     */
    int insert(PatientDrug drug);

    /**
     * 更新医嘱
     */
    int update(PatientDrug drug);

    /**
     * 执行医嘱
     */
    int execute(@Param("drugId") Integer drugId, @Param("executeUserId") Integer executeUserId);

    /**
     * 暂停医嘱
     */
    int pause(@Param("drugId") Integer drugId);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("drugId") Integer drugId);

    /**
     * 查询药品使用统计
     */
    List<Map<String, Object>> selectDrugUsageStats(@Param("startDate") String startDate,
                                                    @Param("endDate") String endDate,
                                                    @Param("department") String department);
}
