<template>
  <div class="sidebar">
    <!-- Logo -->
    <div class="sidebar-logo">
      <el-icon class="logo-icon"><FirstAidKit /></el-icon>
      <span v-if="!collapsed" class="logo-text">HIMS</span>
    </div>

    <!-- 菜单 -->
    <el-menu
      :default-active="activeMenu"
      :collapse="collapsed"
      :unique-opened="true"
      background-color="#fbfbfd"
      text-color="#48484a"
      active-text-color="#0d9488"
      router
    >
      <template v-for="route in menuRoutes" :key="route.path">
        <!-- 有子菜单 -->
        <el-sub-menu v-if="route.children && route.children.length > 1" :index="route.path">
          <template #title>
            <el-icon v-if="route.meta?.icon"><component :is="route.meta.icon" /></el-icon>
            <span>{{ route.meta?.title }}</span>
          </template>
          <el-menu-item
            v-for="child in getVisibleChildren(route.children)"
            :key="child.path"
            :index="resolvePath(route.path, child.path)"
          >
            <el-icon v-if="child.meta?.icon"><component :is="child.meta.icon" /></el-icon>
            <template #title>{{ child.meta?.title }}</template>
          </el-menu-item>
        </el-sub-menu>
        <!-- 无子菜单 -->
        <el-menu-item v-else-if="shouldShowMenu(route)" :index="resolvePath(route.path)">
          <el-icon v-if="route.meta?.icon"><component :is="route.meta.icon" /></el-icon>
          <template #title>{{ route.meta?.title }}</template>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

defineProps({
  collapsed: {
    type: Boolean,
    default: false
  }
})

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

// 当前激活菜单
const activeMenu = computed(() => route.path)

// 获取所有路由，过滤掉 hideInMenu
const allRoutes = router.options.routes
const businessRoutes = allRoutes.find(r => r.path === '/')

const menuRoutes = computed(() => {
  if (!businessRoutes?.children) return []
  return businessRoutes.children.filter(item => shouldShowMenu(item))
})

// 判断菜单是否应该显示
function shouldShowMenu(route) {
  // 隐藏菜单
  if (route.meta?.hideInMenu) return false
  // 角色限制
  if (route.meta?.roles) {
    return route.meta.roles.includes(userStore.roleType)
  }
  return true
}

// 获取可见的子菜单
function getVisibleChildren(children) {
  return children.filter(child => shouldShowMenu(child))
}

// 解析路径（强制返回绝对路径，避免 vue-router 相对解析）
function resolvePath(parentPath, childPath) {
  const full = childPath ? `${parentPath}/${childPath}` : parentPath
  return full.startsWith('/') ? full : '/' + full
}
</script>

<style lang="scss" scoped>
.sidebar {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.sidebar-logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #fbfbfd;
  color: #1d1d1f;
  font-size: 20px;
  font-weight: bold;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);

  .logo-icon {
    font-size: 28px;
    margin-right: 8px;
    color: #3a3a3c;
  }

  .logo-text {
    white-space: nowrap;
  }
}

:deep(.el-menu) {
  border-right: none;
  padding: 8px;
  background-color: transparent;
}

:deep(.el-menu-item),
:deep(.el-sub-menu__title) {
  height: 46px;
  line-height: 46px;
  border-radius: 10px;
  margin: 2px 0;
}

:deep(.el-menu-item.is-active) {
  background-color: #e6faf7 !important;
  color: #0d9488 !important;
  font-weight: 600;
}

:deep(.el-menu-item:hover),
:deep(.el-sub-menu__title:hover) {
  background-color: rgba(0, 0, 0, 0.04) !important;
}
</style>
