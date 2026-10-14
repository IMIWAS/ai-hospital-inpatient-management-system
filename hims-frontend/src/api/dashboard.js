import request from '@/utils/request'

/**
 * 数据大屏相关API
 */
export const dashboardApi = {
  // 首页统计
  getHomeStats: () => request({
    url: '/api/dashboard/home',
    method: 'get'
  }),

  // 在院患者统计
  getInhospitalStats: (department) => request({
    url: '/api/dashboard/inhospital',
    method: 'get',
    params: { department }
  }),

  // 床位统计
  getBedStats: () => request({
    url: '/api/dashboard/bed',
    method: 'get'
  }),

  // 科室住院统计
  getDeptStats: () => request({
    url: '/api/dashboard/dept',
    method: 'get'
  }),

  // 费用统计
  getCostStats: () => request({
    url: '/api/dashboard/cost',
    method: 'get'
  }),

  // 高危患者列表
  getHighRisk: () => request({
    url: '/api/dashboard/high-risk',
    method: 'get'
  }),

  // 体征异常统计
  getAbnormalStats: (params) => request({
    url: '/api/dashboard/vital/abnormal',
    method: 'get',
    params
  }),

  // 随访统计
  getFollowStats: () => request({
    url: '/api/dashboard/follow',
    method: 'get'
  }),

  // 失联随访列表
  getLostFollows: () => request({
    url: '/api/dashboard/follow/lost',
    method: 'get'
  })
}

export default dashboardApi
