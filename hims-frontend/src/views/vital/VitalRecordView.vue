<template>
  <div class="page-container">
    <div class="page-card">
      <!-- 搜索栏 -->
      <div class="toolbar">
        <el-input
          v-model="query.keyword"
          placeholder="搜索患者姓名 / 住院号"
          clearable
          style="width: 240px"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-date-picker
          v-model="query.dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          style="width: 260px"
          value-format="YYYY-MM-DD"
          @change="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">
          <el-icon style="margin-right: 4px"><Search /></el-icon>查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="primary" plain @click="handleAdd">
          <el-icon style="margin-right: 4px"><Plus /></el-icon>录入体征
        </el-button>
        <el-button type="warning" plain @click="handleAiAssess">
          <el-icon style="margin-right: 4px"><MagicStick /></el-icon>AI 风险评估
        </el-button>
      </div>

      <!-- 数据表格 -->
      <el-table :data="pagedList" v-loading="loading" stripe empty-text="暂无体征记录">
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="inhospitalNo" label="住院号" width="120" />
        <el-table-column prop="department" label="科室" width="90" />
        <el-table-column prop="temperature" label="体温(℃)" width="100">
          <template #default="{ row }">
            <span :class="{ 'abnormal': row.temperature > 37.3 || row.temperature < 36 }">{{ row.temperature }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="bloodPressure" label="血压(mmHg)" width="110" />
        <el-table-column prop="heartRate" label="心率(次/分)" width="110">
          <template #default="{ row }">
            <span :class="{ 'abnormal': row.heartRate > 100 || row.heartRate < 60 }">{{ row.heartRate }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="spo2" label="血氧(%)" width="90">
          <template #default="{ row }">
            <span :class="{ 'abnormal': row.spo2 < 95 }">{{ row.spo2 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="respiratoryRate" label="呼吸(次/分)" width="100" />
        <el-table-column label="风险等级" width="90">
          <template #default="{ row }">
            <el-tag :type="riskOption(row.riskLevel)?.type" size="small">
              {{ RISK_LEVEL[row.riskLevel] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="recordTime" label="记录时间" width="160" />
        <el-table-column prop="nurseName" label="录入护士" width="100" />
        <el-table-column label="操作" width="140" fixed="right">
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

    <!-- 录入弹窗 -->
    <el-dialog v-model="dialogVisible" title="录入生命体征" width="600px">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="患者姓名">
              <el-input v-model="form.patientName" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="住院号">
              <el-input v-model="form.inhospitalNo" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体温(℃)">
              <el-input-number v-model="form.temperature" :min="35" :max="42" :step="0.1" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血压(mmHg)">
              <el-input v-model="form.bloodPressure" placeholder="如 120/80" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="心率(次/分)">
              <el-input-number v-model="form.heartRate" :min="30" :max="200" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血氧(%)">
              <el-input-number v-model="form.spo2" :min="80" :max="100" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="呼吸(次/分)">
              <el-input-number v-model="form.respiratoryRate" :min="10" :max="40" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">AI 评估并保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus, MagicStick } from '@element-plus/icons-vue'
import { RISK_LEVEL, RISK_LEVEL_OPTIONS } from '@/utils/dict'

const loading = ref(false)
const dialogVisible = ref(false)

const allData = ref([
  { id: 1, patientName: '张子轩', inhospitalNo: 'IH20261010001', department: '内科', temperature: 37.8, bloodPressure: '135/85', heartRate: 92, spo2: 96, respiratoryRate: 20, riskLevel: 2, recordTime: '2026-10-28 08:00', nurseName: '张护士' },
  { id: 2, patientName: '林诗涵', inhospitalNo: 'IH20261011002', department: '内科', temperature: 36.5, bloodPressure: '145/90', heartRate: 78, spo2: 97, respiratoryRate: 18, riskLevel: 2, recordTime: '2026-10-28 08:15', nurseName: '李护士' },
  { id: 3, patientName: '王梓涵', inhospitalNo: 'IH20261013005', department: 'ICU', temperature: 38.9, bloodPressure: '160/100', heartRate: 110, spo2: 91, respiratoryRate: 26, riskLevel: 3, recordTime: '2026-10-28 08:30', nurseName: '王护士' },
  { id: 4, patientName: '李思琪', inhospitalNo: 'IH20261013006', department: '儿科', temperature: 37.2, bloodPressure: '95/60', heartRate: 105, spo2: 98, respiratoryRate: 22, riskLevel: 1, recordTime: '2026-10-28 08:45', nurseName: '周护士' },
  { id: 5, patientName: '张梦瑶', inhospitalNo: 'IH20261014008', department: '内科', temperature: 36.7, bloodPressure: '120/78', heartRate: 72, spo2: 98, respiratoryRate: 16, riskLevel: 1, recordTime: '2026-10-28 09:00', nurseName: '张护士' },
  { id: 6, patientName: '陈俊熙', inhospitalNo: 'IH20261015009', department: '内科', temperature: 36.3, bloodPressure: '155/95', heartRate: 80, spo2: 96, respiratoryRate: 18, riskLevel: 2, recordTime: '2026-10-28 09:15', nurseName: '李护士' },
  { id: 7, patientName: '刘雅婷', inhospitalNo: 'IH20261015010', department: 'ICU', temperature: 38.2, bloodPressure: '130/82', heartRate: 96, spo2: 93, respiratoryRate: 24, riskLevel: 3, recordTime: '2026-10-28 09:30', nurseName: '王护士' },
  { id: 8, patientName: '刘宇航', inhospitalNo: 'IH20261014007', department: '外科', temperature: 36.6, bloodPressure: '118/75', heartRate: 70, spo2: 99, respiratoryRate: 17, riskLevel: 1, recordTime: '2026-10-28 09:45', nurseName: '周护士' },
  { id: 9, patientName: '赵一鸣', inhospitalNo: 'IH20261016011', department: '外科', temperature: 37.0, bloodPressure: '128/82', heartRate: 76, spo2: 97, respiratoryRate: 19, riskLevel: 1, recordTime: '2026-10-28 10:00', nurseName: '张护士' },
  { id: 10, patientName: '赵若曦', inhospitalNo: 'IH20261016012', department: '妇产科', temperature: 36.8, bloodPressure: '115/72', heartRate: 74, spo2: 98, respiratoryRate: 18, riskLevel: 1, recordTime: '2026-10-28 10:15', nurseName: '李护士' },
  { id: 11, patientName: '王雨桐', inhospitalNo: 'IH20261012004', department: '妇产科', temperature: 36.4, bloodPressure: '110/70', heartRate: 70, spo2: 99, respiratoryRate: 16, riskLevel: 1, recordTime: '2026-10-28 10:30', nurseName: '王护士' },
  { id: 12, patientName: '李浩然', inhospitalNo: 'IH20261012003', department: '外科', temperature: 37.1, bloodPressure: '125/80', heartRate: 78, spo2: 97, respiratoryRate: 18, riskLevel: 1, recordTime: '2026-10-28 10:45', nurseName: '周护士' }
])

const query = reactive({
  keyword: '',
  dateRange: null,
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  patientName: '',
  inhospitalNo: '',
  temperature: 36.5,
  bloodPressure: '',
  heartRate: 75,
  spo2: 98,
  respiratoryRate: 18
})

const filteredList = computed(() => {
  return allData.value.filter(item => {
    const matchKeyword = !query.keyword ||
      item.patientName.includes(query.keyword) ||
      item.inhospitalNo.includes(query.keyword)
    return matchKeyword
  })
})

const pagedList = computed(() => {
  const start = (query.pageNum - 1) * query.pageSize
  return filteredList.value.slice(start, start + query.pageSize)
})

function riskOption(level) {
  return RISK_LEVEL_OPTIONS.find(o => o.value === level)
}

function handleSearch() {
  query.pageNum = 1
}

function handleReset() {
  query.keyword = ''
  query.dateRange = null
  handleSearch()
}

function handleAdd() {
  dialogVisible.value = true
}

function handleSave() {
  ElMessage.success('AI 风险评估完成，体征已保存')
  dialogVisible.value = false
}

function handleAiAssess() {
  ElMessage.success('正在对全量体征数据进行 AI 风险评估...')
}

function handleView(row) {
  ElMessage.info(`查看患者 ${row.patientName} 的体征记录`)
}

function handleEdit(row) {
  ElMessage.info(`编辑患者 ${row.patientName} 的体征记录`)
}

function handleDelete(row) {
  ElMessageBox.confirm(`确认删除患者 ${row.patientName} 的体征记录？`, '提示', { type: 'warning' })
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

.abnormal {
  color: #f56c6c;
  font-weight: 600;
}
</style>
