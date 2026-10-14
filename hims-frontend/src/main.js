import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

// 引入 Element Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

// 引入图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 引入全局样式
import '@/assets/main.css'

// 引入工具类
import request from '@/utils/request'
import { useUserStore } from '@/stores/user'

const app = createApp(App)

// 注册 Element Plus（支持中文）
app.use(ElementPlus, {
  locale: zhCn,
})

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 注册 Pinia
app.use(createPinia())

// 注册路由
app.use(router)

// 全局挂载 request
app.config.globalProperties.$http = request
app.config.globalProperties.$request = request

// 初始化用户信息
const userStore = useUserStore()
userStore.initUser()

app.mount('#app')
