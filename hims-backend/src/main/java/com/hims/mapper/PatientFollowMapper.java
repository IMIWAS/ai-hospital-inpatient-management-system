package com.hims.mapper;

import com.hims.domain.PatientFollow;
import com.hims.query.PatientFollowQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 出院随访Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface PatientFollowMapper {

    /**
     * 分页查询列表
     */
    List<PatientFollow> selectList(@Param("q") PatientFollowQuery query);

    /**
     * 查询总数
     */
    Long countList(@Param("q") PatientFollowQuery query);

    /**
     * 根据ID查询
     */
    PatientFollow selectById(@Param("followId") Integer followId);

    /**
     * 根据患者ID查询随访记录
     */
    List<PatientFollow> selectByPatientId(@Param("patientId") String patientId);

    /**
     * 根据住院ID查询随访记录
     */
    List<PatientFollow> selectByInhospitalId(@Param("inhospitalId") Integer inhospitalId);

    /**
     * 查询待随访列表
     */
    List<PatientFollow> selectPendingFollows();

    /**
     * 插入随访记录
     */
    int insert(PatientFollow follow);

    /**
     * 更新随访记录
     */
    int update(PatientFollow follow);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("followId") Integer followId);

    /**
     * 查询随访统计
     */
    Map<String, Object> selectFollowStats();

    /**
     * 查询随访失联列表
     */
    List<PatientFollow> selectLostFollows();
}
