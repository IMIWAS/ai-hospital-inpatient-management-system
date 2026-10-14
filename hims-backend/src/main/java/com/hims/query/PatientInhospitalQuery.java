package com.hims.query;

import lombok.Data;
import java.util.Date;

/**
 * 住院记录查询条件
 */
@Data
public class PatientInhospitalQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 关键字搜索（住院号、患者姓名、诊断） */
    private String keyword;
    
    /** 科室 */
    private String department;
    
    /** 住院状态：1在院 2已出院 3转科 4请假 */
    private Integer inhospitalStatus;
    
    /** 结算状态：0未结算 1已结算 */
    private Integer settlementStatus;
    
    /** 入院时间开始 */
    private Date inhospitalTimeStart;
    
    /** 入院时间结束 */
    private Date inhospitalTimeEnd;
    
    /** 出院时间开始 */
    private Date outhospitalTimeStart;
    
    /** 出院时间结束 */
    private Date outhospitalTimeEnd;
    
    /** 主管医生ID */
    private Integer doctorId;
    
    /** 责任护士ID */
    private Integer nurseId;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
