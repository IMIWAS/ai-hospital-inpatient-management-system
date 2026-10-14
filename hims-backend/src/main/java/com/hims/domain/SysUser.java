package com.hims.domain;

import lombok.Data;
import java.util.Date;

/**
 * 系统用户实体类
 * 对应表：sys_user
 * 用途：管理医生、护士、管理员、行政人员账号与权限
 */
@Data
public class SysUser {
    
    /** 用户主键ID */
    private Integer userId;
    
    /** 登录账号 */
    private String userName;
    
    /** 登录密码 */
    private String password;
    
    /** 真实姓名 */
    private String realName;
    
    /** 角色类型：1管理员 2医生 3护士 4行政 */
    private Integer roleType;
    
    /** 所属科室 */
    private String department;
    
    /** 职称 */
    private String jobTitle;
    
    /** 联系电话 */
    private String phone;
    
    /** 邮箱 */
    private String email;
    
    /** 性别 */
    private String gender;
    
    /** 身份证号 */
    private String idCard;
    
    /** 头像URL */
    private String avatarUrl;
    
    /** 执业证书号 */
    private String licenseNo;
    
    /** 个人简介 */
    private String introduction;
    
    /** 最后登录时间 */
    private Date lastLoginTime;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 账号状态：0禁用 1正常 */
    private Integer status;
    
    /** 角色名称（用于展示，非数据库字段） */
    private String roleName;
    
    /** 状态名称（用于展示，非数据库字段） */
    private String statusName;
}
