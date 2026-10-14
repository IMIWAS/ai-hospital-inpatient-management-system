import request from '@/utils/request'

/**
 * 床位相关API
 */
export const bedApi = {
    // 分页查询
    getList: (params) => request({
        url: '/api/bed/list',
        method: 'get',
        params
    }),

    // 查询详情
    getById: (bedId) => request({
        url: '/api/bed/info',
        method: 'get',
        params: { bedId }
    }),

    // 查询空床
    getEmptyBeds: (department, bedType) => request({
        url: '/api/bed/empty',
        method: 'get',
        params: { department, bedType }
    }),

    // 智能分配床位
    allocate: (data) => request({
        url: '/api/bed/allocate',
        method: 'post',
        data
    }),

    // 释放床位
    release: (bedId) => request({
        url: '/api/bed/release',
        method: 'post',
        params: { bedId }
    }),

    // 创建床位
    create: (data) => request({
        url: '/api/bed/create',
        method: 'post',
        data
    }),

    // 更新床位
    update: (data) => request({
        url: '/api/bed/update',
        method: 'post',
        data
    }),

    // 删除床位
    delete: (bedId) => request({
        url: '/api/bed/delete',
        method: 'post',
        params: { bedId }
    })
}

export default bedApi
