<template>
  <el-container class="app-layout">
    <!-- 侧边栏 -->
    <el-aside :width="sidebarWidth" class="app-aside">
      <Sidebar :collapsed="isCollapsed" />
    </el-aside>

    <!-- 右侧主体 -->
    <el-container class="app-main">
      <!-- 头部 -->
      <el-header class="app-header">
        <Header />
      </el-header>

      <!-- 内容区 -->
      <el-main class="app-content">
        <router-view v-slot="{ Component, route }">
          <transition name="fade-transform" mode="out-in">
            <keep-alive>
              <component :is="Component" :key="route.fullPath" />
            </keep-alive>
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import Sidebar from './components/Sidebar.vue'
import Header from './components/Header.vue'

const isCollapsed = ref(false)

const sidebarWidth = computed(() => {
  return isCollapsed.value ? '64px' : '220px'
})
</script>

<style lang="scss" scoped>
.app-layout {
  height: 100vh;
  width: 100vw;
}

.app-aside {
  background-color: #fbfbfd;
  transition: width 0.3s;
  overflow: hidden;
}

.app-main {
  background-color: #f5f5f7;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.app-header {
  background: rgba(255, 255, 255, 0.72);
  backdrop-filter: blur(20px) saturate(180%);
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  padding: 0;
  height: 60px;
}

.app-content {
  padding: 0;
  overflow-y: auto;
  background-color: #f5f5f7;
}

.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.3s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
