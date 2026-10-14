import request from '@/utils/request'

/**
 * 生命体征相关API
 */
export const vitalApi = {
    // 分页查询
    getList: (params) => request({
        url: '/api/vital/list',
        method: 'get',
        params
    }),

    // 查询详情
    getById: (vitalId) => request({
        url: '/api/vital/info',
        method: 'get',
        params: { vitalId }
    }),

    // 获取患者最新体征
    getLatest: (patientId) => request({
        url: '/api/vital/latest',
        method: 'get',
        params: { patientId }
    }),

    // 获取患者体征历史
    getHistory: (patientId, limit) => request({
        url: '/api/vital/history',
        method: 'get',
        params: { patientId, limit }
    }),

    // 记录生命体征（包含AI风险评估）
    record: (data, recordUserId) => request({
        url: '/api/vital/record',
        method: 'post',
        data,
        params: { recordUserId }
    }),

    // 更新体征
    update: (data) => request({
        url: '/api/vital/update',
        method: 'post',
        data
    }),

    // 删除
    delete: (vitalId) => request({
        url: '/api/vital/delete',
        method: 'post',
        params: { vitalId }
    }),

    // 获取高危患者列表
    getHighRisk: () => request({
        url: '/api/vital/high-risk',
        method: 'get'
    }),

    // AI风险评估
    aiRiskAssessment: (data) => request({
        url: '/api/vital/risk-assessment',
        method: 'post',
        data
    })
}

export default vitalApi
