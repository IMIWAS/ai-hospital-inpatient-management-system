<template>
  <el-table v-loading="loading" :data="list" stripe size="small" empty-text="暂无用药记录">
    <el-table-column prop="drugName" label="药品" width="150" show-overflow-tooltip />
    <el-table-column prop="drugDose" label="剂量" width="80" />
    <el-table-column prop="drugFrequency" label="频次" width="100" />
    <el-table-column prop="startTime" label="开始时间" width="160">
      <template #default="{ row }">{{ formatDate(row.startTime) }}</template>
    </el-table-column>
    <el-table-column prop="executeStatus" label="状态" width="100">
      <template #default="{ row }">
        <el-tag :type="getStatusType(row.executeStatus)" size="small">{{ getStatusName(row.executeStatus) }}</el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { drugApi } from '@/api'
import { formatDate } from '@/utils/date'
import { DRUG_EXECUTE_STATUS_OPTIONS } from '@/utils/dict'

const props = defineProps({ patientId: String })

const loading = ref(false)
const list = ref([])

function getStatusType(status) { return DRUG_EXECUTE_STATUS_OPTIONS.find(o => o.value === status)?.type || 'info' }
function getStatusName(status) { return DRUG_EXECUTE_STATUS_OPTIONS.find(o => o.value === status)?.label || '未知' }

async function loadData() {
  // TODO 学生实现：drugApi.getByPatient(props.patientId)
  if (!props.patientId) return
  loading.value = true
  try {} finally { loading.value = false }
}

watch(() => props.patientId, loadData)
onMounted(loadData)
</script>
