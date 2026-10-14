<template>
  <div class="big-screen">
    <!-- 顶部标题 -->
    <div class="screen-header">
      <div class="header-side">
        <div class="header-item">
          <el-icon><Calendar /></el-icon>
          {{ currentTime }}
        </div>
      </div>
      <h1 class="header-title">
        <el-icon><FirstAidKit /></el-icon>
        HIMS 医院住院信息管理数据大屏
      </h1>
      <div class="header-side right">
        <el-icon><User /></el-icon>
        欢迎，{{ userStore.realName }}
      </div>
    </div>

    <!-- 内容区 -->
    <div class="screen-content" v-loading="loading">
      <!-- 顶部统计卡片 -->
      <div class="top-cards">
        <div class="data-card card-1">
          <div class="card-icon"><el-icon><UserFilled /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ stats.totalPatients || 0 }}</div>
            <div class="card-label">累计患者</div>
          </div>
        </div>
        <div class="data-card card-2">
          <div class="card-icon"><el-icon><FirstAidKit /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ stats.inhospitalCount || 0 }}</div>
            <div class="card-label">在院患者</div>
          </div>
        </div>
        <div class="data-card card-3">
          <div class="card-icon"><el-icon><Bed /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ bedStats.occupiedBeds || 0 }}/{{ bedStats.totalBeds || 0 }}</div>
            <div class="card-label">床位使用</div>
          </div>
        </div>
        <div class="data-card card-4">
          <div class="card-icon"><el-icon><WarningFilled /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ highRiskList.length }}</div>
            <div class="card-label">高危预警</div>
          </div>
        </div>
        <div class="data-card card-5">
          <div class="card-icon"><el-icon><Phone /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ followStats.pending_count || 0 }}</div>
            <div class="card-label">待随访</div>
          </div>
        </div>
        <div class="data-card card-6">
          <div class="card-icon"><el-icon><Picture /></el-icon></div>
          <div class="card-info">
            <div class="card-value">{{ stats.ocrToday || 0 }}</div>
            <div class="card-label">今日识别</div>
          </div>
        </div>
      </div>

      <!-- 主体网格 -->
      <div class="main-grid">
        <!-- 左：科室住院统计 -->
        <div class="panel">
          <div class="panel-title">
            <el-icon><DataLine /></el-icon>
            科室住院分布
          </div>
          <div class="panel-content">
            <div v-for="dept in deptStats" :key="dept.name" class="dept-bar-item">
              <div class="dept-name">{{ dept.name }}</div>
              <div class="dept-bar">
                <div class="bar-fill" :style="{ width: getBarWidth(dept.count) + '%' }">
                  <span class="bar-value">{{ dept.count }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 中：高危预警 -->
        <div class="panel center-panel">
          <div class="panel-title">
            <el-icon><WarningFilled /></el-icon>
            实时高危预警
          </div>
          <div class="panel-content">
            <el-table :data="highRiskList.slice(0, 10)" size="small" stripe :show-header="true" empty-text="暂无预警">
              <el-table-column prop="patient_name" label="患者" width="80" />
              <el-table-column label="风险" width="70">
                <template #default="{ row }">
                  <el-tag :type="getRiskType(row.risk_level)" size="small">
                    {{ getRiskName(row.risk_level) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="department" label="科室" width="80" />
              <el-table-column prop="risk_tip" label="提示" show-overflow-tooltip />
              <el-table-column prop="record_time" label="时间" width="100">
                <template #default="{ row }">{{ formatDate(row.record_time, 'HH:mm') }}</template>
              </el-table-column>
            </el-table>
          </div>
        </div>

        <!-- 右：床位使用率 -->
        <div class="panel">
          <div class="panel-title">
            <el-icon><PieChart /></el-icon>
            床位使用率
          </div>
          <div class="panel-content">
            <div class="bed-summary">
              <div class="circle-chart">
                <div class="circle-inner">
                  <div class="circle-value">{{ bedStats.occupancyRate || 0 }}%</div>
                  <div class="circle-label">使用率</div>
                </div>
              </div>
            </div>
            <div class="bed-detail">
              <div class="bed-stat-item">
                <span class="dot dot-occupied"></span>
                <span class="stat-name">已使用</span>
                <span class="stat-value">{{ bedStats.occupiedBeds || 0 }}</span>
              </div>
              <div class="bed-stat-item">
                <span class="dot dot-empty"></span>
                <span class="stat-name">空床</span>
                <span class="stat-value">{{ bedStats.emptyBeds || 0 }}</span>
              </div>
              <div class="bed-stat-item">
                <span class="dot dot-maintenance"></span>
                <span class="stat-name">维修</span>
                <span class="stat-value">{{ bedStats.maintenanceBeds || 0 }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 失联随访 -->
        <div class="panel">
          <div class="panel-title">
            <el-icon><Phone /></el-icon>
            失联随访
          </div>
          <div class="panel-content">
            <el-table :data="lostFollowList" size="small" stripe empty-text="暂无失联随访">
              <el-table-column prop="patientName" label="患者" width="80" />
              <el-table-column prop="followType" label="类型" width="100" />
              <el-table-column prop="followTime" label="计划时间" width="140">
                <template #default="{ row }">{{ formatDate(row.followTime, 'MM-DD HH:mm') }}</template>
              </el-table-column>
              <el-table-column prop="phone" label="联系方式" />
            </el-table>
          </div>
        </div>

        <!-- 随访统计 -->
        <div class="panel">
          <div class="panel-title">
            <el-icon><DataAnalysis /></el-icon>
            随访统计
          </div>
          <div class="panel-content">
            <div class="follow-stat-grid">
              <div class="follow-stat-item pending">
                <div class="stat-num">{{ followStats.pending_count || 0 }}</div>
                <div class="stat-label">待随访</div>
              </div>
              <div class="follow-stat-item completed">
                <div class="stat-num">{{ followStats.completed_count || 0 }}</div>
                <div class="stat-label">已完成</div>
              </div>
              <div class="follow-stat-item lost">
                <div class="stat-num">{{ followStats.lost_count || 0 }}</div>
                <div class="stat-label">失联</div>
              </div>
              <div class="follow-stat-item total">
                <div class="stat-num">{{ followStats.total_count || 0 }}</div>
                <div class="stat-label">总数</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { dashboardApi } from '@/api'
import { formatDate } from '@/utils/date'
import { RISK_LEVEL_OPTIONS } from '@/utils/dict'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const loading = ref(false)
const currentTime = ref('')
const stats = ref({})
const bedStats = ref({})
const followStats = ref({})
const highRiskList = ref([])
const deptStats = ref([])
const lostFollowList = ref([])

let timer = null
let dataTimer = null

function getRiskType(level) {
  return RISK_LEVEL_OPTIONS.find(o => o.value === level)?.type || 'info'
}

function getRiskName(level) {
  return RISK_LEVEL_OPTIONS.find(o => o.value === level)?.label || '未知'
}

function getBarWidth(count) {
  const max = Math.max(...deptStats.value.map(d => d.count), 1)
  return (count / max) * 100
}

function updateTime() {
  const now = new Date()
  currentTime.value = formatDate(now, 'yyyy-MM-dd HH:mm:ss')
}

async function loadData() {
  loading.value = true
  try {
    const [home, bed, highRisk, follow, dept, lost] = await Promise.all([
      dashboardApi.getHomeStats(),
      dashboardApi.getBedStats(),
      dashboardApi.getHighRisk(),
      dashboardApi.getFollowStats(),
      dashboardApi.getDeptStats(),
      dashboardApi.getLostFollows()
    ])
    stats.value = home || {}
    bedStats.value = bed || {}
    followStats.value = follow || {}
    highRiskList.value = highRisk?.list || []
    deptStats.value = dept?.list || []
    lostFollowList.value = lost?.list || []
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  updateTime()
  loadData()
  // 时钟
  timer = setInterval(updateTime, 1000)
  // 数据刷新（30秒）
  dataTimer = setInterval(loadData, 30000)
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
  if (dataTimer) clearInterval(dataTimer)
})
</script>

<style lang="scss" scoped>
.big-screen {
  min-height: 100vh;
  background: linear-gradient(180deg, #f0fdfa 0%, #dcf3ef 100%);
  color: #134e4a;
  padding: 0;
}

.screen-header {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  background: linear-gradient(180deg, rgba(20, 184, 166, 0.15) 0%, transparent 100%);
  border-bottom: 2px solid rgba(20, 184, 166, 0.3);
  position: relative;

  .header-title {
    font-size: 28px;
    font-weight: 700;
    margin: 0;
    background: linear-gradient(180deg, #0f766e 0%, #0d9488 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    display: flex;
    align-items: center;
    gap: 12px;

    .el-icon {
      color: #0d9488;
      -webkit-text-fill-color: #0d9488;
    }
  }

  .header-side {
    font-size: 14px;
    color: #6b7280;
    display: flex;
    align-items: center;
    gap: 8px;
    min-width: 280px;
  }
}

.screen-content {
  padding: 20px;
}

.top-cards {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
  margin-bottom: 20px;
}

.data-card {
  background: linear-gradient(135deg, rgba(20, 184, 166, 0.2) 0%, rgba(20, 184, 166, 0.05) 100%);
  border: 1px solid rgba(20, 184, 166, 0.3);
  border-radius: 8px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  transition: transform 0.3s;

  &:hover {
    transform: translateY(-4px);
  }

  .card-icon {
    font-size: 36px;
    color: #0d9488;
  }

  .card-value {
    font-size: 28px;
    font-weight: 700;
    line-height: 1.2;
  }

  .card-label {
    font-size: 13px;
    color: #6b7280;
  }
}

.main-grid {
  display: grid;
  grid-template-columns: 1fr 1.5fr 1fr;
  gap: 16px;
}

.panel {
  background: linear-gradient(135deg, rgba(20, 184, 166, 0.1) 0%, rgba(20, 184, 166, 0.02) 100%);
  border: 1px solid rgba(20, 184, 166, 0.2);
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 16px;
}

.panel-title {
  padding: 12px 16px;
  background: linear-gradient(90deg, rgba(20, 184, 166, 0.2) 0%, transparent 100%);
  font-size: 16px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
  border-bottom: 1px solid rgba(20, 184, 166, 0.2);
}

.panel-content {
  padding: 16px;
  min-height: 280px;
}

.dept-bar-item {
  margin-bottom: 12px;

  .dept-name {
    font-size: 13px;
    margin-bottom: 4px;
    color: #6b7280;
  }

  .dept-bar {
    height: 24px;
    background: rgba(13, 148, 136, 0.08);
    border-radius: 4px;
    overflow: hidden;
  }

  .bar-fill {
    height: 100%;
    background: linear-gradient(90deg, #2dd4bf 0%, #0d9488 100%);
    transition: width 0.5s ease;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    padding: 0 8px;
    border-radius: 4px;
    min-width: 40px;
  }

  .bar-value {
    font-size: 12px;
    font-weight: 600;
    color: #fff;
  }
}

.bed-summary {
  text-align: center;
  margin: 20px 0;
}

.circle-chart {
  width: 160px;
  height: 160px;
  border-radius: 50%;
  background: conic-gradient(
    #0d9488 0% calc(var(--rate, 0) * 1%),
    rgba(13, 148, 136, 0.12) calc(var(--rate, 0) * 1%) 100%
  );
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    width: 120px;
    height: 120px;
    background: #ffffff;
    border-radius: 50%;
  }
}

.circle-inner {
  position: relative;
  z-index: 1;
  text-align: center;

  .circle-value {
    font-size: 32px;
    font-weight: 700;
    color: #0d9488;
  }

  .circle-label {
    font-size: 12px;
    color: #6b7280;
  }
}

.bed-detail {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 16px;
}

.bed-stat-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;

  .dot {
    width: 10px;
    height: 10px;
    border-radius: 50%;
  }
  .dot-occupied { background: #0d9488; }
  .dot-empty { background: #67c23a; }
  .dot-maintenance { background: #e6a23c; }

  .stat-name {
    flex: 1;
    color: #6b7280;
  }

  .stat-value {
    color: #0f766e;
    font-weight: 600;
  }
}

.follow-stat-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.follow-stat-item {
  text-align: center;
  padding: 20px;
  border-radius: 8px;

  .stat-num {
    font-size: 36px;
    font-weight: 700;
    margin-bottom: 4px;
  }

  .stat-label {
    font-size: 13px;
    opacity: 0.9;
  }

  &.pending {
    background: linear-gradient(135deg, rgba(250, 173, 20, 0.3) 0%, rgba(250, 173, 20, 0.1) 100%);
    border: 1px solid rgba(250, 173, 20, 0.5);
    color: #d97706;
  }
  &.completed {
    background: linear-gradient(135deg, rgba(82, 196, 26, 0.3) 0%, rgba(82, 196, 26, 0.1) 100%);
    border: 1px solid rgba(82, 196, 26, 0.5);
    color: #16a34a;
  }
  &.lost {
    background: linear-gradient(135deg, rgba(255, 77, 79, 0.3) 0%, rgba(255, 77, 79, 0.1) 100%);
    border: 1px solid rgba(255, 77, 79, 0.5);
    color: #dc2626;
  }
  &.total {
    background: linear-gradient(135deg, rgba(20, 184, 166, 0.3) 0%, rgba(20, 184, 166, 0.1) 100%);
    border: 1px solid rgba(20, 184, 166, 0.5);
    color: #6b7280;
  }
}
</style>
