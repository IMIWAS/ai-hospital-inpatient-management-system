<template>
  <div class="page-container">
    <div class="page-card">
      <div class="profile-header">
        <el-avatar :size="80" :src="userInfo?.avatarUrl">
          <el-icon><UserFilled /></el-icon>
        </el-avatar>
        <div class="profile-info">
          <h2 class="profile-name">{{ userInfo?.realName }}</h2>
          <p class="profile-meta">
            <el-tag size="small">{{ ROLE_TYPE[userInfo?.roleType] }}</el-tag>
            <span style="margin-left: 8px;">{{ userInfo?.department || '未分配' }}</span>
            <span v-if="userInfo?.title" style="margin-left: 8px;">{{ userInfo.title }}</span>
          </p>
          <p class="profile-username">
            用户名：{{ userInfo?.userName }} | 用户ID：{{ userInfo?.userId }}
          </p>
        </div>
      </div>

      <el-divider />

      <el-descriptions :column="2" border>
        <el-descriptions-item label="用户名">{{ userInfo?.userName }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ userInfo?.realName }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ ROLE_TYPE[userInfo?.roleType] }}</el-descriptions-item>
        <el-descriptions-item label="科室">{{ userInfo?.department || '-' }}</el-descriptions-item>
        <el-descriptions-item label="职称">{{ userInfo?.title || '-' }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ userInfo?.phone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="邮箱" :span="2">{{ userInfo?.email || '-' }}</el-descriptions-item>
        <el-descriptions-item label="账号状态">
          <el-tag :type="userInfo?.status === 1 ? 'success' : 'danger'" size="small">
            {{ userInfo?.status === 1 ? '正常' : '禁用' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(userInfo?.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="最后登录">{{ formatDate(userInfo?.lastLoginTime) || '首次登录' }}</el-descriptions-item>
        <el-descriptions-item label="最后登录IP">{{ userInfo?.lastLoginIp || '-' }}</el-descriptions-item>
      </el-descriptions>

      <div style="margin-top: 20px; text-align: center;">
        <el-button type="primary" @click="$refs.passwordDialog.open()">
          <el-icon><Lock /></el-icon>修改密码
        </el-button>
        <el-button @click="refreshInfo">
          <el-icon><Refresh /></el-icon>刷新信息
        </el-button>
      </div>

      <PasswordDialog ref="passwordDialog" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { formatDate } from '@/utils/date'
import { ROLE_TYPE } from '@/utils/dict'
import PasswordDialog from '@/layouts/components/PasswordDialog.vue'

const userStore = useUserStore()

const userInfo = computed(() => userStore.userInfo)

function refreshInfo() {
  userStore.refreshUserInfo()
}
</script>

<style lang="scss" scoped>
.profile-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 0;
}

.profile-info {
  flex: 1;

  .profile-name {
    margin: 0 0 8px 0;
    font-size: 24px;
    font-weight: 600;
  }

  .profile-meta {
    margin: 4px 0;
    color: #606266;
  }

  .profile-username {
    margin: 4px 0;
    color: #909399;
    font-size: 13px;
  }
}
</style>
