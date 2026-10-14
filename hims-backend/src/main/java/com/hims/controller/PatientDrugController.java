package com.hims.controller;

import com.hims.domain.PatientDrug;
import com.hims.query.PatientDrugQuery;
import com.hims.service.PatientDrugService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用药医嘱控制器
 */
@RestController
@RequestMapping("/api/drug")
@CrossOrigin
public class PatientDrugController {

    @Autowired
    private PatientDrugService drugService;

    /**
     * 分页查询用药医嘱
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getDrugList(PatientDrugQuery query) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/info")
    public ResultVo<PatientDrug> getDrugById(@RequestParam Integer drugId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据患者ID查询医嘱
     */
    @GetMapping("/by-patient")
    public ResultVo<Map<String, Object>> getDrugsByPatient(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据住院ID查询医嘱
     */
    @GetMapping("/by-inhospital")
    public ResultVo<Map<String, Object>> getDrugsByInhospital(@RequestParam Integer inhospitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 查询患者待执行医嘱
     */
    @GetMapping("/pending")
    public ResultVo<Map<String, Object>> getPendingDrugs(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 创建医嘱
     */
    @PostMapping("/create")
    public ResultVo<PatientDrug> createDrug(@RequestBody PatientDrug drug) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 更新医嘱
     */
    @PostMapping("/update")
    public ResultVo<String> updateDrug(@RequestBody PatientDrug drug) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 执行医嘱
     */
    @PostMapping("/execute")
    public ResultVo<String> executeDrug(@RequestParam Integer drugId, @RequestParam Integer executeUserId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 暂停医嘱
     */
    @PostMapping("/pause")
    public ResultVo<String> pauseDrug(@RequestParam Integer drugId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 删除医嘱
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteDrug(@RequestParam Integer drugId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取药品使用统计
     */
    @GetMapping("/stats")
    public ResultVo<Map<String, Object>> getDrugStats(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String department) {
        // TODO 学生实现
        return ResultVo.success();
    }
}
