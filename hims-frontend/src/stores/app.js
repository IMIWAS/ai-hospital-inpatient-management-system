import { defineStore } from 'pinia'

/**
 * 应用全局状态
 */
export const useAppStore = defineStore('app', {
  state: () => ({
    // 侧边栏是否折叠
    sidebarCollapsed: false,
    // 当前激活的菜单
    activeMenu: '',
    // 面包屑
    breadcrumb: []
  }),

  actions: {
    /**
     * 切换侧边栏
     */
    toggleSidebar() {
      this.sidebarCollapsed = !this.sidebarCollapsed
    },

    /**
     * 设置激活菜单
     */
    setActiveMenu(menu) {
      this.activeMenu = menu
    },

    /**
     * 设置面包屑
     */
    setBreadcrumb(breadcrumb) {
      this.breadcrumb = breadcrumb
    }
  }
})
