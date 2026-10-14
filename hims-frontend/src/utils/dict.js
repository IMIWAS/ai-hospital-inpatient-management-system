/**
 * 字典常量
 */

// 角色字典
export const ROLE_TYPE = {
  1: '管理员',
  2: '医生',
  3: '护士',
  4: '行政',
  5: '医技'
}

// 性别字典
export const GENDER = {
  '男': '男',
  '女': '女'
}

// 住院状态字典
export const INHOSPITAL_STATUS = {
  1: '在院',
  2: '已出院',
  3: '转科',
  4: '请假'
}

export const INHOSPITAL_STATUS_OPTIONS = [
  { value: 1, label: '在院', type: 'primary' },
  { value: 2, label: '已出院', type: 'info' },
  { value: 3, label: '转科', type: 'warning' },
  { value: 4, label: '请假', type: 'danger' }
]

// 床位状态字典
export const BED_STATUS = {
  0: '空床',
  1: '占用',
  2: '维修',
  3: '预留'
}

export const BED_STATUS_OPTIONS = [
  { value: 0, label: '空床', type: 'success' },
  { value: 1, label: '占用', type: 'primary' },
  { value: 2, label: '维修', type: 'warning' },
  { value: 3, label: '预留', type: 'info' }
]

// 风险等级字典
export const RISK_LEVEL = {
  1: '低危',
  2: '中危',
  3: '高危'
}

export const RISK_LEVEL_OPTIONS = [
  { value: 1, label: '低危', type: 'success', class: 'risk-low' },
  { value: 2, label: '中危', type: 'warning', class: 'risk-medium' },
  { value: 3, label: '高危', type: 'danger', class: 'risk-high' }
]

// 医嘱执行状态字典
export const DRUG_EXECUTE_STATUS = {
  0: '未执行',
  1: '已执行',
  2: '暂停',
  3: '完成'
}

export const DRUG_EXECUTE_STATUS_OPTIONS = [
  { value: 0, label: '未执行', type: 'warning' },
  { value: 1, label: '已执行', type: 'success' },
  { value: 2, label: '暂停', type: 'info' },
  { value: 3, label: '完成', type: 'primary' }
]

// 病程记录类型字典
export const COURSE_RECORD_TYPE = {
  '日常病程': '日常病程',
  '查房记录': '查房记录',
  '抢救记录': '抢救记录',
  '手术记录': '手术记录',
  '出院记录': '出院记录'
}

// 随访状态字典
export const FOLLOW_STATUS = {
  0: '待随访',
  1: '已完成',
  2: '失联',
  3: '取消'
}

export const FOLLOW_STATUS_OPTIONS = [
  { value: 0, label: '待随访', type: 'warning' },
  { value: 1, label: '已完成', type: 'success' },
  { value: 2, label: '失联', type: 'danger' },
  { value: 3, label: '取消', type: 'info' }
]

// 随访类型字典
export const FOLLOW_TYPE = {
  '常规随访': '常规随访',
  '术后随访': '术后随访',
  '慢病随访': '慢病随访',
  '康复随访': '康复随访'
}

// 随访方式字典
export const FOLLOW_WAY = {
  '电话随访': '电话随访',
  '门诊随访': '门诊随访',
  '上门随访': '上门随访',
  '视频随访': '视频随访'
}

// OCR 识别类型字典
export const OCR_TYPE = {
  '身份证': '身份证',
  '就诊卡': '就诊卡',
  '医保卡': '医保卡',
  '检查报告': '检查报告',
  '病历首页': '病历首页'
}

// OCR 状态字典
export const OCR_STATUS = {
  0: '待处理',
  1: '已识别',
  2: '已建档',
  3: '识别失败'
}

export const OCR_STATUS_OPTIONS = [
  { value: 0, label: '待处理', type: 'info' },
  { value: 1, label: '已识别', type: 'primary' },
  { value: 2, label: '已建档', type: 'success' },
  { value: 3, label: '识别失败', type: 'danger' }
]

// 血型字典
export const BLOOD_TYPE = ['A', 'B', 'AB', 'O', 'A型', 'B型', 'AB型', 'O型', '未知']

// 婚姻状况字典
export const MARITAL_STATUS = ['未婚', '已婚', '离异', '丧偶']

// 账号状态字典
export const USER_STATUS = {
  0: '禁用',
  1: '正常'
}

// 给药途径字典
export const DRUG_ROUTE = ['口服', '静脉注射', '肌肉注射', '皮下注射', '外用']

// 用药频次字典
export const DRUG_FREQUENCY = ['每日1次', '每日2次', '每日3次', '每日4次', '睡前服用', '必要时服用']

// 床位类型字典
export const BED_TYPE = ['普通', 'ICU', '单人间', '双人间']

// 结算状态字典
export const SETTLEMENT_STATUS = {
  0: '未结算',
  1: '已结算'
}

export default {
  ROLE_TYPE,
  GENDER,
  INHOSPITAL_STATUS,
  INHOSPITAL_STATUS_OPTIONS,
  BED_STATUS,
  BED_STATUS_OPTIONS,
  RISK_LEVEL,
  RISK_LEVEL_OPTIONS,
  DRUG_EXECUTE_STATUS,
  DRUG_EXECUTE_STATUS_OPTIONS,
  COURSE_RECORD_TYPE,
  FOLLOW_STATUS,
  FOLLOW_STATUS_OPTIONS,
  FOLLOW_TYPE,
  FOLLOW_WAY,
  OCR_TYPE,
  OCR_STATUS,
  OCR_STATUS_OPTIONS,
  BLOOD_TYPE,
  MARITAL_STATUS,
  USER_STATUS,
  DRUG_ROUTE,
  DRUG_FREQUENCY,
  BED_TYPE,
  SETTLEMENT_STATUS
}
