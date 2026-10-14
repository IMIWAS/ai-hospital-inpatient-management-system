<template>
  <div class="page-container">
    <div class="page-card">
      <!-- 搜索栏 -->
      <div class="toolbar">
        <el-input
          v-model="query.keyword"
          placeholder="搜索住院号 / 姓名 / 诊断"
          clearable
          style="width: 240px"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select
          v-model="query.department"
          placeholder="全部科室"
          clearable
          style="width: 140px"
          @change="handleSearch"
        >
          <el-option v-for="d in departments" :key="d" :label="d" :value="d" />
        </el-select>
        <el-select
          v-model="query.inhospitalStatus"
          placeholder="全部状态"
          clearable
          style="width: 130px"
          @change="handleSearch"
        >
          <el-option
            v-for="o in INHOSPITAL_STATUS_OPTIONS"
            :key="o.value"
            :label="o.label"
            :value="o.value"
          />
        </el-select>
        <el-button type="primary" @click="handleSearch">
          <el-icon style="margin-right: 4px"><Search /></el-icon>查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
      </div>

      <!-- 数据表格 -->
      <el-table :data="list" v-loading="loading" stripe empty-text="暂无住院记录">
        <el-table-column prop="inhospital_no" label="住院号" width="120" />
        <el-table-column prop="patient_name" label="姓名" width="90" />
        <el-table-column prop="gender" label="性别" width="60" />
        <el-table-column prop="age" label="年龄" width="60" />
        <el-table-column prop="department" label="科室" width="90" />
        <el-table-column label="床位" width="90">
          <template #default="{ row }">{{ row.bed_no || '-' }}</template>
        </el-table-column>
        <el-table-column prop="diagnosis" label="入院诊断" min-width="140" show-overflow-tooltip />
        <el-table-column label="风险等级" width="90">
          <template #default="{ row }">
            <el-tag
              v-if="row.risk_level"
              :type="riskOption(row.risk_level)?.type"
              size="small"
            >
              {{ RISK_LEVEL[row.risk_level] }}
            </el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="入院时间" width="150">
          <template #default="{ row }">
            {{ formatDate(row.inhospital_time, 'yyyy-MM-dd HH:mm') }}
          </template>
        </el-table-column>
        <el-table-column prop="inhospital_days" label="天数" width="60" />
        <el-table-column label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="statusOption(row.inhospital_status)?.type" size="small">
              {{ INHOSPITAL_STATUS[row.inhospital_status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="主管医生" width="90">
          <template #default="{ row }">{{ row.doctor_name || '-' }}</template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrap">
        <el-pagination
          background
          layout="total, prev, pager, next, sizes"
          :total="total"
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :page-sizes="[10, 20, 50]"
          @current-change="loadData"
          @size-change="handleSearch"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { inhospitalApi } from '@/api'
import { formatDate } from '@/utils/date'
import {
  RISK_LEVEL,
  RISK_LEVEL_OPTIONS,
  INHOSPITAL_STATUS,
  INHOSPITAL_STATUS_OPTIONS
} from '@/utils/dict'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const departments = ['内科', '外科', 'ICU', '妇产科', '儿科']

const query = reactive({
  keyword: '',
  department: '',
  inhospitalStatus: null,
  pageNum: 1,
  pageSize: 10
})

function riskOption(level) {
  return RISK_LEVEL_OPTIONS.find(o => o.value === level)
}

function statusOption(status) {
  return INHOSPITAL_STATUS_OPTIONS.find(o => o.value === status)
}

async function loadData() {
  loading.value = true
  try {
    const data = await inhospitalApi.getList({ ...query })
    list.value = data?.list || []
    total.value = data?.total || 0
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  query.pageNum = 1
  loadData()
}

function handleReset() {
  query.keyword = ''
  query.department = ''
  query.inhospitalStatus = null
  handleSearch()
}

onMounted(loadData)
</script>

<style lang="scss" scoped>
.toolbar {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
}

.pagination-wrap {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
