package com.hims.controller;

import com.hims.domain.PatientCourse;
import com.hims.query.PatientCourseQuery;
import com.hims.service.PatientCourseService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 病程记录控制器
 */
@RestController
@RequestMapping("/api/course")
@CrossOrigin
public class PatientCourseController {

    @Autowired
    private PatientCourseService courseService;

    /**
     * 分页查询病程记录
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getCourseList(PatientCourseQuery query) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/info")
    public ResultVo<PatientCourse> getCourseById(@RequestParam Integer courseId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据患者ID查询病程记录
     */
    @GetMapping("/by-patient")
    public ResultVo<Map<String, Object>> getCoursesByPatient(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据住院ID查询病程记录
     */
    @GetMapping("/by-inhospital")
    public ResultVo<Map<String, Object>> getCoursesByInhospital(@RequestParam Integer inhospitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 创建病程记录
     */
    @PostMapping("/create")
    public ResultVo<PatientCourse> createCourse(@RequestBody PatientCourse course) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 更新病程记录
     */
    @PostMapping("/update")
    public ResultVo<String> updateCourse(@RequestBody PatientCourse course) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 删除病程记录
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteCourse(@RequestParam Integer courseId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * AI生成病程摘要
     */
    @PostMapping("/summary")
    public ResultVo<Map<String, Object>> generateSummary(@RequestBody Map<String, String> params) {
        // TODO 学生实现（可选 AI）
        return ResultVo.success();
    }
}
