package com.hims.query;

import lombok.Data;
import java.util.Date;

/**
 * 用药医嘱查询条件
 */
@Data
public class PatientDrugQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 患者ID */
    private String patientId;
    
    /** 住院记录ID */
    private Integer inhospitalId;
    
    /** 药品名称 */
    private String drugName;
    
    /** 执行状态：0未执行 1已执行 2暂停 3完成 */
    private Integer executeStatus;
    
    /** 开嘱医生ID */
    private Integer doctorId;
    
    /** 开始时间开始 */
    private Date startTimeStart;
    
    /** 开始时间结束 */
    private Date startTimeEnd;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
