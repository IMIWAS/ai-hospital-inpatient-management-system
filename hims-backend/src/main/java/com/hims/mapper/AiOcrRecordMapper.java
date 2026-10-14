package com.hims.mapper;

import com.hims.domain.AiOcrRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AI OCR识别记录Mapper接口
 *
 * 方法签名保留（学生需照着实现 XML 中的 SQL）
 */
public interface AiOcrRecordMapper {

    /**
     * 插入记录
     */
    int insert(AiOcrRecord record);

    /**
     * 更新记录
     */
    int update(AiOcrRecord record);

    /**
     * 根据ID查询
     */
    AiOcrRecord selectById(@Param("ocrId") Integer ocrId);

    /**
     * 根据患者ID查询识别记录
     */
    List<AiOcrRecord> selectByPatientId(@Param("patientId") String patientId);

    /**
     * 分页查询列表
     */
    List<AiOcrRecord> selectList(@Param("pageNum") Integer pageNum,
                                  @Param("pageSize") Integer pageSize,
                                  @Param("ocrType") String ocrType,
                                  @Param("status") Integer status);

    /**
     * 查询总数
     */
    Long countList(@Param("ocrType") String ocrType, @Param("status") Integer status);

    /**
     * 根据ID删除
     */
    int deleteById(@Param("ocrId") Integer ocrId);
}
