<template>
  <div class="login-container">
    <div class="login-bg">
      <div class="bg-shape shape-1"></div>
      <div class="bg-shape shape-2"></div>
      <div class="bg-shape shape-3"></div>
    </div>

    <div class="login-box">
      <!-- 左侧：系统介绍 -->
      <div class="login-left">
        <div class="logo-area">
          <el-icon class="logo-icon"><FirstAidKit /></el-icon>
          <h1 class="system-name">HIMS</h1>
        </div>
        <h2 class="welcome-title">医院住院患者信息管理系统</h2>
        <p class="welcome-desc">AI 赋能的一体化智慧住院管理平台</p>
        <ul class="feature-list">
          <li><el-icon><Check /></el-icon>AI OCR 智能识别建档</li>
          <li><el-icon><Check /></el-icon>AI 病情风险实时预警</li>
          <li><el-icon><Check /></el-icon>智能床位分配调度</li>
          <li><el-icon><Check /></el-icon>数据大屏可视化分析</li>
        </ul>
      </div>

      <!-- 右侧：登录表单 -->
      <div class="login-right">
        <h3 class="form-title">账号登录</h3>
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="rules"
          size="large"
          @keyup.enter="handleLogin"
        >
          <el-form-item prop="userName">
            <el-input
              v-model="loginForm.userName"
              placeholder="请输入用户名"
              :prefix-icon="User"
              clearable
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>
          <el-form-item prop="role">
            <el-select v-model="loginForm.role" placeholder="请选择角色" style="width: 100%">
              <el-option label="管理员" :value="1" />
              <el-option label="医生" :value="2" />
              <el-option label="护士" :value="3" />
              <el-option label="行政" :value="4" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <div class="form-actions">
              <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
              <el-link type="primary" :underline="false">忘记密码？</el-link>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              :loading="loading"
              style="width: 100%"
              size="large"
              @click="handleLogin"
            >
              登 录
            </el-button>
          </el-form-item>
        </el-form>

        <div class="login-tips">
          <p>测试账号：</p>
          <p>管理员：admin / admin123</p>
          <p>医生：doctor001 / 123456</p>
          <p>护士：nurse001 / 123456</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  userName: 'admin',
  password: 'admin123',
  role: 1,
  remember: true
})

const rules = {
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, message: '密码长度不能少于4位', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

async function handleLogin() {
  await loginFormRef.value.validate()
  loading.value = true
  try {
    await userStore.login(loginForm.userName, loginForm.password)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch (e) {
    console.error('登录失败:', e)
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  position: relative;
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  background:
    radial-gradient(1100px 720px at 72% 18%, rgba(255, 255, 255, 0.85) 0%, transparent 62%),
    linear-gradient(135deg, #f5f5f7 0%, #ebebf0 55%, #e3e3e8 100%);
  overflow: hidden;
}

.login-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 0;

  .bg-shape {
    position: absolute;
    border-radius: 50%;
    background: rgba(20, 184, 166, 0.15);
    filter: blur(90px);

    &.shape-1 {
      width: 560px;
      height: 560px;
      top: -220px;
      left: -120px;
    }

    &.shape-2 {
      width: 420px;
      height: 420px;
      bottom: -160px;
      right: 8%;
    }

    &.shape-3 {
      width: 300px;
      height: 300px;
      top: 26%;
      right: -80px;
    }
  }
}

.login-box {
  position: relative;
  z-index: 1;
  display: flex;
  width: 900px;
  height: 560px;
  background: rgba(255, 255, 255, 0.65);
  backdrop-filter: blur(28px) saturate(160%);
  border: 1px solid rgba(255, 255, 255, 0.8);
  border-radius: 20px;
  box-shadow: 0 24px 64px rgba(0, 0, 0, 0.12);
  overflow: hidden;
}

.login-left {
  flex: 1;
  padding: 50px 40px;
  background: transparent;
  color: #1d1d1f;
  display: flex;
  flex-direction: column;
  justify-content: center;

  .logo-area {
    display: flex;
    align-items: center;
    margin-bottom: 30px;

    .logo-icon {
      font-size: 40px;
      margin-right: 12px;
      color: #0d9488;
    }

    .system-name {
      font-size: 32px;
      font-weight: bold;
      margin: 0;
      letter-spacing: -0.5px;
      color: #1d1d1f;
    }
  }

  .welcome-title {
    font-size: 22px;
    font-weight: 600;
    margin-bottom: 12px;
    color: #1d1d1f;
  }

  .welcome-desc {
    font-size: 14px;
    color: #6e6e73;
    margin-bottom: 30px;
  }

  .feature-list {
    list-style: none;
    padding: 0;
    margin: 0;

    li {
      display: flex;
      align-items: center;
      font-size: 14px;
      margin-bottom: 12px;
      color: #0d9488;

      .el-icon {
        margin-right: 8px;
        color: #0d9488;
      }
    }
  }
}

.login-right {
  flex: 1;
  padding: 50px 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: rgba(255, 255, 255, 0.55);
  border-left: 1px solid rgba(0, 0, 0, 0.06);

  .form-title {
    font-size: 24px;
    font-weight: 600;
    color: #1d1d1f;
    margin-bottom: 30px;
    text-align: center;
  }
}

.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.login-tips {
  margin-top: 20px;
  padding: 12px;
  background: rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  font-size: 12px;
  color: #86868b;

  p {
    margin: 2px 0;
  }
}
</style>
