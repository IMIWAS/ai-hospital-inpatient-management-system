<template>
  <el-dialog
    v-model="visible"
    :title="isEdit ? '编辑用户' : '新增用户'"
    width="600px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" v-loading="loading">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="form.userName" :disabled="isEdit" placeholder="登录用户名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名" prop="realName">
            <el-input v-model="form.realName" placeholder="真实姓名" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="角色" prop="roleType">
            <el-select v-model="form.roleType" placeholder="请选择" style="width: 100%">
              <el-option v-for="(name, key) in ROLE_TYPE" :key="key" :label="name" :value="Number(key)" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="科室" prop="department">
            <el-select v-model="form.department" placeholder="请选择" style="width: 100%">
              <el-option label="内科" value="内科" />
              <el-option label="外科" value="外科" />
              <el-option label="ICU" value="ICU" />
              <el-option label="妇产科" value="妇产科" />
              <el-option label="儿科" value="儿科" />
              <el-option label="急诊科" value="急诊科" />
              <el-option label="行政" value="行政" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职称" prop="title">
            <el-input v-model="form.title" placeholder="如：主任医师" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio :value="1">正常</el-radio>
              <el-radio :value="0">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" maxlength="11" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item v-if="!isEdit" label="初始密码" prop="password">
        <el-input v-model="form.password" placeholder="留空将使用默认密码 123456" />
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" :rows="2" />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="loading" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { userApi } from '@/api'
import { ROLE_TYPE } from '@/utils/dict'

const props = defineProps({
  modelValue: Boolean,
  data: Object
})

const emit = defineEmits(['update:modelValue', 'success'])

const visible = ref(props.modelValue)
const loading = ref(false)
const formRef = ref(null)

const isEdit = computed(() => !!props.data?.userId)

const form = reactive({
  userId: null,
  userName: '',
  realName: '',
  password: '',
  roleType: 2,
  department: '',
  title: '',
  phone: '',
  email: '',
  status: 1,
  remark: ''
})

const rules = {
  userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  roleType: [{ required: true, message: '请选择角色', trigger: 'change' }],
  phone: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }],
  email: [{ type: 'email', message: '邮箱格式不正确', trigger: 'blur' }]
}

watch(() => props.modelValue, (val) => {
  visible.value = val
  if (val) {
    if (props.data && props.data.userId) {
      Object.assign(form, props.data)
      form.password = ''
    } else {
      Object.assign(form, {
        userId: null,
        userName: '',
        realName: '',
        password: '',
        roleType: 2,
        department: '',
        title: '',
        phone: '',
        email: '',
        status: 1,
        remark: ''
      })
    }
  }
})

watch(visible, (val) => emit('update:modelValue', val))

async function handleSubmit() {
  await formRef.value.validate()
  loading.value = true
  try {
    if (isEdit.value) {
      await userApi.updateUser(form)
      ElMessage.success('更新成功')
    } else {
      await userApi.createUser(form)
      ElMessage.success('创建成功')
    }
    visible.value = false
    emit('success')
  } finally {
    loading.value = false
  }
}

function handleClose() {
  formRef.value?.resetFields()
}
</script>
