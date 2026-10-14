package com.hims.query;

import lombok.Data;

/**
 * 系统用户查询条件
 */
@Data
public class SysUserQuery {
    
    /** 页码（从0开始） */
    private Integer pageNum = 1;
    
    /** 每页数量 */
    private Integer pageSize = 10;
    
    /** 关键字搜索（用户名、真实姓名、电话） */
    private String keyword;
    
    /** 角色类型：1管理员 2医生 3护士 4行政 */
    private Integer roleType;
    
    /** 所属科室 */
    private String department;
    
    /** 职称 */
    private String jobTitle;
    
    /** 账号状态：0禁用 1正常 */
    private Integer status;
    
    /** 是否仅查询医护人员（医生和护士） */
    private Boolean onlyStaff;
    
    /** 排序字段 */
    private String orderBy;
    
    /** 排序类型：asc/desc */
    private String orderType;
    
    public Integer getPageNum() {
        return pageNum != null ? (pageNum - 1) * pageSize : 0;
    }
}
