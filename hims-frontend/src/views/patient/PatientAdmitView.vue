<template>
    <div class="page-container">
        <div class="page-card">
            <el-steps :active="activeStep" finish-status="success" simple style="margin-bottom: 30px;">
                <el-step title="选择患者" />
                <el-step title="分配床位" />
                <el-step title="填写信息" />
                <el-step title="完成入院" />
            </el-steps>

            <!-- 步骤1: 选择患者 -->
            <div v-if="activeStep === 0" class="step-content">
                <h3 class="step-title">第一步：选择患者</h3>
                <el-input v-model="patientSearch" placeholder="输入患者姓名或身份证号搜索" clearable
                    style="width: 400px; margin-bottom: 16px;" @input="searchPatient" />
                <el-table v-loading="searching" :data="patientOptions" highlight-current-row @row-click="selectPatient"
                    max-height="400">
                    <el-table-column prop="patientId" label="患者ID" width="160" />
                    <el-table-column prop="name" label="姓名" width="100" />
                    <el-table-column prop="gender" label="性别" width="60" />
                    <el-table-column prop="age" label="年龄" width="60" />
                    <el-table-column prop="idCard" label="身份证号" />
                    <el-table-column prop="phone" label="电话" width="120" />
                    <el-table-column label="操作" width="100">
                        <template #default="{ row }">
                            <el-button type="primary" link size="small" @click.stop="selectPatient(row)">选择</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <div style="margin-top: 20px; display: flex; flex-direction: column;">
                    <!-- <el-button type="success" :icon="EditPen" plain @click="openAddPatientDialog">
                        手动录入
                    </el-button> -->
                    <el-link type="primary" style="margin-left: 20px;" @click="openAddPatientDialog">
                        <el-icon>
                            <Picture />
                        </el-icon> 手动录入患者信息
                    </el-link>
                    <el-link type="primary" style="margin-left: 20px;" @click="$router.push('/ai/ocr')">
                        <el-icon>
                            <Picture />
                        </el-icon> 没有找到患者？使用 AI OCR 识别建档
                    </el-link>
                </div>
            </div>

            <!-- 手动录入患者弹窗 -->
            <el-dialog v-model="addPatientDialogVisible" title="手动录入患者信息" width="720px" :close-on-click-modal="false"
                @closed="resetAddPatientForm">
                <el-form ref="addPatientFormRef" :model="addPatientForm" :rules="addPatientRules" label-width="100px"
                    label-position="right">
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="姓名" prop="name">
                                <el-input v-model="addPatientForm.name" placeholder="请输入患者姓名" maxlength="50" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="性别" prop="gender">
                                <el-radio-group v-model="addPatientForm.gender">
                                    <el-radio value="男">男</el-radio>
                                    <el-radio value="女">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="年龄" prop="age">
                                <el-input-number v-model="addPatientForm.age" :min="0" :max="150"
                                    controls-position="right" style="width: 100%" placeholder="选填" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="出生日期" prop="birthDate">
                                <el-date-picker v-model="addPatientForm.birthDate" type="date" value-format="YYYY-MM-DD"
                                    style="width: 100%" placeholder="选填" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="身份证号" prop="idCard">
                                <el-input v-model="addPatientForm.idCard" placeholder="请输入身份证号" maxlength="18" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="联系电话" prop="phone">
                                <el-input v-model="addPatientForm.phone" placeholder="请输入联系电话" maxlength="20" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="住址" prop="address">
                        <el-input v-model="addPatientForm.address" placeholder="选填" maxlength="200" />
                    </el-form-item>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="紧急联系人" prop="emergencyContact">
                                <el-input v-model="addPatientForm.emergencyContact" placeholder="选填" maxlength="50" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="紧急电话" prop="emergencyPhone">
                                <el-input v-model="addPatientForm.emergencyPhone" placeholder="选填" maxlength="20" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="血型" prop="bloodType">
                                <el-select v-model="addPatientForm.bloodType" placeholder="选填" clearable
                                    style="width: 100%">
                                    <el-option label="A型" value="A" />
                                    <el-option label="B型" value="B" />
                                    <el-option label="AB型" value="AB" />
                                    <el-option label="O型" value="O" />
                                    <el-option label="未知" value="未知" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="婚姻状况" prop="maritalStatus">
                                <el-select v-model="addPatientForm.maritalStatus" placeholder="选填" clearable
                                    style="width: 100%">
                                    <el-option label="未婚" value="未婚" />
                                    <el-option label="已婚" value="已婚" />
                                    <el-option label="离异" value="离异" />
                                    <el-option label="丧偶" value="丧偶" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="职业" prop="occupation">
                                <el-input v-model="addPatientForm.occupation" placeholder="选填" maxlength="50" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="医保类型" prop="insuranceType">
                                <el-select v-model="addPatientForm.insuranceType" placeholder="选填" clearable
                                    style="width: 100%">
                                    <el-option label="自费" value="自费" />
                                    <el-option label="城镇职工医保" value="城镇职工医保" />
                                    <el-option label="城镇居民医保" value="城镇居民医保" />
                                    <el-option label="新农合" value="新农合" />
                                    <el-option label="商业保险" value="商业保险" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="既往病史" prop="medicalHistory">
                        <el-input v-model="addPatientForm.medicalHistory" type="textarea" :rows="2"
                            placeholder="选填，如：高血压、糖尿病、手术史等" maxlength="500" show-word-limit />
                    </el-form-item>
                    <el-form-item label="过敏信息" prop="allergyInfo">
                        <el-input v-model="addPatientForm.allergyInfo" type="textarea" :rows="2"
                            placeholder="选填，如：青霉素过敏、食物过敏等" maxlength="500" show-word-limit />
                    </el-form-item>
                </el-form>
                <template #footer>
                    <el-button @click="addPatientDialogVisible = false">取消</el-button>
                    <el-button type="primary" :loading="addPatientSubmitting" @click="submitAddPatient">
                        保存并选择
                    </el-button>
                </template>
            </el-dialog>

            <!-- 步骤2: 分配床位 -->
            <div v-if="activeStep === 1" class="step-content">
                <h3 class="step-title">第二步：选择床位</h3>
                <el-form :model="form" label-width="100px" inline>
                    <el-form-item label="科室">
                        <el-select v-model="form.department" placeholder="选择科室" @change="loadEmptyBeds"
                            style="width: 200px">
                            <el-option label="内科" value="内科" />
                            <el-option label="外科" value="外科" />
                            <el-option label="ICU" value="ICU" />
                            <el-option label="妇产科" value="妇产科" />
                            <el-option label="儿科" value="儿科" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="床位类型">
                        <el-select v-model="form.bedType" placeholder="选择类型" @change="loadEmptyBeds"
                            style="width: 160px">
                            <el-option v-for="t in bedTypes" :key="t" :label="t" :value="t" />
                        </el-select>
                    </el-form-item>
                </el-form>

                <el-radio-group v-model="form.bedId" v-loading="loadingBeds" style="width: 100%;">
                    <el-table :data="emptyBeds" stripe border max-height="400">
                        <el-table-column label="选择" width="80">
                            <template #default="{ row }">
                                <el-radio :value="row.bedId" :label="row.bedId">&nbsp;</el-radio>
                            </template>
                        </el-table-column>
                        <el-table-column prop="bedNo" label="床位号" width="100" />
                        <el-table-column prop="roomNo" label="病房号" width="100" />
                        <el-table-column prop="department" label="科室" width="100" />
                        <el-table-column prop="bedType" label="类型" width="100" />
                        <el-table-column prop="bedPrice" label="床位费" width="100">
                            <template #default="{ row }">¥{{ row.bedPrice }}/天</template>
                        </el-table-column>
                        <el-table-column prop="floor" label="楼层" />
                    </el-table>
                </el-radio-group>
            </div>

            <!-- 步骤3: 填写信息 -->
            <div v-if="activeStep === 2" class="step-content">
                <h3 class="step-title">第三步：填写入院信息</h3>
                <el-form :model="form" label-width="100px" v-loading="loading">
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="患者姓名">
                                <el-input v-model="selectedPatient.name" disabled />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="住院号">
                                <el-input :value="form.inhospitalNo || '入院时自动生成'" disabled />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="科室" required>
                                <el-input v-model="form.department" disabled />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="床位">
                                <el-input v-model="selectedBed.bedNo" disabled />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="入院诊断" required>
                        <el-input v-model="form.diagnosis" type="textarea" :rows="2" placeholder="请输入入院诊断" />
                    </el-form-item>
                    <el-form-item label="ICD编码">
                        <el-input v-model="form.diagnosisIcd" placeholder="如：J18.901" />
                    </el-form-item>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="主管医生" required>
                                <el-select v-model="form.doctorId" placeholder="选择医生" filterable style="width: 100%">
                                    <el-option v-for="doc in doctors" :key="doc.userId"
                                        :label="`${doc.realName}（${doc.department}）`" :value="doc.userId" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="责任护士">
                                <el-select v-model="form.nurseId" placeholder="选择护士" filterable style="width: 100%">
                                    <el-option v-for="n in nurses" :key="n.userId"
                                        :label="`${n.realName}（${n.department}）`" :value="n.userId" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="备注">
                        <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="如有特殊情况请备注" />
                    </el-form-item>
                </el-form>
            </div>

            <!-- 步骤4: 完成入院 -->
            <div v-if="activeStep === 3" class="step-content">
                <el-result icon="success" title="入院办理成功">
                    <template #extra>
                        <el-descriptions :column="2" border>
                            <el-descriptions-item label="住院号">{{ result.inhospitalNo }}</el-descriptions-item>
                            <el-descriptions-item label="患者姓名">{{ selectedPatient.name }}</el-descriptions-item>
                            <el-descriptions-item label="科室">{{ form.department }}</el-descriptions-item>
                            <el-descriptions-item label="床位">{{ selectedBed.bedNo }}</el-descriptions-item>
                            <el-descriptions-item label="入院时间" :span="2">{{ formatDate(result.inhospitalTime)
                            }}</el-descriptions-item>
                        </el-descriptions>
                        <div style="margin-top: 20px;">
                            <el-button type="primary" @click="$router.push('/patient')">返回列表</el-button>
                            <el-button @click="reset">继续入院</el-button>
                        </div>
                    </template>
                </el-result>
            </div>

            <!-- 操作按钮 -->
            <div v-if="activeStep !== 3" class="step-actions">
                <el-button v-if="activeStep > 0" @click="prevStep">上一步</el-button>
                <el-button v-if="activeStep === 0" type="primary" :disabled="!selectedPatient" @click="nextStep">
                    下一步：选择床位
                </el-button>
                <el-button v-if="activeStep === 1" type="primary" :disabled="!form.bedId" @click="nextStep">
                    下一步：填写信息
                </el-button>
                <el-button v-if="activeStep === 2" type="primary" :loading="submitting" @click="handleSubmit">
                    提交入院
                </el-button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { EditPen } from '@element-plus/icons-vue'
import { debounce } from '@/utils'
import { patientApi, bedApi, inhospitalApi, userApi } from '@/api'
import { formatDate } from '@/utils/date'
import { BED_TYPE } from '@/utils/dict'

const route = useRoute()

const activeStep = ref(0)
const patientSearch = ref('')
const patientOptions = ref([])
const searching = ref(false)
const selectedPatient = ref(null)
const emptyBeds = ref([])
const loadingBeds = ref(false)
const selectedBed = ref({})
const submitting = ref(false)
const loading = ref(false)
const result = ref({})
const doctors = ref([])
const nurses = ref([])
const bedTypes = BED_TYPE

// 手动录入患者
const addPatientDialogVisible = ref(false)
const addPatientSubmitting = ref(false)
const addPatientFormRef = ref(null)

function buildEmptyAddPatientForm () {
    return {
        name: '',
        gender: '',
        age: null,
        birthDate: null,
        idCard: '',
        phone: '',
        address: '',
        emergencyContact: '',
        emergencyPhone: '',
        bloodType: '',
        maritalStatus: '',
        occupation: '',
        insuranceType: '',
        medicalHistory: '',
        allergyInfo: ''
    }
}

const addPatientForm = reactive(buildEmptyAddPatientForm())

const addPatientRules = {
    name: [{ required: true, message: '请输入患者姓名', trigger: 'blur' }],
    gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
    idCard: [
        {
            pattern: /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/,
            message: '身份证号格式不正确',
            trigger: 'blur'
        }
    ],
    phone: [
        {
            pattern: /^1[3-9]\d{9}$/,
            message: '请输入正确的手机号',
            trigger: 'blur'
        }
    ],
    emergencyPhone: [
        {
            pattern: /(^1[3-9]\d{9}$)|(^\d{3,4}-\d{7,8}$)/,
            message: '请输入正确的电话号码',
            trigger: 'blur'
        }
    ]
}

const form = reactive({
    patientId: '',
    department: '',
    bedId: null,
    diagnosis: '',
    diagnosisIcd: '',
    doctorId: null,
    nurseId: null,
    remark: ''
})

const searchPatient = debounce(async () => {
    if (!patientSearch.value) {
        patientOptions.value = []
        return
    }
    searching.value = true
    try {
        const data = await patientApi.getPatientList({ keyword: patientSearch.value, pageSize: 20 })
        patientOptions.value = data?.list || []
    } finally {
        searching.value = false
    }
}, 300)

function selectPatient (row) {
    selectedPatient.value = row
    form.patientId = row.patientId
}

async function loadEmptyBeds () {
    if (!form.department) {
        emptyBeds.value = []
        return
    }
    loadingBeds.value = true
    try {
        const data = await bedApi.getEmptyBeds(form.department, form.bedType)
        emptyBeds.value = data?.list || []
    } finally {
        loadingBeds.value = false
    }
}

async function loadStaff () {
    try {
        const [docs, nurs] = await Promise.all([
            userApi.getUsersByDepartment(form.department),
            userApi.getUsersByDepartment(form.department)
        ])
        doctors.value = docs.filter(u => u.roleType === 2)
        nurses.value = nurs.filter(u => u.roleType === 3)
    } catch (e) {
        console.error('加载医护人员失败:', e)
    }
}

function nextStep () {
    if (activeStep.value === 1) {
        selectedBed.value = emptyBeds.value.find(b => b.bedId === form.bedId) || {}
        loadStaff()
    }
    activeStep.value++
}

function prevStep () {
    activeStep.value--
}

async function handleSubmit () {
    if (!form.diagnosis) {
        ElMessage.warning('请输入入院诊断')
        return
    }
    if (!form.doctorId) {
        ElMessage.warning('请选择主管医生')
        return
    }
    submitting.value = true
    try {
        const data = await inhospitalApi.admit(form)
        result.value = data
        activeStep.value = 3
        ElMessage.success('入院办理成功')
    } finally {
        submitting.value = false
    }
}

function reset () {
    activeStep.value = 0
    selectedPatient.value = null
    selectedBed.value = {}
    patientSearch.value = ''
    Object.assign(form, {
        patientId: '',
        department: '',
        bedId: null,
        diagnosis: '',
        diagnosisIcd: '',
        doctorId: null,
        nurseId: null,
        remark: ''
    })
    result.value = {}
}

// ===== 手动录入患者 =====
function openAddPatientDialog () {
    addPatientDialogVisible.value = true
}

function resetAddPatientForm () {
    Object.assign(addPatientForm, buildEmptyAddPatientForm())
    addPatientFormRef.value?.clearValidate()
}

function submitAddPatient () {
    addPatientFormRef.value?.validate(async (valid) => {
        if (!valid) return
        addPatientSubmitting.value = true
        try {
            // 优先从登录态获取当前操作员 ID
            let operatorId
            const raw = localStorage.getItem('hims_user')
            if (raw) {
                try {
                    const u = JSON.parse(raw)
                    operatorId = u?.userId || u?.id
                } catch (e) { /* ignore */ }
            }
            const newPatient = await patientApi.createPatient(addPatientForm, operatorId)
            ElMessage.success('患者建档成功')
            addPatientDialogVisible.value = false
            // 自动选中新患者，跳到下一步
            if (newPatient) {
                selectedPatient.value = newPatient
                form.patientId = newPatient.patientId
                activeStep.value = 1
            }
        } catch (e) {
            console.error('建档失败:', e)
            ElMessage.error(e?.message || '建档失败')
        } finally {
            addPatientSubmitting.value = false
        }
    })
}

onMounted(async () => {
    // 如果路由带有 patientId，则自动选中
    const preselectedId = route.query.patientId
    if (preselectedId) {
        const data = await patientApi.getPatientById(preselectedId)
        if (data) {
            selectedPatient.value = data
            form.patientId = data.patientId
        }
    }
})
</script>

<style lang="scss" scoped>
.step-title {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
    margin: 0 0 20px 0;
    padding-left: 8px;
    border-left: 3px solid #0d9488;
}

.step-content {
    min-height: 300px;
    padding: 20px 0;
}

.step-actions {
    text-align: center;
    margin-top: 30px;
    padding-top: 20px;
    border-top: 1px solid #ebeef5;

    .el-button {
        margin: 0 8px;
    }
}
</style>
