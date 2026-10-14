<template>
  <div class="page-container">
    <div class="page-card">
      <!-- 搜索栏 -->
      <div class="toolbar">
        <el-input
          v-model="query.keyword"
          placeholder="搜索姓名 / 药品名 / 医嘱号"
          clearable
          style="width: 240px"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select
          v-model="query.executeStatus"
          placeholder="执行状态"
          clearable
          style="width: 140px"
          @change="handleSearch"
        >
          <el-option
            v-for="o in DRUG_EXECUTE_STATUS_OPTIONS"
            :key="o.value"
            :label="o.label"
            :value="o.value"
          />
        </el-select>
        <el-button type="primary" @click="handleSearch">
          <el-icon style="margin-right: 4px"><Search /></el-icon>查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="primary" plain @click="handleAdd">
          <el-icon style="margin-right: 4px"><Plus /></el-icon>新增医嘱
        </el-button>
      </div>

      <!-- 数据表格 -->
      <el-table :data="pagedList" v-loading="loading" stripe empty-text="暂无用药医嘱">
        <el-table-column prop="orderNo" label="医嘱号" width="130" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="drugName" label="药品名称" min-width="140" show-overflow-tooltip />
        <el-table-column prop="specification" label="规格" width="100" />
        <el-table-column prop="dosage" label="剂量" width="90" />
        <el-table-column prop="route" label="给药途径" width="100" />
        <el-table-column prop="frequency" label="频次" width="100" />
        <el-table-column prop="doctorName" label="开嘱医生" width="100" />
        <el-table-column label="执行状态" width="100">
          <template #default="{ row }">
            <el-tag :type="executeStatusOption(row.executeStatus)?.type" size="small">
              {{ DRUG_EXECUTE_STATUS[row.executeStatus] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="开嘱时间" width="160" />
        <el-table-column label="操作" width="140" fixed="right">
          <template #default="{ row }">
            <el-button
              v-if="row.executeStatus === 0"
              type="success"
              link
              size="small"
              @click="handleExecute(row)"
            >执行</el-button>
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
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
import { Search, Plus } from '@element-plus/icons-vue'
import { DRUG_EXECUTE_STATUS, DRUG_EXECUTE_STATUS_OPTIONS } from '@/utils/dict'

const loading = ref(false)

const allData = ref([
  { id: 1, orderNo: 'OD20261010001', patientName: '张子轩', drugName: '阿莫西林胶囊', specification: '0.25g*24粒', dosage: '0.5g', route: '口服', frequency: '每日3次', doctorName: '李医生', executeStatus: 0, createTime: '2026-10-20 09:35' },
  { id: 2, orderNo: 'OD20261010002', patientName: '张子轩', drugName: '布洛芬缓释胶囊', specification: '0.3g*20粒', dosage: '0.3g', route: '口服', frequency: '每日2次', doctorName: '李医生', executeStatus: 1, createTime: '2026-10-20 09:40' },
  { id: 3, orderNo: 'OD20261011001', patientName: '林诗涵', drugName: '阿司匹林肠溶片', specification: '100mg*30片', dosage: '100mg', route: '口服', frequency: '每日1次', doctorName: '王主任', executeStatus: 1, createTime: '2026-10-21 10:20' },
  { id: 4, orderNo: 'OD20261011002', patientName: '林诗涵', drugName: '硝酸甘油片', specification: '0.5mg*100片', dosage: '0.5mg', route: '口服', frequency: '必要时服用', doctorName: '王主任', executeStatus: 2, createTime: '2026-10-21 10:25' },
  { id: 5, orderNo: 'OD20261012001', patientName: '李浩然', drugName: '头孢曲松钠注射液', specification: '1g/支', dosage: '2g', route: '静脉注射', frequency: '每日2次', doctorName: '赵医生', executeStatus: 1, createTime: '2026-10-22 14:30' },
  { id: 6, orderNo: 'OD20261012002', patientName: '李浩然', drugName: '甲硝唑注射液', specification: '0.5g/100ml', dosage: '0.5g', route: '静脉注射', frequency: '每日2次', doctorName: '赵医生', executeStatus: 1, createTime: '2026-10-22 14:35' },
  { id: 7, orderNo: 'OD20261013001', patientName: '王梓涵', drugName: '甘露醇注射液', specification: '20% 250ml', dosage: '250ml', route: '静脉注射', frequency: '每日1次', doctorName: '孙主任', executeStatus: 1, createTime: '2026-10-23 08:20' },
  { id: 8, orderNo: 'OD20261013002', patientName: '王梓涵', drugName: '苯巴比妥钠注射液', specification: '0.1g/支', dosage: '0.1g', route: '肌肉注射', frequency: '必要时服用', doctorName: '孙主任', executeStatus: 3, createTime: '2026-10-23 09:00' },
  { id: 9, orderNo: 'OD20261014001', patientName: '张梦瑶', drugName: '氨溴索口服液', specification: '100ml', dosage: '10ml', route: '口服', frequency: '每日3次', doctorName: '李医生', executeStatus: 1, createTime: '2026-10-24 09:05' },
  { id: 10, orderNo: 'OD20261014002', patientName: '张梦瑶', drugName: '阿奇霉素片', specification: '0.25g*6片', dosage: '0.5g', route: '口服', frequency: '每日1次', doctorName: '李医生', executeStatus: 0, createTime: '2026-10-24 09:10' },
  { id: 11, orderNo: 'OD20261015001', patientName: '陈俊熙', drugName: '硝苯地平缓释片', specification: '20mg*30片', dosage: '20mg', route: '口服', frequency: '每日2次', doctorName: '王主任', executeStatus: 1, createTime: '2026-10-25 08:50' },
  { id: 12, orderNo: 'OD20261016001', patientName: '赵一鸣', drugName: '双氯芬酸钠缓释片', specification: '75mg*10片', dosage: '75mg', route: '口服', frequency: '每日1次', doctorName: '赵医生', executeStatus: 1, createTime: '2026-10-26 09:20' }
])

const query = reactive({
  keyword: '',
  executeStatus: null,
  pageNum: 1,
  pageSize: 10
})

const filteredList = computed(() => {
  return allData.value.filter(item => {
    const matchKeyword = !query.keyword ||
      item.patientName.includes(query.keyword) ||
      item.drugName.includes(query.keyword) ||
      item.orderNo.includes(query.keyword)
    const matchStatus = query.executeStatus === null || query.executeStatus === '' || item.executeStatus === query.executeStatus
    return matchKeyword && matchStatus
  })
})

const pagedList = computed(() => {
  const start = (query.pageNum - 1) * query.pageSize
  return filteredList.value.slice(start, start + query.pageSize)
})

function executeStatusOption(status) {
  return DRUG_EXECUTE_STATUS_OPTIONS.find(o => o.value === status)
}

function handleSearch() {
  query.pageNum = 1
}

function handleReset() {
  query.keyword = ''
  query.executeStatus = null
  handleSearch()
}

function handleAdd() {
  ElMessage.info('新增医嘱功能开发中')
}

function handleEdit(row) {
  ElMessage.info(`编辑医嘱 ${row.orderNo}`)
}

function handleDelete(row) {
  ElMessageBox.confirm(`确认删除医嘱 ${row.orderNo}？`, '提示', { type: 'warning' })
    .then(() => {
      allData.value = allData.value.filter(i => i.id !== row.id)
      ElMessage.success('删除成功')
    }).catch(() => {})
}

function handleExecute(row) {
  ElMessageBox.confirm(`确认执行医嘱 ${row.orderNo}？`, '提示', { type: 'success' })
    .then(() => {
      row.executeStatus = 1
      ElMessage.success('医嘱已执行')
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
