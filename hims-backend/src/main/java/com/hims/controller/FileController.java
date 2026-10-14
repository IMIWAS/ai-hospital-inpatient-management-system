package com.hims.controller;

import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class FileController {

    @Value("${hims.upload.path}")
    private String uploadPath;

    @Value("${hims.upload.url-prefix}")
    private String urlPrefix;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public ResultVo<Map<String, Object>> upload(@RequestParam("file") MultipartFile file,
                                                 @RequestParam(required = false) String type) {
        // TODO 学生实现：建目录、保存文件、返回 fileUrl
        return ResultVo.success();
    }

    /**
     * 上传图片（用于患者头像、检查报告等）
     */
    @PostMapping("/upload/image")
    public ResultVo<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file,
                                                     @RequestParam(required = false) String category) {
        // TODO 学生实现：判断 contentType 是 image/*，保存图片
        return ResultVo.success();
    }

    /**
     * 批量上传文件
     */
    @PostMapping("/upload/batch")
    public ResultVo<List<Map<String, Object>>> uploadBatch(@RequestParam("files") MultipartFile[] files) {
        // TODO 学生实现：循环保存每个文件
        return ResultVo.success();
    }
}
