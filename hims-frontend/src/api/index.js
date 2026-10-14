/**
 * 统一导出所有 API
 */
import { userApi } from './user'
import { patientApi } from './patient'
import { inhospitalApi } from './inhospital'
import { bedApi } from './bed'
import { vitalApi } from './vital'
import { ocrApi } from './ocr'
import { courseApi } from './course'
import { drugApi } from './drug'
import { followApi } from './follow'
import { dashboardApi } from './dashboard'
import { fileApi } from './file'

// 命名导出，供 `import { userApi } from '@/api'` 使用
export {
  userApi,
  patientApi,
  inhospitalApi,
  bedApi,
  vitalApi,
  ocrApi,
  courseApi,
  drugApi,
  followApi,
  dashboardApi,
  fileApi
}

// 默认导出，挂在全局时也方便
export default {
  userApi,
  patientApi,
  inhospitalApi,
  bedApi,
  vitalApi,
  ocrApi,
  courseApi,
  drugApi,
  followApi,
  dashboardApi,
  fileApi
}
