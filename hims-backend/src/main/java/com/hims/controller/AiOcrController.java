package com.hims.controller;

import com.hims.domain.AiOcrRecord;
import com.hims.service.AiOcrService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * AI OCR识别控制器
 */
@RestController
@RequestMapping("/api/ai/ocr")
@CrossOrigin
public class AiOcrController {

    @Autowired
    private AiOcrService ocrService;

    /**
     * OCR识别身份证
     */
    @PostMapping("/id-card")
    public ResultVo<Map<String, Object>> recognizeIdCard(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) Integer operatorId) {
        // TODO 学生实现：判断 file.isEmpty() + 调用 ocrService.recognizeIdCard
        return ResultVo.success();
    }

    /**
     * OCR识别医疗单据
     */
    @PostMapping("/medical")
    public ResultVo<Map<String, Object>> recognizeMedical(
            @RequestParam("file") MultipartFile file,
            @RequestParam String ocrType,
            @RequestParam(required = false) Integer operatorId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取识别记录详情
     */
    @GetMapping("/record")
    public ResultVo<AiOcrRecord> getRecord(@RequestParam Integer ocrId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据患者ID查询识别记录
     */
    @GetMapping("/records/patient")
    public ResultVo<Map<String, Object>> getRecordsByPatient(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 分页查询识别记录
     */
    @GetMapping("/records")
    public ResultVo<Map<String, Object>> getRecords(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String ocrType,
            @RequestParam(required = false) Integer status) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 更新识别记录状态
     */
    @PostMapping("/status")
    public ResultVo<String> updateStatus(
            @RequestParam Integer ocrId,
            @RequestParam Integer status,
            @RequestParam(required = false) String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 删除识别记录
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteRecord(@RequestParam Integer ocrId) {
        // TODO 学生实现
        return ResultVo.success();
    }
}
