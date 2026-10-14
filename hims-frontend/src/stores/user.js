import { defineStore } from 'pinia'
import { userApi } from '@/api'

/**
 * 用户状态管理
 */
export const useUserStore = defineStore('user', {
  state: () => ({
    // 用户信息
    userInfo: null,
    // Token
    token: '',
    // 角色
    role: '',
    // 权限列表
    permissions: []
  }),

  getters: {
    // 是否登录
    isLoggedIn: (state) => !!state.userInfo,
    // 用户ID
    userId: (state) => state.userInfo?.userId || null,
    // 用户名
    userName: (state) => state.userInfo?.userName || '',
    // 真实姓名
    realName: (state) => state.userInfo?.realName || '',
    // 角色名称
    roleName: (state) => state.userInfo?.roleName || '',
    // 角色类型
    roleType: (state) => state.userInfo?.roleType || null,
    // 是否管理员
    isAdmin: (state) => state.userInfo?.roleType === 1,
    // 是否医生
    isDoctor: (state) => state.userInfo?.roleType === 2,
    // 是否护士
    isNurse: (state) => state.userInfo?.roleType === 3
  },

  actions: {
    /**
     * 初始化用户信息（从 localStorage 恢复）
     */
    initUser() {
      const saved = localStorage.getItem('hims_user')
      if (saved) {
        try {
          this.userInfo = JSON.parse(saved)
          this.token = this.userInfo.token || ''
          this.role = this.userInfo.roleName || ''
        } catch (e) {
          console.error('解析用户信息失败:', e)
          this.logout()
        }
      }
    },

    /**
     * 登录
     */
    async login(userName, password) {
      const data = await userApi.login(userName, password)
      this.userInfo = data
      this.token = data.token || ''
      this.role = data.roleName || ''
      // 保存到 localStorage
      localStorage.setItem('hims_user', JSON.stringify(data))
      return data
    },

    /**
     * 登出
     */
    logout() {
      this.userInfo = null
      this.token = ''
      this.role = ''
      this.permissions = []
      localStorage.removeItem('hims_user')
    },

    /**
     * 刷新用户信息
     */
    async refreshUserInfo() {
      if (!this.userId) return
      try {
        const data = await userApi.getUserInfo(this.userId)
        this.userInfo = { ...this.userInfo, ...data }
        localStorage.setItem('hims_user', JSON.stringify(this.userInfo))
      } catch (e) {
        console.error('刷新用户信息失败:', e)
      }
    },

    /**
     * 检查是否有权限
     */
    hasPermission(permission) {
      if (this.isAdmin) return true
      return this.permissions.includes(permission)
    }
  }
})
