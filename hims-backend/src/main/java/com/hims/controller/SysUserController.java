package com.hims.controller;

import com.hims.domain.SysUser;
import com.hims.query.SysUserQuery;
import com.hims.service.SysUserService;
import com.hims.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户控制器
 *
 * 注意：login() 保留完整业务逻辑（教学参考）
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResultVo<SysUser> login(@RequestBody Map<String, String> params) {
        // ====== 保留登录业务逻辑（教学参考） ======
        String userName = params.get("userName");
        String password = params.get("password");

        if (userName == null || password == null) {
            return ResultVo.error("用户名和密码不能为空");
        }

        SysUser user = sysUserService.login(userName, password);
        return ResultVo.success("登录成功", user);
    }

    /**
     * 注册（学生可自行扩展）
     */
    @PostMapping("/register")
    public ResultVo<String> register(@RequestBody SysUser user) {
        // TODO 学生实现：调用 sysUserService.create(user)
        return ResultVo.success();
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResultVo<SysUser> getUserInfo(@RequestParam Integer userId) {
        // TODO 学生实现
        SysUser byId = sysUserService.getById(userId);
        return ResultVo.success(byId);

    }

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    public ResultVo<Map<String, Object>> getUserList(SysUserQuery query) {
        // TODO 学生实现
        Map<String, Object> pageList = sysUserService.getPageList(query);
        return ResultVo.success(pageList);
    }

    /**
     * 获取所有用户列表
     */
    @GetMapping("/all")
    public ResultVo<List<SysUser>> getAllUsers() {
        // TODO 学生实现
        List<SysUser> allList = sysUserService.getAllList();
       
        return ResultVo.success(allList);
    }
    /**
     * 根据科室查询用户
     */
    @GetMapping("/by-department")
    public ResultVo<List<SysUser>> getUsersByDepartment(@RequestParam String department) {
        // TODO 学生实现
        List<SysUser> users = sysUserService.getByDepartment(department);
        return ResultVo.success(users);
    }

    /**
     * 创建用户
     */
    @PostMapping("/create")
    public ResultVo<String> createUser(@RequestBody SysUser user) {
        // TODO 学生实现
        boolean b = sysUserService.create(user);
        return ResultVo.success();
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    public ResultVo<String> updateUser(@RequestBody SysUser user) {
        // TODO 学生实现
        sysUserService.update(user);
        return ResultVo.success();
    }

    /**
     * 修改密码
     */
    @PostMapping("/password")
    public ResultVo<String> updatePassword(@RequestBody Map<String, String> params) {
        // TODO 学生实现
        String id = params.get("userId");
        Integer userId = Integer.valueOf(id);

        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        sysUserService.updatePassword(userId,oldPassword,newPassword);
        return ResultVo.success();
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    public ResultVo<String> resetPassword(@RequestBody Map<String, Object> params) {
        // TODO 学生实现
        Integer userId = Integer.valueOf(params.get("userId").toString());
        String password = params.get("password").toString();
        sysUserService.resetPassword(userId,password);

        return ResultVo.success();
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    public ResultVo<String> deleteUser(@RequestParam Integer userId) {
        // TODO 学生实现
        sysUserService.delete(userId);
        return ResultVo.success();
    }
}
