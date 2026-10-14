package com.hims.query;

import lombok.Data;
import java.util.Date;

/**
 * 病程记录查询条件
 */
@Data
public class PatientCourseQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 患者ID */
    private String patientId;
    
    /** 住院记录ID */
    private Integer inhospitalId;
    
    /** 记录医生ID */
    private Integer doctorId;
    
    /** 记录类型：日常病程/查房记录/抢救记录/手术记录/出院记录 */
    private String recordType;
    
    /** 是否AI生成：0否 1是 */
    private Integer isAiGenerated;
    
    /** 记录时间开始 */
    private Date createTimeStart;
    
    /** 记录时间结束 */
    private Date createTimeEnd;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
