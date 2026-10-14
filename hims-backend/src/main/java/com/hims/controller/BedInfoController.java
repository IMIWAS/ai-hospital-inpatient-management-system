package com.hims.controller;

import com.hims.domain.BedInfo;
import com.hims.query.BedInfoQuery;
import com.hims.service.BedInfoService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 床位管理控制器
 */
@RestController
@RequestMapping("/api/bed")
@CrossOrigin
public class BedInfoController {

    @Autowired
    private BedInfoService bedInfoService;

    /**
     * 分页查询床位列表
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getBedList(BedInfoQuery query) {
        // TODO 学生实现：调用 bedInfoService.getPageList(query) 并返回
        Map<String, Object> pageList = bedInfoService.getPageList(query);
        return ResultVo.success(pageList);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/info")
    public ResultVo<BedInfo> getBedById(@RequestParam Integer bedId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 查询空床列表
     */
    @GetMapping("/empty")
    public ResultVo<Map<String, Object>> getEmptyBeds(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String bedType) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 智能分配床位
     */
    @PostMapping("/allocate")
    public ResultVo<BedInfo> allocateBed(@RequestBody Map<String, Object> params) {
        // TODO 学生实现：从 params 取字段 → 调用 bedInfoService.allocateBed
        return ResultVo.success();
    }

    /**
     * 释放床位
     */
    @PostMapping("/release")
    public ResultVo<String> releaseBed(@RequestParam Integer bedId) {
        // TODO 学生实现
        return ResultVo.success();
    }

    /**
     * 创建床位
     */
    @PostMapping("/create")
    public ResultVo<String> createBed(@RequestBody BedInfo bed) {
        // TODO 学生实现
        bedInfoService.create(bed);
        return ResultVo.success();
    }

    /**
     * 更新床位
     */
    @PostMapping("/update")
    public ResultVo<String> updateBed(@RequestBody BedInfo bed) {
        // TODO 学生实现
        bedInfoService.update(bed);
        return ResultVo.success();
    }

    /**
     * 删除床位
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteBed(@RequestParam Integer bedId) {
        // TODO 学生实现
        bedInfoService.delete(bedId);
        return ResultVo.success();
    }

    /**
     * 获取床位统计
     */
    @GetMapping("/stats")
    public ResultVo<Map<String, Object>> getBedStats() {
        // TODO 学生实现
        return ResultVo.success();
    }
}
