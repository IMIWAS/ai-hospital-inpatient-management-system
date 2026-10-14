package com.hims.mapper;

import com.hims.domain.SysLog;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 系统日志Mapper接口
 */
public interface SysLogMapper {
    
    /**
     * 插入日志
     */
    int insert(SysLog log);
    
    /**
     * 分页查询列表
     */
    List<SysLog> selectList(@Param("pageNum") Integer pageNum,
                            @Param("pageSize") Integer pageSize,
                            @Param("userId") Integer userId,
                            @Param("operation") String operation);
    
    /**
     * 查询总数
     */
    Long countList(@Param("userId") Integer userId, @Param("operation") String operation);
    
    /**
     * 根据ID查询
     */
    SysLog selectById(@Param("logId") Long logId);
    
    /**
     * 清空日志
     */
    int deleteAll();
    
    /**
     * 删除指定天数之前的日志
     */
    int deleteBefore(@Param("beforeTime") java.util.Date beforeTime);
}
