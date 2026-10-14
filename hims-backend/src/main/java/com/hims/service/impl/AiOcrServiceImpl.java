package com.hims.service.impl;

import com.alibaba.fastjson.JSON;
import com.hims.domain.AiOcrRecord;
import com.hims.mapper.AiOcrRecordMapper;
import com.hims.service.AiOcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * AI OCR识别服务实现（使用豆包模型）
 */
@Service
public class AiOcrServiceImpl implements AiOcrService {

    @Autowired
    private AiOcrRecordMapper ocrRecordMapper;

    @Value("${hims.doubao.api-key}")
    private String apiKey;

    @Value("${hims.doubao.base-url}")
    private String baseUrl;

    @Value("${hims.doubao.model-id}")
    private String modelId;

    @Value("${hims.upload.path}")
    private String uploadPath;

    @Value("${hims.upload.url-prefix}")
    private String urlPrefix;

    @Override
    public Map<String, Object> recognizeIdCard(MultipartFile file, Integer operatorId) {
        // TODO 学生实现：保存图片、建 OCR 记录、调豆包 API、提取身份证字段、更新记录
        return null;
    }

    @Override
    public Map<String, Object> recognizeMedicalDocument(MultipartFile file, String ocrType, Integer operatorId) {
        // TODO 学生实现：保存图片、建 OCR 记录、调豆包 API、提取医疗文档字段、更新记录
        return null;
    }

    @Override
    public AiOcrRecord getRecordById(Integer ocrId) {
        // TODO 学生实现
        return ocrRecordMapper.selectById(ocrId);
    }

    @Override
    public Map<String, Object> getRecordsByPatientId(String patientId) {
        // TODO 学生实现
        return null;
    }

    @Override
    public Map<String, Object> getPageList(Integer pageNum, Integer pageSize, String ocrType, Integer status) {
        // TODO 学生实现：分页查询 OCR 记录
        return null;
    }

    @Override
    public boolean updateStatus(Integer ocrId, Integer status, String patientId) {
        // TODO 学生实现：更新 OCR 记录状态
        return false;
    }

    @Override
    public boolean deleteRecord(Integer ocrId) {
        // TODO 学生实现
        return ocrRecordMapper.deleteById(ocrId) > 0;
    }
}
