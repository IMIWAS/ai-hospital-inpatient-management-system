<template>
  <div class="page-container">
    <div class="page-card">
      <!-- 搜索栏 -->
      <div class="toolbar">
        <el-input
          v-model="query.keyword"
          placeholder="搜索姓名 / 住院号 / 诊断"
          clearable
          style="width: 240px"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select
          v-model="query.recordType"
          placeholder="记录类型"
          clearable
          style="width: 140px"
          @change="handleSearch"
        >
          <el-option
            v-for="(label, key) in COURSE_RECORD_TYPE"
            :key="key"
            :label="label"
            :value="label"
          />
        </el-select>
        <el-button type="primary" @click="handleSearch">
          <el-icon style="margin-right: 4px"><Search /></el-icon>查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="primary" plain @click="handleAdd">
          <el-icon style="margin-right: 4px"><Plus /></el-icon>新增病程
        </el-button>
        <el-button type="success" plain @click="handleAiSummary">
          <el-icon style="margin-right: 4px"><MagicStick /></el-icon>AI 病程摘要
        </el-button>
      </div>

      <!-- 数据表格 -->
      <el-table :data="pagedList" v-loading="loading" stripe empty-text="暂无病程记录">
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="inhospitalNo" label="住院号" width="120" />
        <el-table-column prop="recordType" label="记录类型" width="110">
          <template #default="{ row }">
            <el-tag size="small" :type="recordTypeTag(row.recordType)">{{ row.recordType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="department" label="科室" width="90" />
        <el-table-column prop="doctorName" label="记录医生" width="100" />
        <el-table-column prop="chiefComplaint" label="主诉" min-width="160" show-overflow-tooltip />
        <el-table-column prop="diagnosis" label="诊断" min-width="140" show-overflow-tooltip />
        <el-table-column prop="recordTime" label="记录时间" width="160" />
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleView(row)">查看</el-button>
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
import { Search, Plus, MagicStick } from '@element-plus/icons-vue'
import { COURSE_RECORD_TYPE } from '@/utils/dict'

const loading = ref(false)

const allData = ref([
  { id: 1, patientName: '张子轩', inhospitalNo: 'IH20261010001', recordType: '日常病程', department: '内科', doctorName: '李医生', chiefComplaint: '咳嗽、咳痰3天，加重伴发热1天', diagnosis: '急性支气管炎', recordTime: '2026-10-20 09:30' },
  { id: 2, patientName: '林诗涵', inhospitalNo: 'IH20261011002', recordType: '查房记录', department: '内科', doctorName: '王主任', chiefComplaint: '反复胸闷、心悸1周', diagnosis: '冠心病', recordTime: '2026-10-21 10:15' },
  { id: 3, patientName: '李浩然', inhospitalNo: 'IH20261012003', recordType: '手术记录', department: '外科', doctorName: '赵医生', chiefComplaint: '转移性右下腹痛2天', diagnosis: '急性阑尾炎', recordTime: '2026-10-22 14:20' },
  { id: 4, patientName: '王雨桐', inhospitalNo: 'IH20261012004', recordType: '日常病程', department: '妇产科', doctorName: '刘医生', chiefComplaint: '停经40天，阴道流血1天', diagnosis: '先兆流产', recordTime: '2026-10-22 16:45' },
  { id: 5, patientName: '王梓涵', inhospitalNo: 'IH20261013005', recordType: '抢救记录', department: 'ICU', doctorName: '孙主任', chiefComplaint: '突发意识不清、抽搐', diagnosis: '脑出血', recordTime: '2026-10-23 08:10' },
  { id: 6, patientName: '李思琪', inhospitalNo: 'IH20261013006', recordType: '查房记录', department: '儿科', doctorName: '周医生', chiefComplaint: '发热2天，皮疹1天', diagnosis: '手足口病', recordTime: '2026-10-23 11:30' },
  { id: 7, patientName: '刘宇航', inhospitalNo: 'IH20261014007', recordType: '日常病程', department: '外科', doctorName: '钱医生', chiefComplaint: '尿频、尿急、尿痛3天', diagnosis: '泌尿系感染', recordTime: '2026-10-24 09:00' },
  { id: 8, patientName: '张梦瑶', inhospitalNo: 'IH20261014008', recordType: '出院记录', department: '内科', doctorName: '李医生', chiefComplaint: '反复咳嗽、咳痰10年，加重1周', diagnosis: '慢性支气管炎急性发作', recordTime: '2026-10-24 15:20' },
  { id: 9, patientName: '陈俊熙', inhospitalNo: 'IH20261015009', recordType: '日常病程', department: '内科', doctorName: '王主任', chiefComplaint: '头晕、头痛2天', diagnosis: '高血压', recordTime: '2026-10-25 08:45' },
  { id: 10, patientName: '刘雅婷', inhospitalNo: 'IH20261015010', recordType: '查房记录', department: 'ICU', doctorName: '孙主任', chiefComplaint: '术后持续发热', diagnosis: '术后感染', recordTime: '2026-10-25 10:00' },
  { id: 11, patientName: '赵一鸣', inhospitalNo: 'IH20261016011', recordType: '日常病程', department: '外科', doctorName: '赵医生', chiefComplaint: '左膝关节疼痛1月', diagnosis: '膝关节炎', recordTime: '2026-10-26 09:15' },
  { id: 12, patientName: '赵若曦', inhospitalNo: 'IH20261016012', recordType: '手术记录', department: '妇产科', doctorName: '刘医生', chiefComplaint: '子宫肌瘤', diagnosis: '子宫肌瘤', recordTime: '2026-10-26 14:30' }
])

const query = reactive({
  keyword: '',
  recordType: '',
  pageNum: 1,
  pageSize: 10
})

const filteredList = computed(() => {
  return allData.value.filter(item => {
    const matchKeyword = !query.keyword ||
      item.patientName.includes(query.keyword) ||
      item.inhospitalNo.includes(query.keyword) ||
      item.diagnosis.includes(query.keyword)
    const matchType = !query.recordType || item.recordType === query.recordType
    return matchKeyword && matchType
  })
})

const pagedList = computed(() => {
  const start = (query.pageNum - 1) * query.pageSize
  return filteredList.value.slice(start, start + query.pageSize)
})

function recordTypeTag(type) {
  const map = { '日常病程': '', '查房记录': 'primary', '抢救记录': 'danger', '手术记录': 'warning', '出院记录': 'success' }
  return map[type] || 'info'
}

function handleSearch() {
  query.pageNum = 1
}

function handleReset() {
  query.keyword = ''
  query.recordType = ''
  handleSearch()
}

function handleAdd() {
  ElMessage.info('新增病程记录功能开发中')
}

function handleView(row) {
  ElMessage.info(`查看患者 ${row.patientName} 的病程记录`)
}

function handleEdit(row) {
  ElMessage.info(`编辑患者 ${row.patientName} 的病程记录`)
}

function handleDelete(row) {
  ElMessageBox.confirm(`确认删除患者 ${row.patientName} 的病程记录？`, '提示', { type: 'warning' })
    .then(() => {
      allData.value = allData.value.filter(i => i.id !== row.id)
      ElMessage.success('删除成功')
    }).catch(() => {})
}

function handleAiSummary() {
  ElMessage.success('AI 病程摘要生成中，请稍候...')
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
