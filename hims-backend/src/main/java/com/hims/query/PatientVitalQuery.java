package com.hims.query;

import lombok.Data;
import java.util.Date;

/**
 * 生命体征查询条件
 */
@Data
public class PatientVitalQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 患者ID */
    private String patientId;
    
    /** 住院记录ID */
    private Integer inhospitalId;
    
    /** 风险等级：1低危 2中危 3高危 */
    private Integer riskLevel;
    
    /** 记录时间开始 */
    private Date recordTimeStart;
    
    /** 记录时间结束 */
    private Date recordTimeEnd;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
