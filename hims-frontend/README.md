# HIMS 医院住院患者信息管理系统 - 前端

基于 Vue3 + Element Plus 的医院住院患者信息管理系统前端。

## 项目简介

本项目是 AI+医院住院患者信息管理系统的配套前端，提供完整的住院信息管理功能，包括：

- **患者管理**：患者建档、AI OCR识别、查询、编辑、详情
- **住院管理**：入院登记、转科、出院、结算
- **床位管理**：床位分配、状态管理、可视化展示
- **生命体征**：体征录入、AI 风险预警、实时监控
- **病程记录**：病程录入、AI 自动摘要
- **用药医嘱**：医嘱开立、执行、暂停
- **出院随访**：随访计划、AI 建议、随访完成
- **AI 智能**：OCR 识别（身份证/就诊卡/检查报告）、智能风险评估
- **数据大屏**：实时可视化数据展示
- **用户管理**：账号、角色、权限管理

## 技术栈

- **核心框架**：Vue 3.5+ (Composition API)
- **构建工具**：Vite 8.x
- **UI 组件库**：Element Plus 2.14+
- **状态管理**：Pinia 4.x
- **路由**：Vue Router 5.x
- **HTTP 请求**：Axios 1.x
- **图标**：Element Plus Icons
- **语言**：JavaScript（无 TypeScript）

## 项目结构

```
hims-frontend/
├── public/                          # 公共静态资源
├── src/
│   ├── api/                         # API 接口封装
│   │   ├── user.js                  # 用户管理
│   │   ├── patient.js               # 患者管理
│   │   ├── inhospital.js            # 住院管理
│   │   ├── bed.js                   # 床位管理
│   │   ├── vital.js                 # 生命体征
│   │   ├── ocr.js                   # AI OCR
│   │   ├── course.js                # 病程记录
│   │   ├── drug.js                  # 用药医嘱
│   │   ├── follow.js                # 出院随访
│   │   ├── dashboard.js             # 数据大屏
│   │   ├── file.js                  # 文件上传
│   │   └── index.js                 # 统一导出
│   │
│   ├── assets/                      # 静态资源
│   │   └── main.css                 # 全局样式
│   │
│   ├── layouts/                     # 布局组件
│   │   ├── AppLayout.vue            # 主布局
│   │   └── components/
│   │       ├── Sidebar.vue          # 侧边栏
│   │       ├── Header.vue           # 顶部
│   │       ├── Breadcrumb.vue       # 面包屑
│   │       └── PasswordDialog.vue   # 修改密码
│   │
│   ├── router/                      # 路由配置
│   │   └── index.js
│   │
│   ├── stores/                      # Pinia 状态管理
│   │   ├── user.js                  # 用户状态
│   │   └── app.js                   # 应用状态
│   │
│   ├── utils/                       # 工具类
│   │   ├── request.js               # Axios 封装
│   │   ├── storage.js               # 本地存储
│   │   ├── date.js                  # 日期工具
│   │   ├── dict.js                  # 字典常量
│   │   └── index.js                 # 通用工具
│   │
│   ├── views/                       # 业务页面
│   │   ├── LoginView.vue            # 登录
│   │   ├── DashboardView.vue        # 首页仪表盘
│   │   ├── BigScreenView.vue        # 数据大屏
│   │   ├── ProfileView.vue          # 个人中心
│   │   ├── patient/                 # 患者管理
│   │   │   ├── PatientListView.vue
│   │   │   ├── PatientAdmitView.vue
│   │   │   ├── PatientDetailView.vue
│   │   │   └── components/
│   │   ├── inhospital/              # 住院管理
│   │   │   ├── InhospitalListView.vue
│   │   │   └── components/
│   │   ├── bed/                     # 床位管理
│   │   │   ├── BedManageView.vue
│   │   │   └── components/
│   │   ├── vital/                   # 生命体征
│   │   │   ├── VitalRecordView.vue
│   │   │   ├── HighRiskView.vue
│   │   │   └── components/
│   │   ├── ai/                      # AI 智能
│   │   │   ├── OcrView.vue
│   │   │   ├── OcrRecordsView.vue
│   │   │   └── components/
│   │   ├── course/                  # 病程记录
│   │   │   ├── CourseListView.vue
│   │   │   └── components/
│   │   ├── drug/                    # 用药医嘱
│   │   │   ├── DrugListView.vue
│   │   │   └── components/
│   │   ├── follow/                  # 出院随访
│   │   │   ├── FollowListView.vue
│   │   │   └── components/
│   │   └── user/                    # 用户管理
│   │       ├── UserListView.vue
│   │       └── components/
│   │
│   ├── App.vue                      # 根组件
│   └── main.js                      # 入口文件
│
├── .env.development                 # 开发环境变量
├── .env.production                  # 生产环境变量
├── .gitignore
├── index.html                       # HTML 模板
├── package.json                     # 项目依赖
├── vite.config.js                   # Vite 配置
└── README.md                        # 项目说明
```

## 快速开始

### 环境要求

- Node.js 16+
- npm 7+ 或 yarn / pnpm

### 安装依赖

```bash
npm install
# 或
yarn install
# 或
pnpm install
```

### 启动开发服务器

```bash
npm run dev
```

默认访问 `http://localhost:3000`

### 构建生产版本

```bash
npm run build
```

构建产物输出到 `dist/` 目录。

### 预览生产构建

```bash
npm run preview
```

## 环境配置

### 开发环境（`.env.development`）

```env
VITE_API_BASE_URL=http://localhost:9000
```

### 生产环境（`.env.production`）

```env
VITE_API_BASE_URL=/
```

## 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |
| doctor001 | 123456 | 医生 |
| nurse001 | 123456 | 护士 |

## 后端联调

前端通过 `/api` 路径访问后端接口，开发环境通过 Vite 代理转发到 `http://localhost:9000`。

后端项目参考：`../hims-backend`（Spring Boot）

主要 API 接口规范：

| 模块 | 路径前缀 |
|------|----------|
| 用户 | `/api/user` |
| 患者 | `/api/patient` |
| 住院 | `/api/inhospital` |
| 床位 | `/api/bed` |
| 体征 | `/api/vital` |
| AI OCR | `/api/ai/ocr` |
| 病程 | `/api/course` |
| 用药 | `/api/drug` |
| 随访 | `/api/follow` |
| 大屏 | `/api/dashboard` |
| 文件 | `/api/file` |

统一响应格式：
```json
{
  "code": 200,
  "msg": "success",
  "data": { ... }
}
```

## 核心功能详解

### AI OCR 识别

支持以下类型图片的智能识别：

- **身份证识别**：自动提取姓名、性别、身份证号、地址等信息，一键建档
- **就诊卡识别**：提取患者基本信息
- **医保卡识别**：识别医保信息
- **检查报告识别**：OCR 识别检查单内容
- **病历首页识别**：智能提取病历信息

### AI 风险预警

生命体征录入时自动进行 AI 风险评估：

- **评估指标**：体温、心率、血压、血氧、呼吸等
- **风险等级**：低危 / 中危 / 高危
- **预警提示**：根据异常指标生成专业医疗建议
- **实时刷新**：高危预警页面 30 秒自动刷新

### 数据大屏

可视化展示关键指标：

- **顶部卡片**：累计患者、在院患者、床位使用、高危预警、待随访、今日识别
- **科室分布**：各科室在院患者柱状图
- **实时预警**：高危患者滚动列表
- **床位饼图**：床位使用率环形图
- **失联随访**：失联患者列表
- **随访统计**：随访状态分类统计

## 路由结构

```
/login                          # 登录
/                               # 首页（业务布局）
  ├─ /dashboard                 # 仪表盘
  ├─ /big-screen                # 数据大屏
  ├─ /patient                   # 患者管理
  │   ├─ /patient/list
  │   ├─ /patient/admit
  │   └─ /patient/detail/:id
  ├─ /inhospital                # 住院管理
  ├─ /bed                       # 床位管理
  ├─ /vital                     # 生命体征
  │   ├─ /vital/record
  │   └─ /vital/high-risk
  ├─ /course                    # 病程记录
  ├─ /drug                      # 用药医嘱
  ├─ /follow                    # 出院随访
  ├─ /ai                        # AI 智能
  │   ├─ /ai/ocr
  │   └─ /ai/records
  ├─ /user                      # 用户管理
  └─ /profile                   # 个人中心
```

## 开发规范

### 命名约定

- 组件文件：大驼峰 `PatientListView.vue`
- 普通文件：小驼峰 `index.js`
- 常量：大写下划线 `API_BASE_URL`
- 变量/函数：小驼峰 `loadList()`

### 代码风格

- 使用 Vue3 Composition API + `<script setup>`
- 不使用 TypeScript
- 优先使用解构和箭头函数
- 模板中尽量简洁，复杂逻辑抽到 script

### 组件设计

- 每个页面一个文件夹，包含 `*View.vue` 和 `components/` 子目录
- 复用组件抽离到 `components/` 子目录
- 全局通用组件放到 `src/components/` 或 `src/layouts/components/`

## 浏览器支持

- Chrome 90+
- Edge 90+
- Firefox 88+
- Safari 14+

## License

MIT

## 联系方式

- 作者：HIMS Team
- 版本：1.0.0
- 文档更新时间：2026-09-19
