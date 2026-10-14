<template>
  <el-table v-loading="loading" :data="list" stripe size="small" empty-text="暂无病程记录">
    <el-table-column prop="recordType" label="类型" width="100"><template #default="{ row }"><el-tag size="small">{{ row.recordType }}</el-tag></template></el-table-column>
    <el-table-column prop="createTime" label="记录时间" width="160"><template #default="{ row }">{{ formatDate(row.createTime) }}</template></el-table-column>
    <el-table-column prop="courseContent" label="病程内容" show-overflow-tooltip />
  </el-table>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { courseApi } from '@/api'
import { formatDate } from '@/utils/date'

const props = defineProps({ patientId: String })
const loading = ref(false); const list = ref([])

async function loadData() {
  // TODO 学生实现：courseApi.getByPatient(props.patientId)
  if (!props.patientId) return
  loading.value = true
  try {} finally { loading.value = false }
}
watch(() => props.patientId, loadData)
onMounted(loadData)
</script>
