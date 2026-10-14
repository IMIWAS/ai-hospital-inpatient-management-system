/**
 * 本地存储工具类
 */
const PREFIX = 'hims_'

export const storage = {
  /**
   * 设置存储
   * @param {string} key 键
   * @param {any} value 值
   * @param {number} expire 过期时间（秒），不传则永久
   */
  set(key, value, expire) {
    const data = {
      value,
      expire: expire ? Date.now() + expire * 1000 : null
    }
    localStorage.setItem(PREFIX + key, JSON.stringify(data))
  },

  /**
   * 获取存储
   * @param {string} key 键
   * @returns {any|null}
   */
  get(key) {
    const item = localStorage.getItem(PREFIX + key)
    if (!item) return null

    try {
      const data = JSON.parse(item)
      // 检查是否过期
      if (data.expire && Date.now() > data.expire) {
        localStorage.removeItem(PREFIX + key)
        return null
      }
      return data.value
    } catch (e) {
      return null
    }
  },

  /**
   * 删除存储
   */
  remove(key) {
    localStorage.removeItem(PREFIX + key)
  },

  /**
   * 清空所有以 PREFIX 开头的存储
   */
  clear() {
    Object.keys(localStorage).forEach(key => {
      if (key.startsWith(PREFIX)) {
        localStorage.removeItem(key)
      }
    })
  }
}

export default storage
