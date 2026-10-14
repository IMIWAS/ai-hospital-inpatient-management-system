package com.hims.controller;

import com.hims.domain.PatientInfo;
import com.hims.query.PatientInfoQuery;
import com.hims.service.PatientInfoService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 患者信息控制器
 */
@RestController
@RequestMapping("/api/patient")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientInfoService patientInfoService;

    /**
     * 分页查询患者列表
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getPatientList(PatientInfoQuery query) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据ID查询患者
     */
    @GetMapping("/info")
    public ResultVo<PatientInfo> getPatientById(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据身份证号查询患者
     */
    @GetMapping("/by-idcard")
    public ResultVo<PatientInfo> getPatientByIdCard(@RequestParam String idCard) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据住院号查询患者
     */
    @GetMapping("/by-inhospital-no")
    public ResultVo<PatientInfo> getPatientByInhospitalNo(@RequestParam String inhospitalNo) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 创建患者
     */
    @PostMapping("/create")
    public ResultVo<PatientInfo> createPatient(@RequestBody PatientInfo patient,
                                                @RequestParam(required = false) Integer operatorId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 更新患者信息
     */
    @PostMapping("/update")
    public ResultVo<String> updatePatient(@RequestBody PatientInfo patient) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 删除患者
     */
    @PostMapping("/delete")
    public ResultVo<String> deletePatient(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }
}
