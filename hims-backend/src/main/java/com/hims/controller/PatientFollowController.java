package com.hims.controller;

import com.hims.domain.PatientFollow;
import com.hims.query.PatientFollowQuery;
import com.hims.service.PatientFollowService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 出院随访控制器
 */
@RestController
@RequestMapping("/api/follow")
@CrossOrigin
public class PatientFollowController {

    @Autowired
    private PatientFollowService followService;

    /**
     * 分页查询随访记录
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getFollowList(PatientFollowQuery query) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/info")
    public ResultVo<PatientFollow> getFollowById(@RequestParam Integer followId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据患者ID查询随访记录
     */
    @GetMapping("/by-patient")
    public ResultVo<Map<String, Object>> getFollowsByPatient(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据住院ID查询随访记录
     */
    @GetMapping("/by-inhospital")
    public ResultVo<Map<String, Object>> getFollowsByInhospital(@RequestParam Integer inhospitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 查询待随访列表
     */
    @GetMapping("/pending")
    public ResultVo<Map<String, Object>> getPendingFollows() {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 创建随访记录
     */
    @PostMapping("/create")
    public ResultVo<PatientFollow> createFollow(@RequestBody PatientFollow follow) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 更新随访记录
     */
    @PostMapping("/update")
    public ResultVo<String> updateFollow(@RequestBody PatientFollow follow) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 完成随访
     */
    @PostMapping("/complete")
    public ResultVo<String> completeFollow(@RequestParam Integer followId, @RequestBody PatientFollow follow) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 标记失联
     */
    @PostMapping("/lost")
    public ResultVo<String> markLost(@RequestParam Integer followId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 取消随访
     */
    @PostMapping("/cancel")
    public ResultVo<String> cancelFollow(@RequestParam Integer followId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 删除随访记录
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteFollow(@RequestParam Integer followId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取随访统计
     */
    @GetMapping("/stats")
    public ResultVo<Map<String, Object>> getFollowStats() {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取失联随访列表
     */
    @GetMapping("/lost")
    public ResultVo<Map<String, Object>> getLostFollows() {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * AI生成随访建议
     */
    @PostMapping("/suggest")
    public ResultVo<Map<String, Object>> generateSuggest(@RequestBody Map<String, Object> params) {
        // TODO 学生实现（可选 AI）
        return ResultVo.success();
    }

    /**
     * AI自动创建随访计划
     */
    @PostMapping("/auto-plan")
    public ResultVo<String> autoCreatePlan(@RequestParam Integer inhospitalId) {
        // TODO 学生实现（可选 AI）
        return ResultVo.success();
    }
}
