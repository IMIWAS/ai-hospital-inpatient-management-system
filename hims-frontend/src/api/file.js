import request from '@/utils/request'

/**
 * 文件上传API
 */
export const fileApi = {
  // 上传文件
  upload: (formData) => request({
    url: '/api/file/upload',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  }),

  // 上传图片
  uploadImage: (formData) => request({
    url: '/api/file/upload/image',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  }),

  // 批量上传
  uploadBatch: (formData) => request({
    url: '/api/file/upload/batch',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

export default fileApi
