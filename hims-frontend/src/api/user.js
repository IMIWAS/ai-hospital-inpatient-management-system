import request from '@/utils/request'

/**
 * 用户相关API
 */
export const userApi = {
  // 登录
  login: (userName, password) => request({
    url: '/api/user/login',
    method: 'post',
    data: { userName, password }
  }),

  // 获取用户信息
  getUserInfo: (userId) => request({
    url: '/api/user/info',
    method: 'get',
    params: { userId }
  }),

  // 分页查询用户列表
  getUserList: (params) => request({
    url: '/api/user/list',
    method: 'get',
    params
  }),

  // 获取所有用户
  getAllUsers: () => request({
    url: '/api/user/all',
    method: 'get'
  }),

  // 根据科室查询用户
  getUsersByDepartment: (department) => request({
    url: '/api/user/by-department',
    method: 'get',
    params: { department }
  }),

  // 创建用户
  createUser: (data) => request({
    url: '/api/user/create',
    method: 'post',
    data
  }),

  // 更新用户
  updateUser: (data) => request({
    url: '/api/user/update',
    method: 'post',
    data
  }),

  // 修改密码
  updatePassword: (data) => request({
    url: '/api/user/password',
    method: 'post',
    data
  }),

  // 重置密码
  resetPassword: (userId, newPassword) => request({
    url: '/api/user/reset-password',
    method: 'post',
    data: { userId, newPassword }
  }),

  // 删除用户
  deleteUser: (userId) => request({
    url: '/api/user/delete',
    method: 'post',
    params: { userId }
  })
}

export default userApi
