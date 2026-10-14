<template>
  <div class="header">
    <!-- 左侧：折叠按钮 + 面包屑 -->
    <div class="header-left">
      <el-icon class="collapse-btn" @click="toggleSidebar">
        <Fold v-if="!collapsed" />
        <Expand v-else />
      </el-icon>
      <Breadcrumb />
    </div>

    <!-- 右侧：实时时间 + 用户信息 -->
    <div class="header-right">
      <!-- 实时时间 -->
      <div class="header-time">
        <el-icon><Clock /></el-icon>
        <span>{{ currentTime }}</span>
      </div>

      <!-- 消息通知 -->
      <el-tooltip content="高危预警" placement="bottom">
        <el-badge :value="highRiskCount" :max="99" :hidden="!highRiskCount" class="header-icon">
          <el-icon @click="goHighRisk"><WarningFilled /></el-icon>
        </el-badge>
      </el-tooltip>

      <!-- 待随访 -->
      <el-tooltip content="待随访" placement="bottom">
        <el-badge :value="pendingFollowCount" :max="99" :hidden="!pendingFollowCount" class="header-icon">
          <el-icon @click="goPendingFollow"><Bell /></el-icon>
        </el-badge>
      </el-tooltip>

      <!-- 用户信息 -->
      <el-dropdown @command="handleCommand">
        <div class="user-info">
          <el-avatar :size="32" :src="userInfo?.avatarUrl">
            <el-icon><UserFilled /></el-icon>
          </el-avatar>
          <span class="user-name">{{ userInfo?.realName || '用户' }}</span>
          <el-icon><ArrowDown /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">
              <el-icon><User /></el-icon>个人中心
            </el-dropdown-item>
            <el-dropdown-item command="password">
              <el-icon><Lock /></el-icon>修改密码
            </el-dropdown-item>
            <el-dropdown-item divided command="logout">
              <el-icon><SwitchButton /></el-icon>退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <!-- 修改密码弹窗 -->
    <PasswordDialog v-model="passwordDialogVisible" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import Breadcrumb from './Breadcrumb.vue'
import PasswordDialog from './PasswordDialog.vue'
import { useUserStore } from '@/stores/user'
import { vitalApi, followApi } from '@/api'

defineProps({
  collapsed: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['toggle'])

const router = useRouter()
const userStore = useUserStore()

const highRiskCount = ref(0)
const pendingFollowCount = ref(0)
const currentTime = ref('')
const passwordDialogVisible = ref(false)

const userInfo = computed(() => userStore.userInfo)

function toggleSidebar() {
  emit('toggle')
}

function handleCommand(command) {
  switch (command) {
    case 'profile':
      router.push('/profile')
      break
    case 'password':
      passwordDialogVisible.value = true
      break
    case 'logout':
      confirmLogout()
      break
  }
}

function confirmLogout() {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }).catch(() => {})
}

function goHighRisk() {
  router.push('/vital/high-risk')
}

function goPendingFollow() {
  router.push('/follow')
}

// 定时刷新预警数量
let timer = null

// 实时时钟
let clockTimer = null

function updateClock() {
  const now = new Date()
  const pad = (n) => String(n).padStart(2, '0')
  currentTime.value = `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`
}

async function refreshCounts() {
  try {
    const highRisk = await vitalApi.getHighRisk()
    highRiskCount.value = highRisk.count || 0
  } catch (e) {
    // 静默失败
  }
  try {
    const pending = await followApi.getPending()
    pendingFollowCount.value = pending.count || 0
  } catch (e) {
    // 静默失败
  }
}

onMounted(() => {
  refreshCounts()
  // 每60秒刷新一次
  timer = setInterval(refreshCounts, 60000)
  // 实时时钟，每秒更新
  updateClock()
  clockTimer = setInterval(updateClock, 1000)
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
  if (clockTimer) clearInterval(clockTimer)
})
</script>

<style lang="scss" scoped>
.header {
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.collapse-btn {
  font-size: 20px;
  cursor: pointer;
  color: #606266;

  &:hover {
    color: #0d9488;
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-time {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #606266;
}

.header-icon {
  font-size: 20px;
  cursor: pointer;
  color: #606266;

  &:hover {
    color: #0d9488;
  }
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 0 12px;
  height: 40px;
  border-radius: 4px;

  &:hover {
    background-color: #f5f5f7;
  }

  .user-name {
    font-size: 14px;
    color: #303133;
  }
}
</style>
