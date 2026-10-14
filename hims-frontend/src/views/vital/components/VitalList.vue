<template>
  <el-table
    v-loading="loading"
    :data="list"
    stripe
    size="small"
    :show-header="true"
    empty-text="暂无生命体征记录"
  >
    <el-table-column prop="recordTime" label="记录时间" width="160">
      <template #default="{ row }">{{ formatDate(row.recordTime) }}</template>
    </el-table-column>
    <el-table-column prop="temperature" label="体温" width="70" />
    <el-table-column prop="heartRate" label="心率" width="70" />
    <el-table-column prop="bpHigh" label="收缩压" width="70" />
    <el-table-column prop="bpLow" label="舒张压" width="70" />
    <el-table-column prop="bloodOxygen" label="血氧" width="70" />
    <el-table-column prop="riskLevel" label="风险等级" width="100">
      <template #default="{ row }">
        <el-tag :type="getRiskType(row.riskLevel)" size="small">
          {{ getRiskName(row.riskLevel) }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="riskTip" label="预警提示" show-overflow-tooltip />
  </el-table>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { vitalApi } from '@/api'
import { formatDate } from '@/utils/date'
import { RISK_LEVEL_OPTIONS } from '@/utils/dict'

const props = defineProps({
  patientId: {
    type: String,
    required: true
  }
})

const loading = ref(false)
const list = ref([])

function getRiskType(level) {
  return RISK_LEVEL_OPTIONS.find(o => o.value === level)?.type || 'info'
}

function getRiskName(level) {
  return RISK_LEVEL_OPTIONS.find(o => o.value === level)?.label || '未知'
}

async function loadData() {
  if (!props.patientId) return
  loading.value = true
  try {
    const data = await vitalApi.getHistory(props.patientId, 20)
    list.value = data?.list || []
  } finally {
    loading.value = false
  }
}

watch(() => props.patientId, loadData)
onMounted(loadData)
</script>
