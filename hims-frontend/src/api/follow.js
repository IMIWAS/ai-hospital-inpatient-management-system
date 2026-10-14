import request from '@/utils/request'

/**
 * 出院随访相关API
 */
export const followApi = {
  // 分页查询
  getList: (params) => request({
    url: '/api/follow/list',
    method: 'get',
    params
  }),

  // 查询详情
  getById: (followId) => request({
    url: '/api/follow/info',
    method: 'get',
    params: { followId }
  }),

  // 根据患者ID查询
  getByPatient: (patientId) => request({
    url: '/api/follow/by-patient',
    method: 'get',
    params: { patientId }
  }),

  // 根据住院ID查询
  getByInhospital: (inhospitalId) => request({
    url: '/api/follow/by-inhospital',
    method: 'get',
    params: { inhospitalId }
  }),

  // 查询待随访列表
  getPending: () => request({
    url: '/api/follow/pending',
    method: 'get'
  }),

  // 创建随访记录
  create: (data) => request({
    url: '/api/follow/create',
    method: 'post',
    data
  }),

  // 更新随访记录
  update: (data) => request({
    url: '/api/follow/update',
    method: 'post',
    data
  }),

  // 完成随访
  complete: (followId, data) => request({
    url: '/api/follow/complete',
    method: 'post',
    params: { followId },
    data
  }),

  // 标记失联
  markLost: (followId) => request({
    url: '/api/follow/lost',
    method: 'post',
    params: { followId }
  }),

  // 取消随访
  cancel: (followId) => request({
    url: '/api/follow/cancel',
    method: 'post',
    params: { followId }
  }),

  // 删除随访记录
  delete: (followId) => request({
    url: '/api/follow/delete',
    method: 'post',
    params: { followId }
  }),

  // AI生成随访建议
  generateSuggest: (data) => request({
    url: '/api/follow/suggest',
    method: 'post',
    data
  }),

  // AI自动创建随访计划
  autoCreatePlan: (inhospitalId) => request({
    url: '/api/follow/auto-plan',
    method: 'post',
    params: { inhospitalId }
  })
}

export default followApi
