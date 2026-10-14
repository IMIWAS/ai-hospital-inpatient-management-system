import { createRouter, createWebHistory } from 'vue-router'

// 公共路由
const publicRoutes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/LoginView.vue'),
        meta: { title: '登录', hideInMenu: true }
    }
]

// 业务路由
const businessRoutes = [
    {
        path: '/',
        component: () => import('@/layouts/AppLayout.vue'),
        redirect: '/dashboard',
        children: [
            // 首页仪表盘
            {
                path: 'dashboard',
                name: 'Dashboard',
                component: () => import('@/views/DashboardView.vue'),
                meta: { title: '首页', icon: 'HomeFilled' }
            },

            // 数据大屏
            {
                path: 'big-screen',
                name: 'BigScreen',
                component: () => import('@/views/BigScreenView.vue'),
                meta: { title: '数据大屏', icon: 'DataAnalysis' }
            },

            // 患者管理
            {
                path: 'patient',
                name: 'Patient',
                meta: { title: '患者管理', icon: 'User' },
                children: [
                    {
                        path: 'list',
                        name: 'PatientList',
                        component: () => import('@/views/patient/PatientListView.vue'),
                        meta: { title: '患者列表', icon: 'List' }
                    },
                    {
                        path: 'admit',
                        name: 'PatientAdmit',
                        component: () => import('@/views/patient/PatientAdmitView.vue'),
                        meta: { title: '入院登记', icon: 'Plus' }
                    },
                    {
                        path: 'detail/:id',
                        name: 'PatientDetail',
                        component: () => import('@/views/patient/PatientDetailView.vue'),
                        meta: { title: '患者详情', hideInMenu: true }
                    }
                ]
            },

            // 住院管理
            {
                path: 'inhospital',
                name: 'Inhospital',
                component: () => import('@/views/inhospital/InhospitalListView.vue'),
                meta: { title: '住院管理', icon: 'FirstAidKit' }
            },

            // 床位管理
            {
                path: 'bed',
                name: 'Bed',
                component: () => import('@/views/bed/BedManageView.vue'),
                meta: { title: '床位管理', icon: 'Document' }
            },

            // 生命体征
            {
                path: 'vital',
                name: 'Vital',
                meta: { title: '生命体征', icon: 'Memo' },
                children: [
                    {
                        path: 'record',
                        name: 'VitalRecord',
                        component: () => import('@/views/vital/VitalRecordView.vue'),
                        meta: { title: '体征记录', icon: 'EditPen' }
                    },
                    {
                        path: 'high-risk',
                        name: 'VitalHighRisk',
                        component: () => import('@/views/vital/HighRiskView.vue'),
                        meta: { title: '高危预警', icon: 'WarningFilled' }
                    }
                ]
            },

            // 病程记录
            {
                path: 'course',
                name: 'Course',
                component: () => import('@/views/course/CourseListView.vue'),
                meta: { title: '病程记录', icon: 'Notebook' }
            },

            // 用药医嘱
            {
                path: 'drug',
                name: 'Drug',
                component: () => import('@/views/drug/DrugListView.vue'),
                meta: { title: '用药医嘱', icon: 'FirstAidKit' }
            },

            // 出院随访
            {
                path: 'follow',
                name: 'Follow',
                component: () => import('@/views/follow/FollowListView.vue'),
                meta: { title: '出院随访', icon: 'Phone' }
            },

            // AI OCR 识别
            {
                path: 'ai',
                name: 'AI',
                meta: { title: 'AI 智能', icon: 'MagicStick' },
                children: [
                    {
                        path: 'ocr',
                        name: 'AiOcr',
                        component: () => import('@/views/ai/OcrView.vue'),
                        meta: { title: 'OCR识别建档', icon: 'Picture' }
                    },
                    {
                        path: 'records',
                        name: 'AiRecords',
                        component: () => import('@/views/ai/OcrRecordsView.vue'),
                        meta: { title: '识别记录', icon: 'FolderOpened' }
                    }
                ]
            },

            // 用户管理
            {
                path: 'user',
                name: 'User',
                component: () => import('@/views/user/UserListView.vue'),
                meta: { title: '用户管理', icon: 'UserFilled', roles: [1] }
            },

            // 个人中心
            {
                path: 'profile',
                name: 'Profile',
                component: () => import('@/views/ProfileView.vue'),
                meta: { title: '个人中心', icon: 'UserFilled', hideInMenu: true }
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [...publicRoutes, ...businessRoutes]
})

// 路由守卫
router.beforeEach((to, from, next) => {
    // 设置页面标题
    if (to.meta?.title) {
        document.title = `${to.meta.title} - HIMS 住院信息管理系统`
    }

    // 不需要登录的页面
    if (to.path === '/login') {
        return next()
    }

    // 检查登录状态
    const userInfo = localStorage.getItem('hims_user')
    if (!userInfo) {
        return next('/login')
    }

    // 检查角色权限
    if (to.meta?.roles) {
        try {
            const user = JSON.parse(userInfo)
            if (!to.meta.roles.includes(user.roleType)) {
                return next('/dashboard')
            }
        } catch (e) {
            return next('/login')
        }
    }

    next()
})

export default router
