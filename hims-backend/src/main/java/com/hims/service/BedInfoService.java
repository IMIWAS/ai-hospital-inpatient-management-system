package com.hims.service;

import com.hims.domain.BedInfo;
import com.hims.query.BedInfoQuery;
import java.util.Map;

/**
 * 床位管理服务接口
 */
public interface BedInfoService {
    
    /**
     * 分页查询床位列表
     */
    Map<String, Object> getPageList(BedInfoQuery query);
    
    /**
     * 根据ID查询
     */
    BedInfo getById(Integer bedId);
    
    /**
     * 查询空床列表
     */
    Map<String, Object> getEmptyBeds(String department, String bedType);
    
    /**
     * 智能分配床位
     */
    BedInfo allocateBed(String department, String patientId, Integer inhospitalId, String bedType);
    
    /**
     * 释放床位
     */
    boolean releaseBed(Integer bedId);
    
    /**
     * 创建床位
     */
    boolean create(BedInfo bed);
    
    /**
     * 更新床位
     */
    boolean update(BedInfo bed);
    
    /**
     * 删除床位
     */
    boolean delete(Integer bedId);
    
    /**
     * 获取床位统计
     */
    Map<String, Object> getBedStats();
}
