<template>
  <div class="page-container">
    <div class="page-card">
      <!-- 统计概览 -->
      <el-row :gutter="20" class="overview-row">
        <el-col :span="6">
          <div class="stat-card pending">
            <div class="stat-value">{{ pendingCount }}</div>
            <div class="stat-label">待随访</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card completed">
            <div class="stat-value">{{ completedCount }}</div>
            <div class="stat-label">已完成</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card lost">
            <div class="stat-value">{{ lostCount }}</div>
            <div class="stat-label">失联</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card total">
            <div class="stat-value">{{ allData.length }}</div>
            <div class="stat-label">总数</div>
          </div>
        </el-col>
      </el-row>

      <!-- 搜索栏 -->
      <div class="toolbar">
        <el-input
          v-model="query.keyword"
          placeholder="搜索姓名 / 住院号 / 电话"
          clearable
          style="width: 240px"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select
          v-model="query.followType"
          placeholder="随访类型"
          clearable
          style="width: 140px"
          @change="handleSearch"
        >
          <el-option
            v-for="(label, key) in FOLLOW_TYPE"
            :key="key"
            :label="label"
            :value="label"
          />
        </el-select>
        <el-select
          v-model="query.followStatus"
          placeholder="随访状态"
          clearable
          style="width: 130px"
          @change="handleSearch"
        >
          <el-option
            v-for="o in FOLLOW_STATUS_OPTIONS"
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
          <el-icon style="margin-right: 4px"><Plus /></el-icon>新增随访
        </el-button>
      </div>

      <!-- 数据表格 -->
      <el-table :data="pagedList" v-loading="loading" stripe empty-text="暂无随访记录">
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="inhospitalNo" label="住院号" width="120" />
        <el-table-column prop="followType" label="随访类型" width="110" />
        <el-table-column prop="followWay" label="随访方式" width="110" />
        <el-table-column prop="planTime" label="计划时间" width="160" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="content" label="随访内容" min-width="160" show-overflow-tooltip />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="followStatusOption(row.followStatus)?.type" size="small">
              {{ FOLLOW_STATUS[row.followStatus] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button
              v-if="row.followStatus === 0"
              type="success"
              link
              size="small"
              @click="handleComplete(row)"
            >完成</el-button>
            <el-button
              v-if="row.followStatus === 0"
              type="warning"
              link
              size="small"
              @click="handleLost(row)"
            >标记失联</el-button>
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
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
import { FOLLOW_TYPE, FOLLOW_STATUS, FOLLOW_STATUS_OPTIONS } from '@/utils/dict'

const loading = ref(false)

const allData = ref([
  { id: 1, patientName: '张子轩', inhospitalNo: 'IH20261010001', followType: '常规随访', followWay: '电话随访', planTime: '2026-10-01 09:00', phone: '138****6789', content: '出院后复查血常规，询问咳嗽缓解情况', followStatus: 0, createTime: '2026-10-24 15:30' },
  { id: 2, patientName: '林诗涵', inhospitalNo: 'IH20261011002', followType: '慢病随访', followWay: '电话随访', planTime: '2026-10-02 10:00', phone: '139****1234', content: '冠心病患者，询问胸闷、心悸发作情况，指导用药', followStatus: 0, createTime: '2026-10-25 10:00' },
  { id: 3, patientName: '李浩然', inhospitalNo: 'IH20261012003', followType: '术后随访', followWay: '门诊随访', planTime: '2026-10-03 14:00', phone: '136****5678', content: '阑尾炎术后拆线，检查伤口愈合情况', followStatus: 1, createTime: '2026-10-26 14:30' },
  { id: 4, patientName: '王雨桐', inhospitalNo: 'IH20261012004', followType: '常规随访', followWay: '电话随访', planTime: '2026-10-01 15:00', phone: '137****9012', content: '先兆流产患者，询问阴道出血及腹痛情况', followStatus: 2, createTime: '2026-10-27 09:00' },
  { id: 5, patientName: '王梓涵', inhospitalNo: 'IH20261013005', followType: '术后随访', followWay: '上门随访', planTime: '2026-10-04 09:30', phone: '135****3456', content: '脑出血术后康复评估，肢体活动恢复情况', followStatus: 0, createTime: '2026-10-28 11:00' },
  { id: 6, patientName: '李思琪', inhospitalNo: 'IH20261013006', followType: '常规随访', followWay: '电话随访', planTime: '2026-10-30 10:00', phone: '133****7890', content: '手足口病患儿，询问皮疹消退及进食情况', followStatus: 1, createTime: '2026-10-27 16:00' },
  { id: 7, patientName: '刘宇航', inhospitalNo: 'IH20261014007', followType: '常规随访', followWay: '电话随访', planTime: '2026-10-02 11:00', phone: '131****2345', content: '泌尿系感染，询问尿频尿急改善情况', followStatus: 0, createTime: '2026-10-28 09:30' },
  { id: 8, patientName: '张梦瑶', inhospitalNo: 'IH20261014008', followType: '慢病随访', followWay: '电话随访', planTime: '2026-10-03 10:30', phone: '159****6789', content: '慢性支气管炎，询问咳嗽咳痰及呼吸情况', followStatus: 1, createTime: '2026-10-29 14:00' },
  { id: 9, patientName: '陈俊熙', inhospitalNo: 'IH20261015009', followType: '慢病随访', followWay: '门诊随访', planTime: '2026-10-05 09:00', phone: '187****1234', content: '高血压患者，复查血压，调整用药', followStatus: 0, createTime: '2026-10-30 08:50' },
  { id: 10, patientName: '刘雅婷', inhospitalNo: 'IH20261015010', followType: '术后随访', followWay: '电话随访', planTime: '2026-10-04 15:00', phone: '186****5678', content: '术后感染患者，询问体温及伤口恢复情况', followStatus: 3, createTime: '2026-10-29 16:00' },
  { id: 11, patientName: '赵一鸣', inhospitalNo: 'IH20261016011', followType: '康复随访', followWay: '电话随访', planTime: '2026-10-06 10:00', phone: '132****9012', content: '膝关节炎康复指导，功能锻炼情况', followStatus: 0, createTime: '2026-10-30 10:00' },
  { id: 12, patientName: '赵若曦', inhospitalNo: 'IH20261016012', followType: '术后随访', followWay: '门诊随访', planTime: '2026-10-06 14:00', phone: '180****3456', content: '子宫肌瘤术后复查，B超检查', followStatus: 1, createTime: '2026-10-01 09:00' }
])

const query = reactive({
  keyword: '',
  followType: '',
  followStatus: null,
  pageNum: 1,
  pageSize: 10
})

const filteredList = computed(() => {
  return allData.value.filter(item => {
    const matchKeyword = !query.keyword ||
      item.patientName.includes(query.keyword) ||
      item.inhospitalNo.includes(query.keyword) ||
      item.phone.includes(query.keyword)
    const matchType = !query.followType || item.followType === query.followType
    const matchStatus = query.followStatus === null || query.followStatus === '' || item.followStatus === query.followStatus
    return matchKeyword && matchType && matchStatus
  })
})

const pagedList = computed(() => {
  const start = (query.pageNum - 1) * query.pageSize
  return filteredList.value.slice(start, start + query.pageSize)
})

const pendingCount = computed(() => allData.value.filter(i => i.followStatus === 0).length)
const completedCount = computed(() => allData.value.filter(i => i.followStatus === 1).length)
const lostCount = computed(() => allData.value.filter(i => i.followStatus === 2).length)

function followStatusOption(status) {
  return FOLLOW_STATUS_OPTIONS.find(o => o.value === status)
}

function handleSearch() {
  query.pageNum = 1
}

function handleReset() {
  query.keyword = ''
  query.followType = ''
  query.followStatus = null
  handleSearch()
}

function handleAdd() {
  ElMessage.info('新增随访功能开发中')
}

function handleEdit(row) {
  ElMessage.info(`编辑患者 ${row.patientName} 的随访计划`)
}

function handleComplete(row) {
  ElMessageBox.confirm(`确认完成患者 ${row.patientName} 的随访？`, '提示', { type: 'success' })
    .then(() => {
      row.followStatus = 1
      ElMessage.success('随访已完成')
    }).catch(() => {})
}

function handleLost(row) {
  ElMessageBox.confirm(`确认将患者 ${row.patientName} 标记为失联？`, '提示', { type: 'warning' })
    .then(() => {
      row.followStatus = 2
      ElMessage.success('已标记失联')
    }).catch(() => {})
}
</script>

<style lang="scss" scoped>
.overview-row {
  margin-bottom: 20px;
}

.stat-card {
  border-radius: 12px;
  padding: 20px;
  text-align: center;
  color: #fff;

  .stat-value {
    font-size: 28px;
    font-weight: 700;
    margin-bottom: 4px;
  }

  .stat-label {
    font-size: 13px;
    opacity: 0.9;
  }

  &.pending { background: linear-gradient(135deg, #0d9488 0%, #0f766e 100%); }
  &.completed { background: linear-gradient(135deg, #16a34a 0%, #15803d 100%); }
  &.lost { background: linear-gradient(135deg, #dc2626 0%, #b91c1c 100%); }
  &.total { background: linear-gradient(135deg, #6366f1 0%, #4f46e5 100%); }
}

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
