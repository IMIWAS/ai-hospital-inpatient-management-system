# AI+医院住院患者信息管理系统（HIMS）

## 项目简介

这是一个基于 Spring Boot + MySQL 的医院住院患者信息管理系统，集成了豆包AI的OCR识别和风险预警功能，实现住院患者信息的智能化管理。

## 技术栈

### 后端
- **框架**: Spring Boot 2.7.12
- **数据库**: MySQL 8.0
- **ORM**: MyBatis
- **AI服务**: 豆包AI (Volcengine Ark Runtime)
- **工具库**: Hutool, FastJSON, OkHttp

### 前端 (hims-frontend)
- **框架**: Vue 3 + JavaScript
- **UI组件**: Element Plus
- **构建工具**: Vite
- **HTTP库**: Axios
- **状态管理**: Pinia (可选)

## 核心功能模块

### 1. 用户权限管理
- 角色区分：管理员、医生、护士、行政人员
- 用户CRUD、密码管理

### 2. 患者信息管理
- 患者档案创建、编辑、删除
- 身份证OCR智能识别（豆包AI）
- 患者搜索、筛选

### 3. 住院管理
- 患者入院登记
- 床位智能分配
- 患者转科、请假
- 患者出院办理
- 住院记录查询

### 4. 床位管理
- 床位CRUD
- 床位状态管理（空床/占用/维修/预留）
- 床位使用统计

### 5. 生命体征监测与AI风险预警
- 体温、血压、心率、血氧等体征记录
- AI自动风险评估（低危/中危/高危）
- 高危患者实时预警
- 风险阈值可配置

### 6. 病程记录
- 病程记录CRUD
- 记录类型：日常病程、查房记录、抢救记录、手术记录、出院记录
- AI生成病程摘要

### 7. 用药医嘱管理
- 医嘱创建、编辑
- 医嘱执行、暂停
- 执行状态跟踪
- 药品使用统计

### 8. 出院随访管理
- 随访计划创建
- 随访记录管理
- 随访状态跟踪（待随访/已完成/失联/取消）
- AI生成随访建议
- AI自动创建随访计划

### 9. 数据大屏
- 在院患者统计
- 床位使用率
- 科室住院人次
- 费用统计
- 高危患者预警
- 随访统计

## 项目结构

```
hims-backend/
├── src/main/java/com/hims/
│   ├── config/              # 配置类
│   │   ├── CorsConfig.java
│   │   ├── RestTemplateConfig.java
│   │   ├── DoubaoConfig.java
│   │   ├── RiskConfig.java
│   │   ├── UploadConfig.java
│   │   └── WebMvcConfig.java
│   ├── controller/          # 控制器层
│   │   ├── SysUserController.java
│   │   ├── PatientController.java
│   │   ├── PatientInhospitalController.java
│   │   ├── BedInfoController.java
│   │   ├── PatientVitalController.java
│   │   ├── PatientCourseController.java
│   │   ├── PatientDrugController.java
│   │   ├── PatientFollowController.java
│   │   ├── AiOcrController.java
│   │   ├── FileController.java
│   │   └── DashboardController.java
│   ├── domain/             # 实体类
│   │   ├── SysUser.java
│   │   ├── PatientInfo.java
│   │   ├── PatientInhospital.java
│   │   ├── BedInfo.java
│   │   ├── PatientVital.java
│   │   ├── PatientCourse.java
│   │   ├── PatientDrug.java
│   │   ├── PatientFollow.java
│   │   ├── SysLog.java
│   │   └── AiOcrRecord.java
│   ├── exception/          # 异常处理
│   │   ├── GlobalExceptionHandler.java
│   │   └── BusinessException.java
│   ├── interceptor/        # 拦截器
│   │   └── LoginInterceptor.java
│   ├── mapper/             # 数据访问层
│   │   └── (10个Mapper接口)
│   ├── query/              # 查询对象
│   │   └── (8个Query类)
│   ├── service/            # 服务接口
│   │   ├── SysUserService.java
│   │   ├── PatientInfoService.java
│   │   ├── PatientInhospitalService.java
│   │   ├── BedInfoService.java
│   │   ├── PatientVitalService.java
│   │   ├── PatientCourseService.java
│   │   ├── PatientDrugService.java
│   │   ├── PatientFollowService.java
│   │   └── AiOcrService.java
│   ├── service/impl/       # 服务实现
│   │   └── (9个ServiceImpl)
│   ├── utils/              # 工具类
│   │   └── ResultVo.java
│   └── websocket/          # WebSocket配置
│       ├── WebSocketConfig.java
│       └── WebSocketServerUtil.java
├── src/main/resources/
│   ├── mapper/             # MyBatis XML映射文件
│   │   └── (10个Mapper XML)
│   ├── sql/
│   │   └── hims_database.sql
│   └── application.yml
├── pom.xml
└── README.md
```

## 数据库表结构

系统设计了10张核心业务表：

1. **sys_user** - 系统用户表
2. **patient_info** - 患者基础信息表
3. **patient_inhospital** - 患者住院记录表
4. **bed_info** - 床位信息表
5. **patient_vital** - 患者生命体征表
6. **patient_course** - 病程记录表
7. **patient_drug** - 用药医嘱表
8. **patient_follow** - 出院随访表
9. **sys_log** - 系统日志表
10. **ai_ocr_record** - AI识别记录表

## API接口

所有API前缀：`/hims/api/`

### 用户管理
- `POST /api/user/login` - 用户登录
- `GET /api/user/info` - 获取用户信息
- `GET /api/user/list` - 用户列表
- `POST /api/user/create` - 创建用户
- `POST /api/user/update` - 更新用户
- `POST /api/user/password` - 修改密码

### 患者管理
- `GET /api/patient/list` - 患者列表
- `GET /api/patient/info` - 患者详情
- `POST /api/patient/create` - 创建患者
- `POST /api/patient/update` - 更新患者

### 住院管理
- `POST /api/inhospital/admit` - 患者入院
- `POST /api/inhospital/discharge` - 患者出院
- `POST /api/inhospital/transfer` - 患者转科
- `GET /api/inhospital/list` - 住院记录列表

### 床位管理
- `GET /api/bed/list` - 床位列表
- `GET /api/bed/empty` - 空床查询
- `POST /api/bed/allocate` - 分配床位
- `GET /api/bed/stats` - 床位统计

### 生命体征
- `POST /api/vital/record` - 记录体征（含AI风险评估）
- `GET /api/vital/latest` - 最新体征
- `GET /api/vital/high-risk` - 高危患者
- `POST /api/vital/risk-assessment` - AI风险评估

### AI OCR识别
- `POST /api/ai/ocr/id-card` - 身份证识别
- `POST /api/ai/ocr/medical` - 医疗单据识别
- `GET /api/ai/ocr/records` - 识别记录

### 随访管理
- `GET /api/follow/pending` - 待随访列表
- `POST /api/follow/create` - 创建随访
- `POST /api/follow/complete` - 完成随访
- `POST /api/follow/suggest` - AI随访建议

### 数据统计
- `GET /api/dashboard/home` - 首页统计
- `GET /api/dashboard/bed` - 床位统计
- `GET /api/dashboard/dept` - 科室统计
- `GET /api/dashboard/follow` - 随访统计

## 配置说明

### application.yml 主要配置

```yaml
server:
  port: 9000
  servlet:
    context-path: /hims

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hims
    username: root
    password: your_password

hims:
  doubao:
    api-key: your_api_key
    base-url: https://ark.cn-beijing.volces.com/api/v3
    model-id: ep-xxx
  
  upload:
    path: /path/to/upload
    url-prefix: /hims/file/view/
  
  risk:
    temp-high: 39.0
    temp-low: 35.0
    bp-high-high: 160
    heart-rate-high: 120
    blood-oxygen-low: 90.0
```

## 快速开始

### 1. 数据库初始化

```bash
mysql -u root -p
source src/main/resources/sql/hims_database.sql
```

### 2. 修改配置文件

编辑 `application.yml`，配置：
- 数据库连接信息
- 豆包AI API密钥
- 文件上传路径

### 3. 编译运行

```bash
mvn clean package
java -jar target/hims-backend.jar
```

### 4. 访问接口

- 基础URL: `http://localhost:9000/hims`
- API文档: 暂无（可使用Postman测试）

## AI功能说明

### OCR智能识别

系统集成豆包AI的OCR能力，支持识别：
- 身份证
- 就诊卡
- 医保卡
- 检查报告
- 病历首页

### AI风险预警

系统根据以下指标自动评估患者风险等级：

| 指标 | 正常范围 | 异常扣分 |
|------|---------|---------|
| 体温 | 35.0-39.0℃ | 超范围+30分 |
| 收缩压 | 90-160mmHg | 超范围+35分 |
| 舒张压 | 60-100mmHg | 超范围+30分 |
| 心率 | 50-120次/分 | 超范围+25分 |
| 血氧 | ≥90% | 低于90% +40分 |

**风险等级**：
- 低危：0-29分
- 中危：30-59分
- 高危：60+分

## 扩展建议

### 前端开发
参考 `hims-frontend` 项目（Vue 3 + JavaScript）进行前端开发

### 功能扩展
1. 对接医院HIS/LIS系统
2. 增加WebSocket实时推送（高危患者预警）
3. 增加数据导出功能（Excel/PDF）
4. 增加消息通知（短信/邮件提醒）
5. 增加移动端支持

## 项目特点

✅ **完整的业务闭环**：入院-在院-出院-随访全流程覆盖  
✅ **AI智能化**：OCR识别建档、AI风险预警、智能随访建议  
✅ **可配置**：风险阈值、通知规则均可配置  
✅ **模块化设计**：代码结构清晰，易于维护和扩展  
✅ **实战导向**：直接可落地，适合毕业设计/项目实践  

## 许可证

本项目仅供学习和研究使用。

## 作者

HIMS Development Team

## 版本

v1.0.0 - 2026-09-19
