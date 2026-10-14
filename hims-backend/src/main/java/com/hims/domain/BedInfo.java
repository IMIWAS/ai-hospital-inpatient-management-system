package com.hims.domain;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 床位信息实体类
 * 对应表：bed_info
 * 用途：AI智能床位调度核心数据表
 */
@Data
public class BedInfo {
    
    /** 床位ID（主键） */
    private Integer bedId;
    
    /** 床位号 */
    private String bedNo;
    
    /** 病房号 */
    private String roomNo;
    
    /** 所属科室 */
    private String department;
    
    /** 楼层 */
    private String floor;
    
    /** 床位类型：普通/ICU/单人间/双人间 */
    private String bedType;
    
    /** 床位状态：0空床 1占用 2维修 3预留 */
    private Integer bedStatus;
    
    /** 当前入住患者ID（空则无） */
    private String patientId;
    
    /** 当前住院记录ID */
    private Integer inhospitalId;
    
    /** 床位日费 */
    private BigDecimal bedPrice;
    
    /** 备注 */
    private String remark;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 状态名称（非数据库字段） */
    private String statusName;
    
    /** 患者姓名（非数据库字段） */
    private String patientName;
}
