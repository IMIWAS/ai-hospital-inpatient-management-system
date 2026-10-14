import request from '@/utils/request'

/**
 * 用药医嘱相关API
 */
export const drugApi = {
  // 分页查询
  getList: (params) => request({
    url: '/api/drug/list',
    method: 'get',
    params
  }),

  // 查询详情
  getById: (drugId) => request({
    url: '/api/drug/info',
    method: 'get',
    params: { drugId }
  }),

  // 根据患者ID查询
  getByPatient: (patientId) => request({
    url: '/api/drug/by-patient',
    method: 'get',
    params: { patientId }
  }),

  // 根据住院ID查询
  getByInhospital: (inhospitalId) => request({
    url: '/api/drug/by-inhospital',
    method: 'get',
    params: { inhospitalId }
  }),

  // 查询患者待执行医嘱
  getPendingDrugs: (patientId) => request({
    url: '/api/drug/pending',
    method: 'get',
    params: { patientId }
  }),

  // 创建医嘱
  create: (data) => request({
    url: '/api/drug/create',
    method: 'post',
    data
  }),

  // 更新医嘱
  update: (data) => request({
    url: '/api/drug/update',
    method: 'post',
    data
  }),

  // 执行医嘱
  execute: (drugId, executeUserId) => request({
    url: '/api/drug/execute',
    method: 'post',
    params: { drugId, executeUserId }
  }),

  // 暂停医嘱
  pause: (drugId) => request({
    url: '/api/drug/pause',
    method: 'post',
    params: { drugId }
  }),

  // 删除医嘱
  delete: (drugId) => request({
    url: '/api/drug/delete',
    method: 'post',
    params: { drugId }
  }),

  // 获取药品使用统计
  getStats: (params) => request({
    url: '/api/drug/stats',
    method: 'get',
    params
  })
}

export default drugApi
