<template>
  <div class="page-container">
    <div class="page-card">
      <el-form :model="query" inline label-width="80px" class="search-form">
        <el-form-item label="关键字">
          <el-input v-model="query.keyword" placeholder="用户名/姓名" clearable style="width: 200px" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="query.roleType" placeholder="全部" clearable style="width: 140px">
            <el-option v-for="t in Object.keys(ROLE_TYPE).filter(k => !isNaN(k))" :key="t" :label="ROLE_TYPE[t]" :value="Number(t)" />
          </el-select>
        </el-form-item>
        <el-form-item label="科室">
          <el-input v-model="query.department" placeholder="科室" clearable style="width: 160px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>查询
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>重置
          </el-button>
        </el-form-item>
      </el-form>

      <div class="toolbar">
        <div>
          <el-button type="primary" @click="openDialog()">
            <el-icon><Plus /></el-icon>新增用户
          </el-button>
        </div>
      </div>

      <el-table
        v-loading="loading"
        :data="list"
        stripe
        border
        style="width: 100%"
        empty-text="暂无用户数据"
      >
        <el-table-column type="index" label="#" width="60" />
        <el-table-column prop="userName" label="用户名" width="120" />
        <el-table-column prop="realName" label="姓名" width="100" />
        <el-table-column prop="roleType" label="角色" width="100">
          <template #default="{ row }">
            <el-tag size="small">{{ ROLE_TYPE[row.roleType] || '-' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="department" label="科室" width="120" />
        <el-table-column prop="title" label="职称" width="100" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
              {{ row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160">
          <template #default="{ row }">{{ formatDate(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="openDialog(row)">
              <el-icon><Edit /></el-icon>编辑
            </el-button>
            <el-button type="warning" link size="small" @click="handleResetPassword(row)">
              <el-icon><Key /></el-icon>重置密码
            </el-button>
            <el-button type="danger" link size="small" @click="handleDelete(row)">
              <el-icon><Delete /></el-icon>删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :page-sizes="[10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top: 20px; justify-content: flex-end;"
        @size-change="loadList"
        @current-change="loadList"
      />
    </div>

    <UserFormDialog v-model="dialogVisible" :data="currentItem" @success="loadList" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { userApi } from '@/api'
import { formatDate } from '@/utils/date'
import { ROLE_TYPE } from '@/utils/dict'
import UserFormDialog from './components/UserFormDialog.vue'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const currentItem = ref({})

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  keyword: '',
  roleType: '',
  department: ''
})

async function loadList() {
  loading.value = true
  try {
    const data = await userApi.getUserList(query)
    list.value = data?.list || []
    total.value = data?.total || 0
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  query.pageNum = 1
  loadList()
}

function handleReset() {
  Object.assign(query, {
    pageNum: 1,
    pageSize: 10,
    keyword: '',
    roleType: '',
    department: ''
  })
  loadList()
}

function openDialog(row) {
  currentItem.value = row ? { ...row } : {}
  dialogVisible.value = true
}

async function handleResetPassword(row) {
  ElMessageBox.prompt('请输入新密码（留空将使用默认密码 123456）', '重置密码', {
    inputValue: '123456',
    inputValidator: (val) => {
      if (!val) return true
      if (val.length < 6) return '密码长度不能少于6位'
      return true
    }
  })
    .then(async ({ value }) => {
      await userApi.resetPassword(row.userId, value || '123456')
      ElMessage.success('密码重置成功')
    })
    .catch(() => {})
}

async function handleDelete(row) {
  ElMessageBox.confirm(`确定要删除用户【${row.realName}】吗？`, '删除确认', { type: 'warning' })
    .then(async () => {
      await userApi.deleteUser(row.userId)
      ElMessage.success('删除成功')
      loadList()
    })
    .catch(() => {})
}

onMounted(() => {
  loadList()
})
</script>

<style lang="scss" scoped>
.search-form {
  margin-bottom: 16px;
}

.toolbar {
  margin-bottom: 16px;
}
</style>
