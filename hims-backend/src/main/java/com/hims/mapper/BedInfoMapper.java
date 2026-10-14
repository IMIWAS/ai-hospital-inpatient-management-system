package com.hims.mapper;

import com.hims.domain.BedInfo;
import com.hims.query.BedInfoQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 床位信息Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface BedInfoMapper {

    /**
     * 分页查询列表
     */
    List<BedInfo> selectList(@Param("q") BedInfoQuery query);

    /**
     * 查询总数
     */
    Long countList(@Param("q") BedInfoQuery query);

    /**
     * 根据ID查询
     */
    BedInfo selectById(@Param("bedId") Integer bedId);

    /**
     * 根据床位号和科室查询
     */
    BedInfo selectByBedNoAndDept(@Param("bedNo") String bedNo, @Param("department") String department);

    /**
     * 查询空床列表
     */
    List<BedInfo> selectEmptyBeds(@Param("department") String department, @Param("bedType") String bedType);

    /**
     * 查询某科室床位统计
     */
    Map<String, Object> selectBedStatsByDept(@Param("department") String department);

    /**
     * 查询所有床位统计
     */
    List<Map<String, Object>> selectAllBedStats();

    /**
     * 插入床位
     */
    int insert(BedInfo bed);

    /**
     * 更新床位
     */
    int update(BedInfo bed);

    /**
     * 更新床位状态
     */
    int updateStatus(@Param("bedId") Integer bedId, @Param("bedStatus") Integer bedStatus,
                    @Param("patientId") String patientId, @Param("inhospitalId") Integer inhospitalId);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("bedId") Integer bedId);
}
