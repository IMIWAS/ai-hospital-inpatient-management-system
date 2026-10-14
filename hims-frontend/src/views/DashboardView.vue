<template>
  <div class="dashboard">
    <!-- 欢迎卡片 -->
    <div class="welcome-card">
      <div class="welcome-info">
        <h2 class="welcome-title">
          <el-icon><Sunny /></el-icon>
          欢迎回来，{{ userStore.realName }}！
        </h2>
        <p class="welcome-desc">
          今天是 {{ today }}，{{ userStore.roleName }}，祝您工作愉快！
        </p>
      </div>
      <div class="welcome-icon">
        <el-icon><FirstAidKit /></el-icon>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6" v-for="item in statsCards" :key="item.title">
        <div class="stat-card">
          <div class="stat-icon" :style="{ background: item.iconBg, color: item.iconColor }">
            <el-icon><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-title">{{ item.title }}</div>
            <div class="stat-value">{{ item.value }}</div>
            <div class="stat-desc">{{ item.desc }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 快捷功能 -->
    <el-row :gutter="20" class="quick-row">
      <el-col :span="24">
        <div class="page-card">
          <div class="page-card-header">
            <span class="page-title">快捷功能</span>
          </div>
          <div class="quick-grid">
            <div
              v-for="item in quickLinks"
              :key="item.title"
              class="quick-item"
              @click="$router.push(item.path)"
            >
              <el-icon class="quick-icon" :style="{ background: item.color }">
                <component :is="item.icon" />
              </el-icon>
              <span class="quick-title">{{ item.title }}</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 详细内容 -->
    <el-row :gutter="20" class="content-row">
      <el-col :span="14">
        <div class="page-card">
          <div class="page-card-header">
            <span class="page-title">高危预警患者</span>
            <el-link type="primary" @click="$router.push('/vital/high-risk')">查看全部</el-link>
          </div>
          <el-table :data="highRiskList" stripe size="default" empty-text="暂无高危预警">
            <el-table-column prop="patient_name" label="患者姓名" width="100" />
            <el-table-column prop="gender" label="性别" width="60" />
            <el-table-column prop="age" label="年龄" width="60" />
            <el-table-column label="风险等级" width="100">
              <template #default="{ row }">
                <el-tag :type="getRiskType(row.risk_level)" size="small">
                  {{ getRiskName(row.risk_level) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="department" label="科室" width="100" />
            <el-table-column prop="bed_no" label="床位" width="80" />
            <el-table-column prop="risk_tip" label="预警提示" show-overflow-tooltip />
          </el-table>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="page-card">
          <div class="page-card-header">
            <span class="page-title">床位使用情况</span>
          </div>
          <div class="bed-stats">
            <div class="bed-summary">
              <div class="summary-item">
                <div class="summary-value">{{ bedStats.totalBeds || 0 }}</div>
                <div class="summary-label">总床位</div>
              </div>
              <div class="summary-item">
                <div class="summary-value text-success">{{ bedStats.occupiedBeds || 0 }}</div>
                <div class="summary-label">已使用</div>
              </div>
              <div class="summary-item">
                <div class="summary-value text-warning">{{ bedStats.emptyBeds || 0 }}</div>
                <div class="summary-label">空床</div>
              </div>
              <div class="summary-item">
                <div class="summary-value text-primary">{{ bedStats.occupancyRate || 0 }}%</div>
                <div class="summary-label">使用率</div>
              </div>
            </div>
            <el-divider />
            <div class="dept-list">
              <div v-for="dept in bedStats.statsByDept || []" :key="dept.name" class="dept-item">
                <span class="dept-name">{{ dept.name }}</span>
                <el-progress
                  :percentage="dept.occupancy_rate || 0"
                  :stroke-width="10"
                  :show-text="false"
                  style="flex: 1; margin: 0 10px;"
                />
                <span class="dept-value">{{ dept.occupied_count }}/{{ dept.total_count }}</span>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { dashboardApi } from '@/api'
import { useUserStore } from '@/stores/user'
import { formatDate } from '@/utils/date'
import { RISK_LEVEL } from '@/utils/dict'

const userStore = useUserStore()

const today = formatDate(new Date(), 'yyyy年MM月dd日')

const homeStats = ref({})
const bedStats = ref({})
const highRiskList = ref([])

const statsCards = computed(() => [
  {
    title: '在院患者',
    value: inhospitalCount.value,
    desc: '当前住院人数',
    icon: 'UserFilled',
    iconBg: '#e6faf7',
    iconColor: '#0d9488'
  },
  {
    title: '高危预警',
    value: homeStats.value.highRiskPatients?.count || 0,
    desc: '需要重点关注',
    icon: 'WarningFilled',
    iconBg: '#e6faf7',
    iconColor: '#0d9488'
  },
  {
    title: '待随访',
    value: homeStats.value.pendingFollows?.count || 0,
    desc: '待完成随访',
    icon: 'Phone',
    iconBg: '#e6faf7',
    iconColor: '#0d9488'
  },
  {
    title: '床位使用率',
    value: (bedStats.value.occupancyRate || 0) + '%',
    desc: `共 ${bedStats.value.totalBeds || 0} 张床位`,
    icon: 'Bed',
    iconBg: '#e6faf7',
    iconColor: '#0d9488'
  }
])

const inhospitalCount = computed(() => {
  const stats = homeStats.value.inhospitalStats?.data || []
  return stats.reduce((sum, item) => sum + (item.inhospital_count || 0), 0)
})

const quickLinks = [
  { title: '患者入院', path: '/patient/admit', icon: 'Plus', color: '#0d9488' },
  { title: '床位分配', path: '/bed', icon: 'Bed', color: '#0d9488' },
  { title: '医嘱录入', path: '/drug', icon: 'FirstAidKit', color: '#0d9488' },
  { title: 'AI识别', path: '/ai/ocr', icon: 'Picture', color: '#0d9488' },
  { title: '数据大屏', path: '/big-screen', icon: 'DataAnalysis', color: '#0d9488' },
  { title: '用户管理', path: '/user', icon: 'UserFilled', color: '#0d9488' }
]

function getRiskType(level) {
  return { 1: 'success', 2: 'warning', 3: 'danger' }[level] || 'info'
}

function getRiskName(level) {
  return RISK_LEVEL[level] || '未知'
}

async function loadData() {
  try {
    const [home, bed, highRisk] = await Promise.all([
      dashboardApi.getHomeStats(),
      dashboardApi.getBedStats(),
      dashboardApi.getHighRisk()
    ])
    homeStats.value = home || {}
    bedStats.value = bed || {}
    highRiskList.value = (highRisk?.list || []).slice(0, 8)
  } catch (e) {
    console.error('加载仪表盘数据失败:', e)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style lang="scss" scoped>
.dashboard {
  padding: 20px;
}

.welcome-card {
  background: linear-gradient(120deg, #0d9488 0%, #14b8a6 55%, #2dd4bf 100%);
  color: #fff;
  border-radius: 14px;
  padding: 28px 32px;
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 8px 24px rgba(13, 148, 136, 0.25);

  .welcome-title {
    display: flex;
    align-items: center;
    font-size: 24px;
    font-weight: 700;
    letter-spacing: -0.3px;
    margin: 0 0 8px 0;

    .el-icon {
      margin-right: 10px;
      font-size: 24px;
      color: #fff;
    }
  }

  .welcome-desc {
    margin: 0;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.85);
  }

  .welcome-icon {
    font-size: 72px;
    color: rgba(255, 255, 255, 0.25);
  }
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  background: #fff;
  border-radius: 14px;
  padding: 22px;
  color: #1d1d1f;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: transform 0.3s, box-shadow 0.3s;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  }

  .stat-icon {
    width: 52px;
    height: 52px;
    border-radius: 13px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 26px;
    margin-right: 16px;
    flex-shrink: 0;
  }

  .stat-title {
    font-size: 13px;
    color: #86868b;
    margin-bottom: 6px;
  }

  .stat-value {
    font-size: 28px;
    font-weight: 700;
    letter-spacing: -0.5px;
    margin-bottom: 2px;
  }

  .stat-desc {
    font-size: 12px;
    color: #a1a1a6;
  }
}

.quick-row {
  margin-bottom: 20px;
}

.quick-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
}

.quick-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border-radius: 14px;
  background-color: #f5f5f7;
  cursor: pointer;
  transition: all 0.3s;

  &:hover {
    background-color: #ebebed;
    transform: translateY(-3px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
  }

  .quick-icon {
    width: 46px;
    height: 46px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-size: 22px;
    margin-bottom: 10px;
  }

  .quick-title {
    font-size: 13px;
    color: #1d1d1f;
  }
}

.content-row {
  margin-bottom: 20px;
}

.bed-stats {
  padding: 10px 0;
}

.bed-summary {
  display: flex;
  justify-content: space-around;
  text-align: center;

  .summary-item {
    flex: 1;
  }

  .summary-value {
    font-size: 28px;
    font-weight: 700;
    margin-bottom: 4px;
  }

  .summary-label {
    font-size: 13px;
    color: #909399;
  }
}

.text-success {
  color: #67c23a;
}

.text-warning {
  color: #e6a23c;
}

.text-primary {
  color: #0d9488;
}

.dept-list {
  .dept-item {
    display: flex;
    align-items: center;
    padding: 8px 0;
    font-size: 14px;

    .dept-name {
      width: 80px;
      color: #303133;
    }

    .dept-value {
      color: #606266;
      font-size: 13px;
      min-width: 60px;
      text-align: right;
    }
  }
}
</style>
