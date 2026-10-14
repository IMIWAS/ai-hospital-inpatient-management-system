package com.hims.controller;

import com.hims.domain.PatientVital;
import com.hims.query.PatientVitalQuery;
import com.hims.service.PatientVitalService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生命体征控制器
 */
@RestController
@RequestMapping("/api/vital")
@CrossOrigin
public class PatientVitalController {

    @Autowired
    private PatientVitalService vitalService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 分页查询生命体征记录
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getVitalList(PatientVitalQuery query) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/info")
    public ResultVo<PatientVital> getVitalById(@RequestParam Integer vitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取患者最新体征
     */
    @GetMapping("/latest")
    public ResultVo<PatientVital> getLatestVital(@RequestParam String patientId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取患者体征历史
     */
    @GetMapping("/history")
    public ResultVo<Map<String, Object>> getVitalHistory(
            @RequestParam String patientId,
            @RequestParam(required = false) Integer limit) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 记录生命体征（包含AI风险评估）
     */
    @PostMapping("/record")
    public ResultVo<PatientVital> recordVital(@RequestBody PatientVital vital,
                                              @RequestParam Integer recordUserId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 更新生命体征
     */
    @PostMapping("/update")
    public ResultVo<String> updateVital(@RequestBody PatientVital vital) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 删除记录
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteVital(@RequestParam Integer vitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取高危患者列表（在院患者最新体征为中危/高危）
     */
    @GetMapping("/high-risk")
    public ResultVo<Map<String, Object>> getHighRiskPatients() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                "SELECT patient_name, gender, age, department, bed_no, " +
                        "CAST(risk_level AS SIGNED) AS risk_level, risk_score, risk_tip, " +
                        "last_vital_time AS record_time " +
                        "FROM v_inhospital_patients " +
                        "WHERE inhospital_status = 1 AND risk_level >= 2 " +
                        "ORDER BY risk_level DESC, record_time DESC");
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("count", list.size());
        return ResultVo.success(data);
    }

    /**
     * AI风险评估（单独调用）
     */
    @PostMapping("/risk-assessment")
    public ResultVo<Map<String, Object>> aiRiskAssessment(@RequestBody PatientVital vital) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取体征异常统计
     */
    @GetMapping("/stats/abnormal")
    public ResultVo<Map<String, Object>> getAbnormalStats(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        // TODO 学生实现
        return ResultVo.success();
    }
}
