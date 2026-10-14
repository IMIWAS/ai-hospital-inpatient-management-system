package com.hims.query;

import lombok.Data;
import java.util.Date;

/**
 * 出院随访查询条件
 */
@Data
public class PatientFollowQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 患者ID */
    private String patientId;
    
    /** 出院住院记录ID */
    private Integer inhospitalId;
    
    /** 随访类型：常规随访/术后随访/慢病随访/康复随访 */
    private String followType;
    
    /** 随访方式：电话随访/门诊随访/上门随访/视频随访 */
    private String followWay;
    
    /** 随访状态：0待随访 1已完成 2失联 3取消 */
    private Integer followStatus;
    
    /** 随访人ID */
    private Integer followUserId;
    
    /** 计划随访时间开始 */
    private Date followTimeStart;
    
    /** 计划随访时间结束 */
    private Date followTimeEnd;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
