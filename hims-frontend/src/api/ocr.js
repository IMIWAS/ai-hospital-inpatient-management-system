import request from '@/utils/request'

/**
 * AI OCR 相关API
 */
export const ocrApi = {
  // OCR识别身份证
  recognizeIdCard: (formData) => request({
    url: '/api/ai/ocr/id-card',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  }),

  // OCR识别医疗单据
  recognizeMedical: (formData) => request({
    url: '/api/ai/ocr/medical',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  }),

  // 获取识别记录详情
  getRecord: (ocrId) => request({
    url: '/api/ai/ocr/record',
    method: 'get',
    params: { ocrId }
  }),

  // 根据患者ID查询识别记录
  getRecordsByPatient: (patientId) => request({
    url: '/api/ai/ocr/records/patient',
    method: 'get',
    params: { patientId }
  }),

  // 分页查询识别记录
  getRecords: (params) => request({
    url: '/api/ai/ocr/records',
    method: 'get',
    params
  }),

  // 更新识别记录状态
  updateStatus: (ocrId, status, patientId) => request({
    url: '/api/ai/ocr/status',
    method: 'post',
    params: { ocrId, status, patientId }
  }),

  // 删除识别记录
  deleteRecord: (ocrId) => request({
    url: '/api/ai/ocr/delete',
    method: 'post',
    params: { ocrId }
  })
}

export default ocrApi
