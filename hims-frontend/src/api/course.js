import request from '@/utils/request'

/**
 * 病程记录相关API
 */
export const courseApi = {
  // 分页查询
  getList: (params) => request({
    url: '/api/course/list',
    method: 'get',
    params
  }),

  // 查询详情
  getById: (courseId) => request({
    url: '/api/course/info',
    method: 'get',
    params: { courseId }
  }),

  // 根据患者ID查询
  getByPatient: (patientId) => request({
    url: '/api/course/by-patient',
    method: 'get',
    params: { patientId }
  }),

  // 根据住院ID查询
  getByInhospital: (inhospitalId) => request({
    url: '/api/course/by-inhospital',
    method: 'get',
    params: { inhospitalId }
  }),

  // 创建病程记录
  create: (data) => request({
    url: '/api/course/create',
    method: 'post',
    data
  }),

  // 更新病程记录
  update: (data) => request({
    url: '/api/course/update',
    method: 'post',
    data
  }),

  // 删除病程记录
  delete: (courseId) => request({
    url: '/api/course/delete',
    method: 'post',
    params: { courseId }
  }),

  // AI生成病程摘要
  generateSummary: (courseContent) => request({
    url: '/api/course/summary',
    method: 'post',
    data: { courseContent }
  })
}

export default courseApi
