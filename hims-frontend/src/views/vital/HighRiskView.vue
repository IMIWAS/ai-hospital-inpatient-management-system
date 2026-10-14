<template>
  <div class="page-container">
    <!-- 统计概览 -->
    <el-row :gutter="16" class="summary-row">
      <el-col :span="8">
        <div class="summary-card">
          <div class="summary-icon high"><el-icon><WarningFilled /></el-icon></div>
          <div class="summary-info">
            <div class="summary-num high-text">{{ countByLevel(3) }}</div>
            <div class="summary-label">高危患者</div>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="summary-card">
          <div class="summary-icon medium"><el-icon><Warning /></el-icon></div>
          <div class="summary-info">
            <div class="summary-num medium-text">{{ countByLevel(2) }}</div>
            <div class="summary-label">中危患者</div>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="summary-card">
          <div class="summary-icon total"><el-icon><Monitor /></el-icon></div>
          <div class="summary-info">
            <div class="summary-num">{{ list.length }}</div>
            <div class="summary-label">AI 预警监控中</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 预警列表 -->
    <div class="page-card">
      <div class="page-card-header">
        <span class="page-title">AI 风险预警患者列表</span>
        <el-button :icon="Refresh" circle @click="loadData" />
      </div>
      <el-table :data="list" v-loading="loading" stripe empty-text="暂无预警患者，所有体征平稳">
        <el-table-column prop="patient_name" label="患者姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="60" />
        <el-table-column prop="age" label="年龄" width="60" />
        <el-table-column prop="department" label="科室" width="90" />
        <el-table-column label="床位" width="90">
          <template #default="{ row }">{{ row.bed_no || '-' }}</template>
        </el-table-column>
        <el-table-column label="风险等级" width="90">
          <template #default="{ row }">
            <el-tag :type="riskOption(row.risk_level)?.type" size="small">
              {{ RISK_LEVEL[row.risk_level] || '未知' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="风险评分" width="110">
          <template #default="{ row }">
            <span :class="['score', row.risk_level === 3 ? 'high-text' : 'medium-text']">
              {{ row.risk_score ?? '-' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="risk_tip" label="AI 预警提示" min-width="200" show-overflow-tooltip />
        <el-table-column label="记录时间" width="150">
          <template #default="{ row }">
            {{ formatDate(row.record_time, 'MM-dd HH:mm') }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { vitalApi } from '@/api'
import { formatDate } from '@/utils/date'
import { RISK_LEVEL, RISK_LEVEL_OPTIONS } from '@/utils/dict'

const loading = ref(false)
const list = ref([])

function riskOption(level) {
  return RISK_LEVEL_OPTIONS.find(o => o.value === level)
}

function countByLevel(level) {
  return list.value.filter(item => item.risk_level === level).length
}

async function loadData() {
  loading.value = true
  try {
    const data = await vitalApi.getHighRisk()
    list.value = data?.list || []
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<style lang="scss" scoped>
.summary-row {
  margin-bottom: 20px;
}

.summary-card {
  display: flex;
  align-items: center;
  background: #fff;
  border-radius: 14px;
  padding: 22px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);

  .summary-icon {
    width: 52px;
    height: 52px;
    border-radius: 13px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 26px;
    margin-right: 16px;
    flex-shrink: 0;

    &.high {
      background: #ffeceb;
      color: #ff3b30;
    }
    &.medium {
      background: #fff4e5;
      color: #ff9500;
    }
    &.total {
      background: #e6faf7;
      color: #0d9488;
    }
  }

  .summary-num {
    font-size: 28px;
    font-weight: 700;
    letter-spacing: -0.5px;
    line-height: 1.2;
  }

  .summary-label {
    font-size: 13px;
    color: #86868b;
    margin-top: 4px;
  }
}

.high-text {
  color: #ff3b30;
}

.medium-text {
  color: #ff9500;
}

.score {
  font-weight: 700;
  font-size: 15px;
}
</style>
