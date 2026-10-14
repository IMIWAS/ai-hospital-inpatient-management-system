-- =====================================================
-- AI+医院住院患者信息管理系统 数据库脚本（完整版）
-- 数据库: MySQL 5.7+ / 8.0+
-- 字符集: utf8mb4 / utf8mb4_unicode_ci
-- 创建时间: 2026-09-19
-- 表数量: 10 张业务表 + 3 张视图
-- =====================================================

-- 如果数据库不存在则创建
CREATE DATABASE IF NOT EXISTS `hims` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `hims`;

-- =====================================================
-- 表1：用户权限表（sys_user）
-- 用途：管理医生、护士、管理员、行政人员账号与权限
-- =====================================================
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `user_id`          INT(11)         NOT NULL AUTO_INCREMENT          COMMENT '用户主键ID',
    `user_name`        VARCHAR(50)     NOT NULL                         COMMENT '登录账号',
    `password`         VARCHAR(255)    NOT NULL                         COMMENT '登录密码(MD5加密)',
    `real_name`        VARCHAR(50)     NOT NULL                         COMMENT '真实姓名',
    `role_type`        TINYINT(1)      NOT NULL DEFAULT 2               COMMENT '角色：1管理员 2医生 3护士 4行政 5医技',
    `department`       VARCHAR(100)    DEFAULT NULL                     COMMENT '所属科室',
    `job_title`        VARCHAR(50)     DEFAULT NULL                     COMMENT '职称：主任医师/副主任医师/主治医师/住院医师/护士长/护士',
    `phone`            VARCHAR(20)     DEFAULT NULL                     COMMENT '联系电话',
    `email`            VARCHAR(100)    DEFAULT NULL                     COMMENT '邮箱',
    `gender`           VARCHAR(10)     DEFAULT NULL                     COMMENT '性别：男/女',
    `id_card`          VARCHAR(18)     DEFAULT NULL                     COMMENT '身份证号',
    `avatar_url`       VARCHAR(500)    DEFAULT NULL                     COMMENT '头像URL',
    `license_no`       VARCHAR(50)     DEFAULT NULL                     COMMENT '执业证号',
    `introduction`     TEXT            DEFAULT NULL                     COMMENT '个人简介',
    `last_login_time`  DATETIME        DEFAULT NULL                     COMMENT '最后登录时间',
    `last_login_ip`    VARCHAR(50)     DEFAULT NULL                     COMMENT '最后登录IP',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '创建时间',
    `update_time`      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `status`           TINYINT(1)      NOT NULL DEFAULT 1               COMMENT '账号状态：0禁用 1正常',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_user_name` (`user_name`),
    KEY `idx_role_type` (`role_type`),
    KEY `idx_department` (`department`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户权限表';

-- 插入默认账号
-- MD5('admin123') = 0192023a7bbd73250516f069df18b500
-- MD5('123456')   = e10adc3949ba59abbe56e057f20f883e
INSERT INTO `sys_user` (`user_name`, `password`, `real_name`, `role_type`, `department`, `job_title`, `phone`, `gender`, `status`) VALUES
('admin',     '0192023a7bbd73250516f069df18b500', '系统管理员', 1, '信息中心', '管理员',    '13800000000', '男', 1),
('doctor001', 'e10adc3949ba59abbe56e057f20f883e', '张医生',    2, '内科',     '主任医师',  '13800000001', '男', 1),
('doctor002', 'e10adc3949ba59abbe56e057f20f883e', '王医生',    2, '外科',     '副主任医师','13800000002', '男', 1),
('nurse001',  'e10adc3949ba59abbe56e057f20f883e', '李护士',    3, '内科',     '护士长',    '13800000003', '女', 1),
('nurse002',  'e10adc3949ba59abbe56e057f20f883e', '陈护士',    3, '外科',     '护士',      '13800000004', '女', 1),
('admin001',  'e10adc3949ba59abbe56e057f20f883e', '赵行政',    4, '医务科',   '科员',      '13800000005', '女', 1);


-- =====================================================
-- 表2：患者基础信息表（patient_info）
-- 用途：AI自动建档，存储患者基础个人信息
-- =====================================================
DROP TABLE IF EXISTS `patient_info`;
CREATE TABLE `patient_info` (
    `patient_id`       VARCHAR(32)     NOT NULL                         COMMENT '患者唯一就诊ID（主键，业务生成）',
    `patient_no`       VARCHAR(32)     DEFAULT NULL                     COMMENT '患者编号（院内就诊卡号）',
    `name`             VARCHAR(50)     NOT NULL                         COMMENT '患者姓名',
    `gender`           VARCHAR(10)     DEFAULT NULL                     COMMENT '性别：男/女',
    `age`              INT(3)          DEFAULT NULL                     COMMENT '年龄（岁）',
    `birth_date`       DATE            DEFAULT NULL                     COMMENT '出生日期',
    `id_card`          VARCHAR(18)     DEFAULT NULL                     COMMENT '身份证号（AI识别录入）',
    `phone`            VARCHAR(20)     DEFAULT NULL                     COMMENT '联系电话',
    `address`          VARCHAR(255)    DEFAULT NULL                     COMMENT '住址',
    `emergency_contact` VARCHAR(100)    DEFAULT NULL                     COMMENT '紧急联系人',
    `emergency_phone`  VARCHAR(20)     DEFAULT NULL                     COMMENT '紧急联系电话',
    `medical_history`  TEXT            DEFAULT NULL                     COMMENT '既往病史',
    `allergy_info`     TEXT            DEFAULT NULL                     COMMENT '过敏信息',
    `blood_type`       VARCHAR(10)     DEFAULT NULL                     COMMENT '血型：A/B/AB/O/A型/B型/AB型/O型/未知',
    `marital_status`   VARCHAR(20)     DEFAULT NULL                     COMMENT '婚姻状况：未婚/已婚/离异/丧偶',
    `occupation`       VARCHAR(50)     DEFAULT NULL                     COMMENT '职业',
    `nationality`      VARCHAR(50)     DEFAULT '中国'                    COMMENT '国籍',
    `insurance_type`   VARCHAR(50)     DEFAULT NULL                     COMMENT '医保类型：城镇职工医保/城镇居民医保/新农合/商业保险/自费',
    `avatar_url`       VARCHAR(500)    DEFAULT NULL                     COMMENT '头像URL',
    `create_user_id`   INT(11)         DEFAULT NULL                     COMMENT '建档人ID',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '建档时间',
    `update_time`      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`patient_id`),
    KEY `idx_name` (`name`),
    KEY `idx_id_card` (`id_card`),
    KEY `idx_phone` (`phone`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者基础信息表';


-- =====================================================
-- 表3：患者住院记录表（patient_inhospital）
-- 用途：记录患者入院、在院、出院全流程住院信息
-- =====================================================
DROP TABLE IF EXISTS `patient_inhospital`;
CREATE TABLE `patient_inhospital` (
    `inhospital_id`    INT(11)         NOT NULL AUTO_INCREMENT          COMMENT '住院记录ID（主键）',
    `patient_id`       VARCHAR(32)     NOT NULL                         COMMENT '关联患者ID',
    `inhospital_no`    VARCHAR(32)     NOT NULL                         COMMENT '住院号（唯一，本次住院唯一标识）',
    `department`       VARCHAR(100)    NOT NULL                         COMMENT '住院科室',
    `bed_id`           INT(11)         DEFAULT NULL                     COMMENT '关联床位ID',
    `diagnosis`        TEXT            DEFAULT NULL                     COMMENT '入院诊断',
    `diagnosis_icd`    VARCHAR(50)     DEFAULT NULL                     COMMENT 'ICD诊断编码',
    `inhospital_time`  DATETIME        NOT NULL                         COMMENT '入院时间',
    `doctor_id`        INT(11)         DEFAULT NULL                     COMMENT '主管医生ID',
    `nurse_id`         INT(11)         DEFAULT NULL                     COMMENT '责任护士ID',
    `outhospital_time` DATETIME        DEFAULT NULL                     COMMENT '出院时间（为空代表在院）',
    `inhospital_days`  INT(5)          DEFAULT 0                        COMMENT '住院天数',
    `total_cost`       DECIMAL(10,2)   DEFAULT 0.00                     COMMENT '总费用',
    `settlement_status` TINYINT(1)     DEFAULT 0                        COMMENT '结算状态：0未结算 1已结算',
    `settlement_time`  DATETIME        DEFAULT NULL                     COMMENT '结算时间',
    `inhospital_status` TINYINT(1)     DEFAULT 1                        COMMENT '住院状态：1在院 2已出院 3转科 4请假',
    `remark`           TEXT            DEFAULT NULL                     COMMENT '备注',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '创建时间',
    `update_time`      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`inhospital_id`),
    UNIQUE KEY `uk_inhospital_no` (`inhospital_no`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_department` (`department`),
    KEY `idx_bed_id` (`bed_id`),
    KEY `idx_inhospital_time` (`inhospital_time`),
    KEY `idx_inhospital_status` (`inhospital_status`),
    KEY `idx_doctor_id` (`doctor_id`),
    KEY `idx_settlement_status` (`settlement_status`),
    CONSTRAINT `fk_inhospital_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient_info` (`patient_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者住院记录表';


-- =====================================================
-- 表4：床位信息表（bed_info）
-- 用途：AI智能床位调度核心数据表
-- =====================================================
DROP TABLE IF EXISTS `bed_info`;
CREATE TABLE `bed_info` (
    `bed_id`           INT(11)         NOT NULL AUTO_INCREMENT          COMMENT '床位ID（主键）',
    `bed_no`           VARCHAR(20)     NOT NULL                         COMMENT '床位号',
    `room_no`          VARCHAR(20)     DEFAULT NULL                     COMMENT '病房号',
    `department`       VARCHAR(100)    NOT NULL                         COMMENT '所属科室',
    `floor`            VARCHAR(20)     DEFAULT NULL                     COMMENT '楼层',
    `bed_type`         VARCHAR(20)     DEFAULT '普通'                   COMMENT '床位类型：普通/ICU/单人间/双人间',
    `bed_status`       TINYINT(1)      DEFAULT 0                        COMMENT '床位状态：0空床 1占用 2维修 3预留',
    `patient_id`       VARCHAR(32)     DEFAULT NULL                     COMMENT '当前入住患者ID',
    `inhospital_id`    INT(11)         DEFAULT NULL                     COMMENT '当前住院记录ID',
    `bed_price`        DECIMAL(8,2)    DEFAULT 0.00                     COMMENT '床位日费（元/天）',
    `remark`           VARCHAR(255)    DEFAULT NULL                     COMMENT '备注',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '创建时间',
    `update_time`      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`bed_id`),
    UNIQUE KEY `uk_bed_no_dept` (`bed_no`, `department`),
    KEY `idx_department` (`department`),
    KEY `idx_bed_status` (`bed_status`),
    KEY `idx_bed_type` (`bed_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='床位信息表';

-- 初始化床位数据
INSERT INTO `bed_info` (`bed_no`, `room_no`, `department`, `floor`, `bed_type`, `bed_status`, `bed_price`) VALUES
-- 内科
('N-A001', '101', '内科', '1楼', '普通',   0, 50.00),
('N-A002', '101', '内科', '1楼', '普通',   0, 50.00),
('N-A003', '101', '内科', '1楼', '普通',   0, 50.00),
('N-A004', '102', '内科', '1楼', '双人间', 0, 80.00),
('N-A005', '102', '内科', '1楼', '双人间', 0, 80.00),
('N-A006', '103', '内科', '1楼', '单人间', 0, 150.00),
-- 外科
('W-B001', '201', '外科', '2楼', '普通',   0, 50.00),
('W-B002', '201', '外科', '2楼', '普通',   0, 50.00),
('W-B003', '201', '外科', '2楼', '普通',   0, 50.00),
('W-B004', '202', '外科', '2楼', '双人间', 0, 80.00),
-- ICU
('ICU-01', '301', 'ICU', '3楼', 'ICU',    0, 300.00),
('ICU-02', '301', 'ICU', '3楼', 'ICU',    0, 300.00),
('ICU-03', '302', 'ICU', '3楼', 'ICU',    0, 300.00),
-- 妇产科
('F-C001', '401', '妇产科', '4楼', '普通', 0, 60.00),
('F-C002', '401', '妇产科', '4楼', '普通', 0, 60.00),
-- 儿科
('P-D001', '501', '儿科', '5楼', '普通',   0, 60.00),
('P-D002', '501', '儿科', '5楼', '普通',   0, 60.00);


-- =====================================================
-- 表5：患者生命体征与风险数据表（patient_vital）
-- 用途：AI风险预警模型核心数据源，用于病情智能评估
-- =====================================================
DROP TABLE IF EXISTS `patient_vital`;
CREATE TABLE `patient_vital` (
    `vital_id`         INT(11)         NOT NULL AUTO_INCREMENT          COMMENT '体征记录ID',
    `patient_id`       VARCHAR(32)     NOT NULL                         COMMENT '患者ID',
    `inhospital_id`    INT(11)         DEFAULT NULL                     COMMENT '住院记录ID',
    `temperature`      FLOAT(4,1)      DEFAULT NULL                     COMMENT '体温（℃）',
    `heart_rate`       INT(3)          DEFAULT NULL                     COMMENT '心率（次/分）',
    `bp_high`          INT(3)          DEFAULT NULL                     COMMENT '收缩压（mmHg）',
    `bp_low`           INT(3)          DEFAULT NULL                     COMMENT '舒张压（mmHg）',
    `blood_oxygen`     FLOAT(4,1)      DEFAULT NULL                     COMMENT '血氧饱和度（%）',
    `breath_rate`      INT(3)          DEFAULT NULL                     COMMENT '呼吸频率（次/分）',
    `weight`           FLOAT(5,1)      DEFAULT NULL                     COMMENT '体重（kg）',
    `height`           FLOAT(5,1)      DEFAULT NULL                     COMMENT '身高（cm）',
    `blood_sugar`      FLOAT(5,1)      DEFAULT NULL                     COMMENT '血糖（mmol/L）',
    `risk_level`       TINYINT(1)      DEFAULT 1                        COMMENT 'AI风险等级：1低危 2中危 3高危',
    `risk_score`       FLOAT(4,2)      DEFAULT 0.00                     COMMENT '风险评分（0~100）',
    `risk_tip`         TEXT            DEFAULT NULL                     COMMENT 'AI预警提示内容',
    `record_user_id`   INT(11)         DEFAULT NULL                     COMMENT '记录人ID',
    `record_time`      DATETIME        NOT NULL                         COMMENT '记录时间',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '创建时间',
    PRIMARY KEY (`vital_id`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_inhospital_id` (`inhospital_id`),
    KEY `idx_record_time` (`record_time`),
    KEY `idx_risk_level` (`risk_level`),
    KEY `idx_record_user_id` (`record_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者生命体征与风险数据表';


-- =====================================================
-- 表6：病程记录数据表（patient_course）
-- 用途：存储AI语音/文字自动生成的病程记录、医生手动修改内容
-- =====================================================
DROP TABLE IF EXISTS `patient_course`;
CREATE TABLE `patient_course` (
    `course_id`        INT(11)         NOT NULL AUTO_INCREMENT          COMMENT '病程记录ID',
    `patient_id`       VARCHAR(32)     NOT NULL                         COMMENT '患者ID',
    `inhospital_id`    INT(11)         DEFAULT NULL                     COMMENT '住院记录ID',
    `doctor_id`        INT(11)         NOT NULL                         COMMENT '记录医生ID',
    `course_content`   TEXT            NOT NULL                         COMMENT '病程内容',
    `course_summary`   TEXT            DEFAULT NULL                     COMMENT '病程摘要',
    `record_type`      VARCHAR(50)     NOT NULL                         COMMENT '记录类型：日常病程/查房记录/抢救记录/手术记录/出院记录',
    `is_ai_generated`  TINYINT(1)      DEFAULT 0                        COMMENT '是否AI生成：0否 1是',
    `ai_confidence`    FLOAT(4,2)      DEFAULT NULL                     COMMENT 'AI生成置信度（0~1）',
    `attachments`      TEXT            DEFAULT NULL                     COMMENT '附件（JSON格式存储文件路径）',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '记录时间',
    `update_time`      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`course_id`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_inhospital_id` (`inhospital_id`),
    KEY `idx_doctor_id` (`doctor_id`),
    KEY `idx_record_type` (`record_type`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='病程记录数据表';


-- =====================================================
-- 表7：用药医嘱表（patient_drug）
-- 用途：管理患者住院用药、医嘱执行记录
-- =====================================================
DROP TABLE IF EXISTS `patient_drug`;
CREATE TABLE `patient_drug` (
    `drug_id`          INT(11)         NOT NULL AUTO_INCREMENT          COMMENT '医嘱ID',
    `patient_id`       VARCHAR(32)     NOT NULL                         COMMENT '患者ID',
    `inhospital_id`    INT(11)         DEFAULT NULL                     COMMENT '住院记录ID',
    `prescription_no`  VARCHAR(50)     DEFAULT NULL                     COMMENT '处方号',
    `drug_name`        VARCHAR(100)    NOT NULL                         COMMENT '药品名称',
    `drug_spec`        VARCHAR(100)    DEFAULT NULL                     COMMENT '药品规格',
    `drug_dose`        VARCHAR(50)     NOT NULL                         COMMENT '用药剂量',
    `drug_unit`        VARCHAR(20)     DEFAULT NULL                     COMMENT '剂量单位',
    `drug_frequency`   VARCHAR(50)     NOT NULL                         COMMENT '用药频次：每日1次/每日2次/每日3次/睡前服用/必要时服用',
    `drug_route`       VARCHAR(50)     DEFAULT '口服'                   COMMENT '给药途径：口服/静脉注射/肌肉注射/皮下注射/外用',
    `doctor_advice`    TEXT            DEFAULT NULL                     COMMENT '医嘱说明',
    `start_time`       DATETIME        NOT NULL                         COMMENT '开始时间',
    `end_time`         DATETIME        DEFAULT NULL                     COMMENT '结束时间',
    `days`             INT(3)          DEFAULT NULL                     COMMENT '用药天数',
    `total_quantity`   VARCHAR(50)     DEFAULT NULL                     COMMENT '总量',
    `price`            DECIMAL(8,2)    DEFAULT 0.00                     COMMENT '单价',
    `total_price`      DECIMAL(10,2)   DEFAULT 0.00                     COMMENT '总价',
    `execute_status`   TINYINT(1)      DEFAULT 0                        COMMENT '执行状态：0未执行 1已执行 2暂停 3完成',
    `execute_time`     DATETIME        DEFAULT NULL                     COMMENT '执行时间',
    `execute_user_id`  INT(11)         DEFAULT NULL                     COMMENT '执行人ID',
    `doctor_id`        INT(11)         NOT NULL                         COMMENT '开嘱医生ID',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '创建时间',
    `update_time`      DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`drug_id`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_inhospital_id` (`inhospital_id`),
    KEY `idx_execute_status` (`execute_status`),
    KEY `idx_start_time` (`start_time`),
    KEY `idx_doctor_id` (`doctor_id`),
    KEY `idx_prescription_no` (`prescription_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用药医嘱表';


-- =====================================================
-- 表8：出院随访表（patient_follow）
-- 用途：出院患者AI智能随访、康复跟踪管理
-- =====================================================
DROP TABLE IF EXISTS `patient_follow`;
CREATE TABLE `patient_follow` (
    `follow_id`            INT(11)         NOT NULL AUTO_INCREMENT          COMMENT '随访记录ID',
    `patient_id`           VARCHAR(32)     NOT NULL                         COMMENT '患者ID',
    `inhospital_id`        INT(11)         DEFAULT NULL                     COMMENT '出院住院记录ID',
    `follow_type`          VARCHAR(50)     DEFAULT '常规随访'               COMMENT '随访类型：常规随访/术后随访/慢病随访/康复随访',
    `follow_time`          DATETIME        NOT NULL                         COMMENT '计划随访时间',
    `follow_actual_time`   DATETIME        DEFAULT NULL                     COMMENT '实际随访时间',
    `follow_way`           VARCHAR(50)     DEFAULT '电话随访'               COMMENT '随访方式：电话随访/门诊随访/上门随访/视频随访',
    `follow_content`       TEXT            DEFAULT NULL                     COMMENT '随访内容、患者康复情况',
    `follow_result`        TEXT            DEFAULT NULL                     COMMENT '随访结果',
    `symptoms`             TEXT            DEFAULT NULL                     COMMENT '症状描述',
    `medication_compliance` VARCHAR(20)    DEFAULT NULL                     COMMENT '用药依从性：良好/一般/差',
    `recovery_status`      VARCHAR(50)     DEFAULT NULL                     COMMENT '恢复状态：好转/稳定/恶化',
    `next_follow_time`     DATETIME        DEFAULT NULL                     COMMENT '下次随访时间',
    `ai_suggest`           TEXT            DEFAULT NULL                     COMMENT 'AI康复指导建议',
    `follow_user_id`       INT(11)         DEFAULT NULL                     COMMENT '随访人ID',
    `follow_status`        TINYINT(1)      DEFAULT 0                        COMMENT '随访状态：0待随访 1已完成 2失联 3取消',
    `create_time`          DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '创建时间',
    `update_time`          DATETIME        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`follow_id`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_inhospital_id` (`inhospital_id`),
    KEY `idx_follow_time` (`follow_time`),
    KEY `idx_follow_status` (`follow_status`),
    KEY `idx_follow_user_id` (`follow_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出院随访表';


-- =====================================================
-- 表9：系统操作日志表（sys_log）
-- 用途：记录系统操作日志（用于审计）
-- =====================================================
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
    `log_id`           BIGINT(20)      NOT NULL AUTO_INCREMENT          COMMENT '日志ID',
    `user_id`          INT(11)         DEFAULT NULL                     COMMENT '操作用户ID',
    `user_name`        VARCHAR(50)     DEFAULT NULL                     COMMENT '操作用户名',
    `operation`        VARCHAR(100)    DEFAULT NULL                     COMMENT '操作描述',
    `method`           VARCHAR(200)    DEFAULT NULL                     COMMENT '请求方法（类.方法）',
    `params`           TEXT            DEFAULT NULL                     COMMENT '请求参数',
    `ip`               VARCHAR(50)     DEFAULT NULL                     COMMENT 'IP地址',
    `result`           TEXT            DEFAULT NULL                     COMMENT '返回结果',
    `status`           TINYINT(1)      DEFAULT 1                        COMMENT '状态：0异常 1正常',
    `error_msg`        TEXT            DEFAULT NULL                     COMMENT '错误信息',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '操作时间',
    PRIMARY KEY (`log_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_status` (`status`),
    KEY `idx_operation` (`operation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志表';


-- =====================================================
-- 表10：AI OCR 识别记录表（ai_ocr_record）
-- 用途：存储 AI OCR 识别的历史记录（身份证/就诊卡/检查报告等）
-- =====================================================
DROP TABLE IF EXISTS `ai_ocr_record`;
CREATE TABLE `ai_ocr_record` (
    `ocr_id`           INT(11)         NOT NULL AUTO_INCREMENT          COMMENT 'OCR记录ID',
    `patient_id`       VARCHAR(32)     DEFAULT NULL                     COMMENT '关联患者ID（识别后可能创建或关联）',
    `ocr_type`         VARCHAR(50)     NOT NULL                         COMMENT '识别类型：身份证/就诊卡/医保卡/检查报告/病历首页',
    `image_url`        VARCHAR(500)    DEFAULT NULL                     COMMENT '图片存储路径',
    `image_base64`     MEDIUMTEXT      DEFAULT NULL                     COMMENT '图片Base64（可选存储）',
    `ocr_result`       TEXT            NOT NULL                         COMMENT 'OCR识别结果（JSON格式）',
    `ocr_confidence`   FLOAT(4,2)      DEFAULT NULL                     COMMENT '识别置信度（0~1）',
    `extracted_fields` TEXT            DEFAULT NULL                     COMMENT '提取的关键字段（JSON格式）',
    `status`           TINYINT(1)      DEFAULT 0                        COMMENT '处理状态：0待处理 1已识别 2已建档 3识别失败',
    `error_msg`        TEXT            DEFAULT NULL                     COMMENT '错误信息',
    `operator_id`      INT(11)         DEFAULT NULL                     COMMENT '操作人ID',
    `create_time`      DATETIME        DEFAULT CURRENT_TIMESTAMP        COMMENT '识别时间',
    PRIMARY KEY (`ocr_id`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_ocr_type` (`ocr_type`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_status` (`status`),
    KEY `idx_operator_id` (`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI OCR 识别记录表';


-- =====================================================
-- 视图1：住院患者综合视图（v_inhospital_patients）
-- 用途：用于数据大屏、住院患者综合查询
-- =====================================================
DROP VIEW IF EXISTS `v_inhospital_patients`;
CREATE OR REPLACE VIEW `v_inhospital_patients` AS
SELECT
    pi.patient_id,
    pi.name AS patient_name,
    pi.gender,
    pi.age,
    pi.phone,
    pi.id_card,
    pi.blood_type,
    phi.inhospital_id,
    phi.inhospital_no,
    phi.department,
    phi.bed_id,
    bi.bed_no,
    bi.room_no,
    phi.diagnosis,
    phi.inhospital_time,
    phi.inhospital_days,
    phi.inhospital_status,
    phi.doctor_id,
    phi.nurse_id,
    doctor.real_name AS doctor_name,
    nurse.real_name AS nurse_name,
    pv.risk_level,
    pv.risk_score,
    pv.risk_tip,
    pv.record_time AS last_vital_time
FROM patient_info pi
LEFT JOIN patient_inhospital phi
    ON pi.patient_id = phi.patient_id AND phi.inhospital_status = 1
LEFT JOIN bed_info bi
    ON phi.bed_id = bi.bed_id
LEFT JOIN sys_user doctor
    ON phi.doctor_id = doctor.user_id
LEFT JOIN sys_user nurse
    ON phi.nurse_id = nurse.user_id
LEFT JOIN (
    SELECT patient_id, inhospital_id, risk_level, risk_score, risk_tip, record_time,
           ROW_NUMBER() OVER(PARTITION BY patient_id ORDER BY record_time DESC) AS rn
    FROM patient_vital
) pv
    ON pi.patient_id = pv.patient_id
    AND (phi.inhospital_id = pv.inhospital_id OR pv.inhospital_id IS NULL)
    AND pv.rn = 1;


-- =====================================================
-- 视图2：床位使用情况视图（v_bed_usage）
-- 用途：床位统计查询（按科室）
-- =====================================================
DROP VIEW IF EXISTS `v_bed_usage`;
CREATE OR REPLACE VIEW `v_bed_usage` AS
SELECT
    department,
    COUNT(*) AS total_count,
    SUM(CASE WHEN bed_status = 0 THEN 1 ELSE 0 END) AS empty_count,
    SUM(CASE WHEN bed_status = 1 THEN 1 ELSE 0 END) AS occupied_count,
    SUM(CASE WHEN bed_status = 2 THEN 1 ELSE 0 END) AS maintenance_count,
    SUM(CASE WHEN bed_status = 3 THEN 1 ELSE 0 END) AS reserved_count,
    ROUND(SUM(CASE WHEN bed_status = 1 THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS occupancy_rate
FROM bed_info
GROUP BY department;


-- =====================================================
-- 视图3：患者最新体征视图（v_patient_latest_vital）
-- 用途：获取每个患者的最新生命体征
-- =====================================================
DROP VIEW IF EXISTS `v_patient_latest_vital`;
CREATE OR REPLACE VIEW `v_patient_latest_vital` AS
SELECT
    pv.vital_id,
    pv.patient_id,
    pv.inhospital_id,
    pv.temperature,
    pv.heart_rate,
    pv.bp_high,
    pv.bp_low,
    pv.blood_oxygen,
    pv.breath_rate,
    pv.risk_level,
    pv.risk_score,
    pv.risk_tip,
    pv.record_user_id,
    pv.record_time,
    pi.name AS patient_name,
    pi.gender,
    pi.age
FROM patient_vital pv
INNER JOIN (
    SELECT patient_id, MAX(record_time) AS max_time
    FROM patient_vital
    GROUP BY patient_id
) latest
    ON pv.patient_id = latest.patient_id AND pv.record_time = latest.max_time
LEFT JOIN patient_info pi
    ON pv.patient_id = pi.patient_id;


-- =====================================================
-- 数据初始化完成提示
-- =====================================================
SELECT '数据库初始化完成！' AS message;
SELECT '======================================' AS separator;
SELECT '默认账号信息：' AS info;
SELECT user_name AS '账号', real_name AS '姓名',
       CASE role_type WHEN 1 THEN '管理员' WHEN 2 THEN '医生' WHEN 3 THEN '护士' WHEN 4 THEN '行政' ELSE '其他' END AS '角色',
       department AS '科室'
FROM sys_user
ORDER BY role_type, user_id;
SELECT '======================================' AS separator;
SELECT '默认密码：' AS info;
SELECT 'admin / admin123 （管理员）' AS '账号 / 密码'
UNION ALL
SELECT 'doctor001~002 / 123456 （医生）'
UNION ALL
SELECT 'nurse001~002 / 123456 （护士）'
UNION ALL
SELECT 'admin001 / 123456 （行政）';
