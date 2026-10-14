import request from '@/utils/request'

/**
 * 患者相关API
 */
export const patientApi = {
  // 分页查询患者列表
  getPatientList: (params) => request({
    url: '/api/patient/list',
    method: 'get',
    params
  }),

  // 查询患者详情
  getPatientById: (patientId) => request({
    url: '/api/patient/info',
    method: 'get',
    params: { patientId }
  }),

  // 根据身份证号查询
  getPatientByIdCard: (idCard) => request({
    url: '/api/patient/by-idcard',
    method: 'get',
    params: { idCard }
  }),

  // 根据住院号查询
  getPatientByInhospitalNo: (inhospitalNo) => request({
    url: '/api/patient/by-inhospital-no',
    method: 'get',
    params: { inhospitalNo }
  }),

  // 创建患者
  createPatient: (data, operatorId) => request({
    url: '/api/patient/create',
    method: 'post',
    data,
    params: { operatorId }
  }),

  // 更新患者信息
  updatePatient: (data) => request({
    url: '/api/patient/update',
    method: 'post',
    data
  }),

  // 删除患者
  deletePatient: (patientId) => request({
    url: '/api/patient/delete',
    method: 'post',
    params: { patientId }
  })
}

export default patientApi
