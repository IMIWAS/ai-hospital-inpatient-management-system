<template>
  <div class="page-container">
    <div class="page-card">
      <el-tabs v-model="activeTab" class="ocr-tabs">
        <!-- 身份证识别 -->
        <el-tab-pane label="身份证识别" name="idcard">
          <div class="ocr-area">
            <div class="upload-box" @click="handleUpload">
              <el-icon class="upload-icon"><UploadFilled /></el-icon>
              <p class="upload-text">点击或拖拽身份证照片到此处</p>
              <p class="upload-hint">支持 JPG / PNG 格式，建议上传清晰的证件照</p>
            </div>
            <div class="mock-btn-wrap">
              <el-button type="primary" plain @click="handleUpload">
                <el-icon style="margin-right: 4px"><MagicStick /></el-icon>模拟识别（演示用）
              </el-button>
            </div>
            <div class="result-box" v-if="idResult">
              <div class="result-title">
                <el-icon><CircleCheck /></el-icon>
                识别结果（AI OCR）
              </div>
              <el-descriptions :column="2" border>
                <el-descriptions-item label="姓名">{{ idResult.name }}</el-descriptions-item>
                <el-descriptions-item label="性别">{{ idResult.gender }}</el-descriptions-item>
                <el-descriptions-item label="民族">{{ idResult.nation }}</el-descriptions-item>
                <el-descriptions-item label="出生日期">{{ idResult.birthDate }}</el-descriptions-item>
                <el-descriptions-item label="身份证号" :span="2">{{ idResult.idCard }}</el-descriptions-item>
                <el-descriptions-item label="住址" :span="2">{{ idResult.address }}</el-descriptions-item>
              </el-descriptions>
              <div class="result-actions">
                <el-button type="primary" @click="confirmId">确认建档</el-button>
                <el-button @click="idResult = null">重新识别</el-button>
              </div>
            </div>
          </div>
        </el-tab-pane>

        <!-- 医疗单据识别 -->
        <el-tab-pane label="医疗单据识别" name="medical">
          <div class="ocr-area">
            <div class="upload-box" @click="handleUploadMedical">
              <el-icon class="upload-icon"><UploadFilled /></el-icon>
              <p class="upload-text">点击或拖拽病历/检查单到此处</p>
              <p class="upload-hint">支持病历首页、检验报告、影像报告等医疗单据</p>
            </div>
            <div class="mock-btn-wrap">
              <el-button type="primary" plain @click="handleUploadMedical">
                <el-icon style="margin-right: 4px"><MagicStick /></el-icon>模拟识别（演示用）
              </el-button>
            </div>
            <div class="result-box" v-if="medicalResult">
              <div class="result-title">
                <el-icon><CircleCheck /></el-icon>
                识别结果（AI OCR + 结构化提取）
              </div>
              <el-descriptions :column="2" border>
                <el-descriptions-item label="患者姓名">{{ medicalResult.patientName }}</el-descriptions-item>
                <el-descriptions-item label="性别">{{ medicalResult.gender }}</el-descriptions-item>
                <el-descriptions-item label="年龄">{{ medicalResult.age }}岁</el-descriptions-item>
                <el-descriptions-item label="科室">{{ medicalResult.department }}</el-descriptions-item>
                <el-descriptions-item label="诊断" :span="2">{{ medicalResult.diagnosis }}</el-descriptions-item>
                <el-descriptions-item label="检验项目" :span="2">{{ medicalResult.testItems }}</el-descriptions-item>
                <el-descriptions-item label="检验结论" :span="2">{{ medicalResult.conclusion }}</el-descriptions-item>
              </el-descriptions>
              <div class="result-actions">
                <el-button type="primary" @click="confirmMedical">导入患者档案</el-button>
                <el-button @click="medicalResult = null">重新识别</el-button>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { UploadFilled, CircleCheck, MagicStick } from '@element-plus/icons-vue'

const activeTab = ref('idcard')
const idResult = ref(null)
const medicalResult = ref(null)

function handleUpload() {
  ElMessage.info('正在调用豆包 AI 视觉模型识别...')
  setTimeout(() => {
    idResult.value = {
      name: '孙浩然',
      gender: '男',
      nation: '汉',
      birthDate: '1990-05-15',
      idCard: '370102199005151234',
      address: '山东省济南市历下区文化东路123号'
    }
    ElMessage.success('识别成功')
  }, 1200)
}

function handleUploadMedical() {
  ElMessage.info('正在调用豆包 AI 视觉模型识别并结构化提取...')
  setTimeout(() => {
    medicalResult.value = {
      patientName: '周梦琪',
      gender: '女',
      age: '45',
      department: '内科',
      diagnosis: '2型糖尿病',
      testItems: '空腹血糖 8.5mmol/L；糖化血红蛋白 7.2%；总胆固醇 6.1mmol/L',
      conclusion: '血糖控制不佳，建议调整降糖方案，定期复查'
    }
    ElMessage.success('识别成功')
  }, 1500)
}

function confirmId() {
  ElMessage.success('已根据识别结果创建患者档案')
}

function confirmMedical() {
  ElMessage.success('检验数据已导入患者档案')
}
</script>

<style lang="scss" scoped>
.ocr-tabs {
  :deep(.el-tabs__item) {
    font-size: 16px;
  }
}

.ocr-area {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
  margin-top: 16px;
}

.upload-box {
  border: 2px dashed #d9d9df;
  border-radius: 12px;
  padding: 60px 40px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  background: #fafafc;

  &:hover {
    border-color: #0d9488;
    background: #f0fdfa;
  }

  .upload-icon {
    font-size: 48px;
    color: #0d9488;
    margin-bottom: 12px;
  }

  .upload-text {
    font-size: 15px;
    color: #1d1d1f;
    margin-bottom: 6px;
  }

  .upload-hint {
    font-size: 12px;
    color: #86868b;
  }
}

.mock-btn-wrap {
  display: flex;
  justify-content: center;
  margin-top: 12px;
}

.result-box {
  border: 1px solid #d9d9df;
  border-radius: 12px;
  padding: 20px;
  background: #fff;

  .result-title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 16px;
    font-weight: 600;
    color: #0d9488;
    margin-bottom: 16px;
  }

  .result-actions {
    margin-top: 20px;
    display: flex;
    gap: 12px;
    justify-content: flex-end;
  }
}
</style>
