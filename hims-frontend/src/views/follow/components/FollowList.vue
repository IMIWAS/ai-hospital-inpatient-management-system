<template>
  <el-table v-loading="loading" :data="list" stripe size="small" empty-text="暂无随访记录">
    <el-table-column prop="followType" label="类型" width="100">
      <template #default="{ row }"><el-tag size="small">{{ row.followType }}</el-tag></template>
    </el-table-column>
    <el-table-column prop="followTime" label="计划时间" width="160">
      <template #default="{ row }">{{ formatDate(row.followTime) }}</template>
    </el-table-column>
    <el-table-column prop="followWay" label="方式" width="100" />
    <el-table-column prop="followStatus" label="状态" width="100">
      <template #default="{ row }">
        <el-tag :type="getStatusType(row.followStatus)" size="small">{{ getStatusName(row.followStatus) }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="followContent" label="随访内容" show-overflow-tooltip />
  </el-table>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { followApi } from '@/api'
import { formatDate } from '@/utils/date'
import { FOLLOW_STATUS_OPTIONS } from '@/utils/dict'

const props = defineProps({ patientId: String })

const loading = ref(false)
const list = ref([])

function getStatusType(status) { return FOLLOW_STATUS_OPTIONS.find(o => o.value === status)?.type || 'info' }
function getStatusName(status) { return FOLLOW_STATUS_OPTIONS.find(o => o.value === status)?.label || '未知' }

async function loadData() {
  // TODO 学生实现：followApi.getByPatient(props.patientId)
  if (!props.patientId) return
  loading.value = true
  try { /* 占位 */ } finally { loading.value = false }
}

watch(() => props.patientId, loadData)
onMounted(loadData)
</script>
