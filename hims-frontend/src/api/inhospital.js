import request from '@/utils/request'

/**
 * 住院记录相关API
 */
export const inhospitalApi = {
  // 分页查询
  getList: (params) => request({
    url: '/api/inhospital/list',
    method: 'get',
    params
  }),

  // 查询详情
  getById: (inhospitalId) => request({
    url: '/api/inhospital/info',
    method: 'get',
    params: { inhospitalId }
  }),

  // 根据住院号查询
  getByNo: (inhospitalNo) => request({
    url: '/api/inhospital/by-no',
    method: 'get',
    params: { inhospitalNo }
  }),

  // 患者入院
  admit: (data) => request({
    url: '/api/inhospital/admit',
    method: 'post',
    data
  }),

  // 患者转科
  transfer: (data) => request({
    url: '/api/inhospital/transfer',
    method: 'post',
    data
  }),

  // 患者出院
  discharge: (data) => request({
    url: '/api/inhospital/discharge',
    method: 'post',
    data
  }),

  // 患者请假
  leave: (data) => request({
    url: '/api/inhospital/leave',
    method: 'post',
    data
  }),

  // 更新住院记录
  update: (data) => request({
    url: '/api/inhospital/update',
    method: 'post',
    data
  }),

  // 结算
  settle: (inhospitalId) => request({
    url: '/api/inhospital/settle',
    method: 'post',
    params: { inhospitalId }
  }),

  // 删除
  delete: (inhospitalId) => request({
    url: '/api/inhospital/delete',
    method: 'post',
    params: { inhospitalId }
  })
}

export default inhospitalApi
