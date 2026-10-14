package com.hims.controller;

import com.hims.domain.PatientInhospital;
import com.hims.query.PatientInhospitalQuery;
import com.hims.service.PatientInhospitalService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 住院记录控制器
 */
@RestController
@RequestMapping("/api/inhospital")
@CrossOrigin
public class PatientInhospitalController {

    @Autowired
    private PatientInhospitalService inhospitalService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 分页查询住院记录
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getInhospitalList(PatientInhospitalQuery query) {
        StringBuilder where = new StringBuilder(" WHERE 1 = 1");
        List<Object> args = new ArrayList<>();
        if (query.getKeyword() != null && !query.getKeyword().isEmpty()) {
            where.append(" AND (pi.name LIKE ? OR phi.inhospital_no LIKE ? OR phi.diagnosis LIKE ?)");
            String kw = "%" + query.getKeyword() + "%";
            args.add(kw);
            args.add(kw);
            args.add(kw);
        }
        if (query.getDepartment() != null && !query.getDepartment().isEmpty()) {
            where.append(" AND phi.department = ?");
            args.add(query.getDepartment());
        }
        if (query.getInhospitalStatus() != null) {
            where.append(" AND phi.inhospital_status = ?");
            args.add(query.getInhospitalStatus());
        }
        String baseSql = "FROM patient_inhospital phi " +
                "JOIN patient_info pi ON phi.patient_id = pi.patient_id " +
                "LEFT JOIN bed_info bi ON phi.bed_id = bi.bed_id " +
                "LEFT JOIN sys_user d ON phi.doctor_id = d.user_id " +
                "LEFT JOIN sys_user n ON phi.nurse_id = n.user_id " +
                "LEFT JOIN v_patient_latest_vital lv ON lv.patient_id = phi.patient_id" +
                where;
        Integer total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) " + baseSql, Integer.class, args.toArray());
        String sql = "SELECT phi.inhospital_id, phi.patient_id, pi.name AS patient_name, pi.gender, pi.age, " +
                "phi.inhospital_no, phi.department, bi.bed_no, bi.room_no, phi.diagnosis, " +
                "phi.inhospital_time, phi.outhospital_time, phi.inhospital_days, phi.total_cost, " +
                "CAST(phi.inhospital_status AS SIGNED) AS inhospital_status, " +
                "CAST(phi.settlement_status AS SIGNED) AS settlement_status, " +
                "d.real_name AS doctor_name, n.real_name AS nurse_name, " +
                "CAST(lv.risk_level AS SIGNED) AS risk_level, lv.risk_score, lv.risk_tip " + baseSql +
                " ORDER BY phi.inhospital_time DESC LIMIT ?, ?";
        List<Object> queryArgs = new ArrayList<>(args);
        queryArgs.add(query.getPageNum());
        queryArgs.add(query.getPageSize());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, queryArgs.toArray());
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", total);
        data.put("pageNum", query.getPageNum() / Math.max(query.getPageSize(), 1) + 1);
        data.put("pageSize", query.getPageSize());
        return ResultVo.success(data);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/info")
    public ResultVo<PatientInhospital> getInhospitalById(@RequestParam Integer inhospitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 根据住院号查询
     */
    @GetMapping("/by-no")
    public ResultVo<PatientInhospital> getInhospitalByNo(@RequestParam String inhospitalNo) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 患者入院
     */
    @PostMapping("/admit")
    public ResultVo<PatientInhospital> admitPatient(@RequestBody PatientInhospital inhospital) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 患者转科
     */
    @PostMapping("/transfer")
    public ResultVo<String> transferPatient(@RequestBody Map<String, Object> params) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 患者出院
     */
    @PostMapping("/discharge")
    public ResultVo<String> dischargePatient(@RequestBody Map<String, Object> params) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 患者请假
     */
    @PostMapping("/leave")
    public ResultVo<String> leavePatient(@RequestBody Map<String, Object> params) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 更新住院记录
     */
    @PostMapping("/update")
    public ResultVo<String> updateInhospital(@RequestBody PatientInhospital inhospital) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 结算
     */
    @PostMapping("/settle")
    public ResultVo<String> settleInhospital(@RequestParam Integer inhospitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 删除住院记录
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteInhospital(@RequestParam Integer inhospitalId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取在院患者统计
     */
    @GetMapping("/stats/inhospital")
    public ResultVo<Map<String, Object>> getInhospitalStats(@RequestParam(required = false) String department) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取床位使用统计
     */
    @GetMapping("/stats/bed")
    public ResultVo<Map<String, Object>> getBedStats() {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取科室统计
     */
    @GetMapping("/stats/dept")
    public ResultVo<Map<String, Object>> getDeptStats() {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 获取费用统计
     */
    @GetMapping("/stats/cost")
    public ResultVo<Map<String, Object>> getCostStats() {
        // TODO 学生实现
        return ResultVo.success();
    }
}
