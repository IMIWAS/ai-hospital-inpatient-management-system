<template>
  <div class="page-container">
    <div class="page-card">
      <!-- 搜索栏 -->
      <div class="toolbar">
        <el-input
          v-model="query.keyword"
          placeholder="搜索患者姓名 / 识别类型"
          clearable
          style="width: 240px"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select
          v-model="query.ocrType"
          placeholder="识别类型"
          clearable
          style="width: 140px"
          @change="handleSearch"
        >
          <el-option
            v-for="(label, key) in OCR_TYPE"
            :key="key"
            :label="label"
            :value="label"
          />
        </el-select>
        <el-select
          v-model="query.status"
          placeholder="识别状态"
          clearable
          style="width: 130px"
          @change="handleSearch"
        >
          <el-option
            v-for="o in OCR_STATUS_OPTIONS"
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
      <el-table :data="pagedList" v-loading="loading" stripe empty-text="暂无识别记录">
        <el-table-column prop="recordNo" label="记录编号" width="150" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="ocrType" label="识别类型" width="110">
          <template #default="{ row }">
            <el-tag size="small" :type="ocrTypeTag(row.ocrType)">{{ row.ocrType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="fileName" label="文件名" min-width="160" show-overflow-tooltip />
        <el-table-column label="识别状态" width="100">
          <template #default="{ row }">
            <el-tag :type="ocrStatusOption(row.status)?.type" size="small">
              {{ OCR_STATUS[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="accuracy" label="识别准确率" width="110">
          <template #default="{ row }">
            <span v-if="row.accuracy">{{ row.accuracy }}%</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="operator" label="操作人" width="100" />
        <el-table-column prop="createTime" label="识别时间" width="160" />
        <el-table-column label="操作" width="140" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleView(row)">查看</el-button>
            <el-button type="success" link size="small" @click="handleArchive(row)">建档</el-button>
            <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrap">
        <el-pagination
          background
          layout="total, prev, pager, next, sizes"
          :total="filteredList.length"
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :page-sizes="[10, 20, 50]"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { OCR_TYPE, OCR_STATUS, OCR_STATUS_OPTIONS } from '@/utils/dict'

const loading = ref(false)

const allData = ref([
  { id: 1, recordNo: 'OCR20261018001', patientName: '孙浩然', ocrType: '身份证', fileName: 'idcard_001.jpg', status: 2, accuracy: 98, operator: 'admin', createTime: '2026-10-28 09:15' },
  { id: 2, recordNo: 'OCR20261018002', patientName: '周梦琪', ocrType: '病历首页', fileName: 'medical_record_001.jpg', status: 1, accuracy: 95, operator: 'admin', createTime: '2026-10-28 09:30' },
  { id: 3, recordNo: 'OCR20261018003', patientName: '吴思远', ocrType: '检查报告', fileName: 'lab_report_001.jpg', status: 2, accuracy: 92, operator: 'doctor001', createTime: '2026-10-28 10:00' },
  { id: 4, recordNo: 'OCR20261018004', patientName: '郑浩宇', ocrType: '医保卡', fileName: 'medical_card_001.jpg', status: 1, accuracy: 96, operator: 'admin', createTime: '2026-10-28 10:20' },
  { id: 5, recordNo: 'OCR20261018005', patientName: '黄俊杰', ocrType: '身份证', fileName: 'idcard_002.jpg', status: 3, accuracy: null, operator: 'nurse001', createTime: '2026-10-28 10:45' },
  { id: 6, recordNo: 'OCR20261018006', patientName: '林晓薇', ocrType: '就诊卡', fileName: 'visit_card_001.jpg', status: 2, accuracy: 99, operator: 'admin', createTime: '2026-10-28 11:00' },
  { id: 7, recordNo: 'OCR20261018007', patientName: '徐嘉怡', ocrType: '检查报告', fileName: 'lab_report_002.jpg', status: 0, accuracy: null, operator: 'doctor001', createTime: '2026-10-28 11:20' },
  { id: 8, recordNo: 'OCR20261018008', patientName: '何子墨', ocrType: '病历首页', fileName: 'medical_record_002.jpg', status: 2, accuracy: 94, operator: 'admin', createTime: '2026-10-28 11:35' },
  { id: 9, recordNo: 'OCR20261019001', patientName: '张子轩', ocrType: '身份证', fileName: 'idcard_003.jpg', status: 2, accuracy: 97, operator: 'admin', createTime: '2026-10-29 08:30' },
  { id: 10, recordNo: 'OCR20261019002', patientName: '林诗涵', ocrType: '医保卡', fileName: 'medical_card_002.jpg', status: 1, accuracy: 93, operator: 'nurse001', createTime: '2026-10-29 09:00' },
  { id: 11, recordNo: 'OCR20261019003', patientName: '李浩然', ocrType: '检查报告', fileName: 'lab_report_003.jpg', status: 2, accuracy: 91, operator: 'doctor001', createTime: '2026-10-29 09:30' },
  { id: 12, recordNo: 'OCR20261019004', patientName: '王梓涵', ocrType: '病历首页', fileName: 'medical_record_003.jpg', status: 3, accuracy: null, operator: 'admin', createTime: '2026-10-29 10:00' }
])

const query = reactive({
  keyword: '',
  ocrType: '',
  status: null,
  pageNum: 1,
  pageSize: 10
})

const filteredList = computed(() => {
  return allData.value.filter(item => {
    const matchKeyword = !query.keyword ||
      item.patientName.includes(query.keyword) ||
      item.ocrType.includes(query.keyword)
    const matchType = !query.ocrType || item.ocrType === query.ocrType
    const matchStatus = query.status === null || query.status === '' || item.status === query.status
    return matchKeyword && matchType && matchStatus
  })
})

const pagedList = computed(() => {
  const start = (query.pageNum - 1) * query.pageSize
  return filteredList.value.slice(start, start + query.pageSize)
})

function ocrTypeTag(type) {
  const map = { '身份证': 'primary', '就诊卡': 'success', '医保卡': 'warning', '检查报告': 'info', '病历首页': 'danger' }
  return map[type] || 'info'
}

function ocrStatusOption(status) {
  return OCR_STATUS_OPTIONS.find(o => o.value === status)
}

function handleSearch() {
  query.pageNum = 1
}

function handleReset() {
  query.keyword = ''
  query.ocrType = ''
  query.status = null
  handleSearch()
}

function handleView(row) {
  ElMessage.info(`查看识别记录 ${row.recordNo}`)
}

function handleArchive(row) {
  if (row.status === 2) {
    ElMessage.warning('该记录已建档')
    return
  }
  ElMessage.success(`已根据识别记录 ${row.recordNo} 创建患者档案`)
}

function handleDelete(row) {
  ElMessageBox.confirm(`确认删除识别记录 ${row.recordNo}？`, '提示', { type: 'warning' })
    .then(() => {
      allData.value = allData.value.filter(i => i.id !== row.id)
      ElMessage.success('删除成功')
    }).catch(() => {})
}
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
