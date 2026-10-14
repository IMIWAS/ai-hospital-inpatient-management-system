# AI+医院住院患者信息管理系统（HIMS）

一个面向医院住院场景的信息管理系统，在传统住院业务管理的基础上融合 AI 能力，提供患者建档、住院流转、床位管理、生命体征监测与风险预警、病程记录、用药医嘱、出院随访等完整业务闭环，并配套数据大屏与 AI OCR 智能识别建档。

## 项目简介

系统围绕"患者从入院到出院"的全流程展开，主要覆盖以下业务模块：

- **患者管理**：患者建档、AI OCR 识别建档、列表查询、编辑、详情查看
- **住院管理**：入院登记、转科、出院、结算办理
- **床位管理**：床位分配、状态维护、床位使用情况可视化
- **生命体征**：体征录入、AI 风险预警、高危患者监控
- **病程记录**：病程录入、AI 自动摘要
- **用药医嘱**：医嘱开立、执行、暂停
- **出院随访**：随访计划、AI 随访建议、随访完成登记
- **AI 智能**：身份证 / 就诊卡 / 检查报告 OCR 识别，生命体征智能风险评估
- **数据大屏**：关键指标实时可视化展示
- **用户管理**：账号、角色、权限管理

## 技术栈

### 前端

| 类别 | 技术 |
|------|------|
| 核心框架 | Vue 3.5（Composition API + `<script setup>`） |
| 构建工具 | Vite 8 |
| UI 组件库 | Element Plus 2.14 |
| 状态管理 | Pinia 4 |
| 路由 | Vue Router 5 |
| HTTP 请求 | Axios |
| 样式 | SCSS（sass-embedded） |

### 后端

| 类别 | 技术 |
|------|------|
| 核心框架 | Spring Boot 2.7.12（JDK 1.8） |
| 持久层 | MyBatis + JdbcTemplate |
| 分页插件 | PageHelper |
| 数据库 | MySQL 8.0 |
| 连接池 | HikariCP |
| 实时通信 | WebSocket |
| AI 能力 | 火山引擎方舟（豆包）Ark SDK |
| 工具库 | Hutool、FastJSON、EasyPoi、OkHttp |

## 项目结构

```
hims-v2/
├── hims-backend/                    # 后端工程（Spring Boot）
│   ├── src/main/java/com/hims/
│   │   ├── controller/              # 控制层
│   │   ├── service/                 # 业务层接口
│   │   │   └── impl/                # 业务层实现
│   │   ├── domain/                  # 实体类
│   │   ├── config/                  # 配置类
│   │   ├── websocket/               # WebSocket 服务
│   │   └── utils/                   # 工具类
│   ├── src/main/resources/
│   │   ├── application.yml          # 主配置文件
│   │   ├── mapper/                  # MyBatis 映射文件
│   │   └── sql/                     # 建表脚本
│   └── pom.xml
│
├── hims-frontend/                   # 前端工程（Vue3 + Vite）
│   ├── src/
│   │   ├── api/                     # 接口封装
│   │   ├── assets/                  # 静态资源与全局样式
│   │   ├── layouts/                 # 布局组件（含侧边栏/顶栏/面包屑）
│   │   ├── router/                  # 路由配置
│   │   ├── stores/                  # Pinia 状态管理
│   │   ├── utils/                   # 工具类
│   │   └── views/                   # 业务页面
│   ├── .env.development
│   ├── .env.production
│   ├── vite.config.js
│   └── package.json
│
├── db/
│   └── hims_database.sql            # 数据库建表与初始化脚本
│
├── .gitignore
└── README.md
```

## 快速开始

### 一、环境要求

- JDK 1.8
- Maven 3.6+
- MySQL 8.0
- Node.js 22.18+（或 24.12+）
- npm 10+

### 二、数据库准备

1. 在 MySQL 中创建数据库 `hims`（字符集 `utf8mb4`）。
2. 执行建表与初始化脚本：

```bash
mysql -u root -p hims < db/hims_database.sql
```

> 如使用 Navicat / DataGrip，直接打开 `db/hims_database.sql` 并在 `hims` 库中运行即可。

3. 如数据库账号密码与本项目默认值不同，请修改 `hims-backend/src/main/resources/application.yml` 中的 `spring.datasource` 配置（默认 `root` / `123456`）。

### 三、启动后端

```bash
cd hims-backend
mvn spring-boot:run
```

后端启动后监听 **`http://localhost:9001/hims`**（`context-path` 为 `/hims`）。

### 四、启动前端

```bash
cd hims-frontend
npm install
npm run dev
```

前端启动后访问 **`http://localhost:3100`**。开发环境下，Vite 会将 `/hims/api` 代理到后端 `http://localhost:9001`。

### 五、前端打包

```bash
cd hims-frontend
npm run build
```

构建产物输出到 `hims-frontend/dist/`。

## 配置说明

### 后端关键配置（`application.yml`）

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| `server.port` | 后端端口 | `9001` |
| `server.servlet.context-path` | 接口统一前缀 | `/hims` |
| `spring.datasource.url` | 数据库连接 | `jdbc:mysql://localhost:3306/hims` |
| `hims.doubao.api-key` | 豆包（方舟）API Key | 需自行配置 |
| `hims.doubao.model-id` | 推理接入点 ID | 需自行配置 |
| `hims.upload.path` | 上传文件存储目录 | `${user.home}/hims-upload` |
| `hims.risk.*` | 生命体征风险预警阈值 | 见配置文件 |

> AI 能力依赖火山引擎方舟（豆包）接口，如需启用 OCR 与风险分析，请将 `hims.doubao.api-key` 和 `hims.doubao.model-id` 替换为你自己的 Key 与接入点。

### 前端环境变量

| 文件 | 变量 | 说明 |
|------|------|------|
| `.env.development` | `VITE_API_BASE_URL=/hims` | 开发环境接口前缀（经 Vite 代理） |
| `.env.production` | `VITE_API_BASE_URL=/` | 生产环境接口前缀 |

## 功能模块与路由

| 模块 | 前端路由 | 说明 |
|------|----------|------|
| 首页 | `/dashboard` | 数据概览与快捷入口 |
| 数据大屏 | `/big-screen` | 关键指标可视化 |
| 患者列表 | `/patient/list` | 患者信息查询与管理 |
| 入院登记 | `/patient/admit` | 新患者入院建档 |
| 患者详情 | `/patient/detail/:id` | 患者全息信息 |
| 住院管理 | `/inhospital` | 在院患者、转科、出院 |
| 床位管理 | `/bed` | 床位分配与状态 |
| 体征记录 | `/vital/record` | 生命体征录入 |
| 高危预警 | `/vital/high-risk` | 高危患者监控 |
| 病程记录 | `/course` | 病程录入与摘要 |
| 用药医嘱 | `/drug` | 医嘱管理 |
| 出院随访 | `/follow` | 随访计划与执行 |
| OCR 识别建档 | `/ai/ocr` | 证件/报告识别建档 |
| 识别记录 | `/ai/records` | OCR 识别历史 |
| 用户管理 | `/user` | 账号与角色（仅管理员） |
| 个人中心 | `/profile` | 个人信息与改密 |

## 后端接口约定

- 统一前缀：`/hims`
- 统一响应格式：

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

| 模块 | 路径前缀 |
|------|----------|
| 用户 | `/hims/user` |
| 患者 | `/hims/patient` |
| 住院 | `/hims/inhospital` |
| 床位 | `/hims/bed` |
| 体征 | `/hims/vital` |
| 病程 | `/hims/course` |
| 用药 | `/hims/drug` |
| 随访 | `/hims/follow` |
| AI OCR | `/hims/ai/ocr` |
| 大屏 | `/hims/dashboard` |
| 文件 | `/hims/file` |

## 说明

- 本项目为课程综合实践项目，用于演示"AI + 住院信息管理"的业务与技术实现。
- 后端 `hims.doubao.api-key` 为示例配置，正式部署前请替换为自己的密钥，避免泄露。
- 文件上传目录与日志目录默认位于用户主目录下的 `hims-upload` / `hims-logs`。

## License

仅供学习交流使用。
