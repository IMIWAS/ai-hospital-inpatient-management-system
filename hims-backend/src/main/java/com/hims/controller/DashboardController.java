package com.hims.controller;

import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计控制器（用于数据大屏）
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取首页统计数据
     */
    @GetMapping("/home")
    public ResultVo<Map<String, Object>> getHomeStats() {
        Map<String, Object> data = new HashMap<>();
        // 在院患者按科室统计
        List<Map<String, Object>> deptList = jdbcTemplate.queryForList(
                "SELECT department, COUNT(*) AS inhospital_count FROM patient_inhospital " +
                        "WHERE inhospital_status = 1 GROUP BY department");
        data.put("inhospitalStats", Collections.singletonMap("data", deptList));
        // 累计患者数
        data.put("totalPatients", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patient_info", Integer.class));
        // 在院患者数
        data.put("inhospitalCount", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patient_inhospital WHERE inhospital_status = 1", Integer.class));
        // 高危预警患者数（在院患者最新体征评级为高危）
        data.put("highRiskPatients", Collections.singletonMap("count", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM v_inhospital_patients WHERE inhospital_status = 1 AND risk_level = 3",
                Integer.class)));
        // 待随访数
        data.put("pendingFollows", Collections.singletonMap("count", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patient_follow WHERE follow_status = 0", Integer.class)));
        // 今日AI识别数
        data.put("ocrToday", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ai_ocr_record WHERE DATE(create_time) = CURDATE()", Integer.class));
        return ResultVo.success(data);
    }

    /**
     * 获取在院患者统计
     */
    @GetMapping("/inhospital")
    public ResultVo<Map<String, Object>> getInhospitalStats(@RequestParam(required = false) String department) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                "SELECT department, COUNT(*) AS inhospital_count FROM patient_inhospital " +
                        "WHERE inhospital_status = 1 GROUP BY department");
        return ResultVo.success(Collections.singletonMap("list", list));
    }

    /**
     * 获取床位使用统计
     */
    @GetMapping("/bed")
    public ResultVo<Map<String, Object>> getBedStats() {
        Map<String, Object> data = new HashMap<>();
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM bed_info", Integer.class);
        Integer occupied = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bed_info WHERE bed_status = 1", Integer.class);
        data.put("totalBeds", total);
        data.put("occupiedBeds", occupied);
        data.put("emptyBeds", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bed_info WHERE bed_status = 0", Integer.class));
        data.put("maintenanceBeds", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bed_info WHERE bed_status = 2", Integer.class));
        data.put("reservedBeds", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bed_info WHERE bed_status = 3", Integer.class));
        data.put("occupancyRate", total == null || total == 0 ? 0
                : Math.round(occupied * 100.0 / total));
        data.put("statsByDept", jdbcTemplate.queryForList(
                "SELECT department AS name, occupancy_rate, occupied_count, total_count " +
                        "FROM v_bed_usage ORDER BY department"));
        return ResultVo.success(data);
    }

    /**
     * 获取科室住院统计
     */
    @GetMapping("/dept")
    public ResultVo<Map<String, Object>> getDeptStats() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                "SELECT department AS name, COUNT(*) AS count FROM patient_inhospital " +
                        "WHERE inhospital_status = 1 GROUP BY department ORDER BY count DESC");
        return ResultVo.success(Collections.singletonMap("list", list));
    }

    /**
     * 获取费用统计
     */
    @GetMapping("/cost")
    public ResultVo<Map<String, Object>> getCostStats() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                "SELECT department, SUM(total_cost) AS total_cost, COUNT(*) AS patient_count " +
                        "FROM patient_inhospital WHERE inhospital_status = 1 GROUP BY department");
        return ResultVo.success(Collections.singletonMap("list", list));
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
        return ResultVo.success(Collections.singletonMap("list", list));
    }

    /**
     * 获取体征异常统计
     */
    @GetMapping("/vital/abnormal")
    public ResultVo<Map<String, Object>> getAbnormalStats(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        StringBuilder sql = new StringBuilder(
                "SELECT risk_level, COUNT(*) AS count FROM patient_vital WHERE 1 = 1");
        List<Object> args = new ArrayList<>();
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND record_time >= ?");
            args.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND record_time <= ?");
            args.add(endDate + " 23:59:59");
        }
        sql.append(" GROUP BY risk_level");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), args.toArray());
        return ResultVo.success(Collections.singletonMap("list", list));
    }

    /**
     * 获取随访统计
     */
    @GetMapping("/follow")
    public ResultVo<Map<String, Object>> getFollowStats() {
        Map<String, Object> data = new HashMap<>();
        data.put("pending_count", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patient_follow WHERE follow_status = 0", Integer.class));
        data.put("completed_count", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patient_follow WHERE follow_status = 1", Integer.class));
        data.put("lost_count", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patient_follow WHERE follow_status = 2", Integer.class));
        data.put("total_count", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patient_follow", Integer.class));
        return ResultVo.success(data);
    }

    /**
     * 获取失联随访列表
     */
    @GetMapping("/follow/lost")
    public ResultVo<Map<String, Object>> getLostFollows() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                "SELECT f.follow_id, p.name AS patientName, f.follow_type AS followType, " +
                        "f.follow_time AS followTime, p.phone " +
                        "FROM patient_follow f LEFT JOIN patient_info p ON f.patient_id = p.patient_id " +
                        "WHERE f.follow_status = 2 ORDER BY f.follow_time DESC");
        return ResultVo.success(Collections.singletonMap("list", list));
    }
}
