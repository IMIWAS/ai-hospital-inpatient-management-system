package com.hims.mapper;

import com.hims.domain.PatientInfo;
import com.hims.query.PatientInfoQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 患者信息Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface PatientInfoMapper {

    /**
     * 分页查询列表
     */
    List<PatientInfo> selectList(@Param("q") PatientInfoQuery query);

    /**
     * 查询总数
     */
    Long countList(@Param("q") PatientInfoQuery query);

    /**
     * 根据ID查询
     */
    PatientInfo selectById(@Param("patientId") String patientId);

    /**
     * 根据身份证号查询
     */
    PatientInfo selectByIdCard(@Param("idCard") String idCard);

    /**
     * 根据住院号查询
     */
    PatientInfo selectByInhospitalNo(@Param("inhospitalNo") String inhospitalNo);

    /**
     * 插入患者信息
     */
    int insert(PatientInfo patient);

    /**
     * 更新患者信息
     */
    int update(PatientInfo patient);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("patientId") String patientId);

    /**
     * 检查身份证号是否存在
     */
    int countByIdCard(@Param("idCard") String idCard, @Param("excludeId") String excludeId);
}
