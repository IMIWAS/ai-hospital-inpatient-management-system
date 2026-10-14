package com.hims.query;

import lombok.Data;
import java.util.Date;

/**
 * 患者信息查询条件
 */
@Data
public class PatientInfoQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 关键字搜索（姓名、患者ID、电话、身份证） */
    private String keyword;
    
    /** 性别 */
    private String gender;
    
    /** 血型 */
    private String bloodType;
    
    /** 所属科室 */
    private String department;
    
    /** 住院状态：1在院 2已出院 */
    private Integer inhospitalStatus;
    
    /** 年龄范围开始 */
    private Integer ageStart;
    
    /** 年龄范围结束 */
    private Integer ageEnd;
    
    /** 建档时间开始 */
    private Date createTimeStart;
    
    /** 建档时间结束 */
    private Date createTimeEnd;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
