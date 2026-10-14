package com.hims.query;

import lombok.Data;

/**
 * 床位查询条件
 */
@Data
public class BedInfoQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 关键字搜索（床位号、病房号） */
    private String keyword;
    
    /** 科室 */
    private String department;
    
    /** 楼层 */
    private String floor;
    
    /** 床位类型：普通/ICU/单人间/双人间 */
    private String bedType;
    
    /** 床位状态：0空床 1占用 2维修 3预留 */
    private Integer bedStatus;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
