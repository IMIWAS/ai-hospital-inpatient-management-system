package com.hims.domain;

import lombok.Data;
import java.util.Date;

/**
 * 系统日志实体类
 * 对应表：sys_log
 * 用途：记录系统操作日志
 */
@Data
public class SysLog {
    
    /** 日志ID */
    private Long logId;
    
    /** 操作用户ID */
    private Integer userId;
    
    /** 操作用户名 */
    private String userName;
    
    /** 操作描述 */
    private String operation;
    
    /** 请求方法 */
    private String method;
    
    /** 请求参数 */
    private String params;
    
    /** IP地址 */
    private String ip;
    
    /** 返回结果 */
    private String result;
    
    /** 状态：0异常 1正常 */
    private Integer status;
    
    /** 错误信息 */
    private String errorMsg;
    
    /** 操作时间 */
    private Date createTime;
}
