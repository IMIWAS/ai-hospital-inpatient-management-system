<template>
  <div class="page-container">
    <div class="page-card">
      <!-- 搜索栏 -->
      <div class="toolbar">
        <el-input
          v-model="query.keyword"
          placeholder="搜索姓名 / 身份证号 / 电话"
          clearable
          style="width: 260px"
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select
          v-model="query.gender"
          placeholder="性别"
          clearable
          style="width: 100px"
          @change="handleSearch"
        >
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
        <el-button type="primary" @click="handleSearch">
          <el-icon style="margin-right: 4px"><Search /></el-icon>查询
        </el-button>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="primary" plain @click="handleAdd">
          <el-icon style="margin-right: 4px"><Plus /></el-icon>新增患者
        </el-button>
        <el-button type="success" plain @click="handleOcr">
          <el-icon style="margin-right: 4px"><Picture /></el-icon>AI OCR 建档
        </el-button>
      </div>

      <!-- 数据表格 -->
      <el-table :data="pagedList" v-loading="loading" stripe empty-text="暂无患者信息">
        <el-table-column prop="patientNo" label="患者编号" width="120" />
        <el-table-column prop="name" label="姓名" width="90" />
        <el-table-column prop="gender" label="性别" width="60" />
        <el-table-column prop="age" label="年龄" width="60" />
        <el-table-column prop="idCard" label="身份证号" width="180" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="bloodType" label="血型" width="70" />
        <el-table-column prop="address" label="住址" min-width="160" show-overflow-tooltip />
        <el-table-column prop="emergencyContact" label="紧急联系人" width="100" />
        <el-table-column prop="createTime" label="建档时间" width="160" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleView(row)">详情</el-button>
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="success" link size="small" @click="handleAdmit(row)">入院</el-button>
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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus, Picture } from '@element-plus/icons-vue'

const router = useRouter()
const loading = ref(false)

const allData = ref([
  { id: 1, patientNo: 'P20261010001', name: '张子轩', gender: '男', age: 62, idCard: '370102196401011234', phone: '138****6789', bloodType: 'A', address: '山东省济南市历下区文化东路123号', emergencyContact: '王小明', createTime: '2026-10-20 09:00' },
  { id: 2, patientNo: 'P20261011002', name: '林诗涵', gender: '女', age: 58, idCard: '370102196805052345', phone: '139****1234', bloodType: 'B', address: '山东省济南市市中区经七路45号', emergencyContact: '李华', createTime: '2026-10-21 10:15' },
  { id: 3, patientNo: 'P20261012003', name: '李浩然', gender: '男', age: 35, idCard: '370102199103033456', phone: '136****5678', bloodType: 'O', address: '山东省济南市槐荫区经十路67号', emergencyContact: '张丽', createTime: '2026-10-22 14:20' },
  { id: 4, patientNo: 'P20261012004', name: '王雨桐', gender: '女', age: 28, idCard: '370102199806064567', phone: '137****9012', bloodType: 'AB', address: '山东省济南市天桥区北园大街89号', emergencyContact: '陈强', createTime: '2026-10-22 16:45' },
  { id: 5, patientNo: 'P20261013005', name: '王梓涵', gender: '男', age: 67, idCard: '370102195907075678', phone: '135****3456', bloodType: 'A', address: '山东省济南市历城区花园路12号', emergencyContact: '刘强', createTime: '2026-10-23 08:10' },
  { id: 6, patientNo: 'P20261013006', name: '李思琪', gender: '女', age: 5, idCard: '370102202108086789', phone: '133****7890', bloodType: 'O', address: '山东省济南市长清区大学路34号', emergencyContact: '王磊', createTime: '2026-10-23 11:30' },
  { id: 7, patientNo: 'P20261014007', name: '刘宇航', gender: '男', age: 45, idCard: '370102198109097890', phone: '131****2345', bloodType: 'B', address: '山东省济南市章丘区明水大街56号', emergencyContact: '赵敏', createTime: '2026-10-24 09:00' },
  { id: 8, patientNo: 'P20261014008', name: '张梦瑶', gender: '女', age: 52, idCard: '370102197410108901', phone: '159****6789', bloodType: 'A', address: '山东省济南市济阳区正安路78号', emergencyContact: '孙伟', createTime: '2026-10-24 15:20' },
  { id: 9, patientNo: 'P20261015009', name: '陈俊熙', gender: '男', age: 48, idCard: '370102197811119012', phone: '187****1234', bloodType: 'O', address: '山东省济南市莱芜区汶河大道90号', emergencyContact: '周静', createTime: '2026-10-25 08:45' },
  { id: 10, patientNo: 'P20261015010', name: '刘雅婷', gender: '女', age: 71, idCard: '370102195512120123', phone: '186****5678', bloodType: 'AB', address: '山东省济南市平阴县榆山路12号', emergencyContact: '吴刚', createTime: '2026-10-25 10:00' },
  { id: 11, patientNo: 'P20261016011', name: '赵一鸣', gender: '男', age: 40, idCard: '370102198603031234', phone: '132****9012', bloodType: 'A', address: '山东省济南市商河县商中路34号', emergencyContact: '郑洁', createTime: '2026-10-26 09:15' },
  { id: 12, patientNo: 'P20261016012', name: '赵若曦', gender: '女', age: 38, idCard: '370102198804042345', phone: '180****3456', bloodType: 'B', address: '山东省济南市济阳区富强街56号', emergencyContact: '黄磊', createTime: '2026-10-26 14:30' }
])

const query = reactive({
  keyword: '',
  gender: '',
  pageNum: 1,
  pageSize: 10
})

const filteredList = computed(() => {
  return allData.value.filter(item => {
    const matchKeyword = !query.keyword ||
      item.name.includes(query.keyword) ||
      item.idCard.includes(query.keyword) ||
      item.phone.includes(query.keyword)
    const matchGender = !query.gender || item.gender === query.gender
    return matchKeyword && matchGender
  })
})

const pagedList = computed(() => {
  const start = (query.pageNum - 1) * query.pageSize
  return filteredList.value.slice(start, start + query.pageSize)
})

function handleSearch() {
  query.pageNum = 1
}

function handleReset() {
  query.keyword = ''
  query.gender = ''
  handleSearch()
}

function handleAdd() {
  ElMessage.info('新增患者功能开发中')
}

function handleOcr() {
  router.push('/ai/ocr')
}

function handleView(row) {
  ElMessage.info(`查看患者 ${row.name} 的详情`)
}

function handleEdit(row) {
  ElMessage.info(`编辑患者 ${row.name} 的信息`)
}

function handleAdmit(row) {
  ElMessage.success(`已选择患者 ${row.name}，跳转入院登记`)
  router.push('/patient/admit')
}

function handleDelete(row) {
  ElMessageBox.confirm(`确认删除患者 ${row.name}？`, '提示', { type: 'warning' })
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
