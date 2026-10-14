package com.hims.service;

import com.hims.domain.SysUser;
import com.hims.query.SysUserQuery;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务接口
 */
public interface SysUserService {
    
    /**
     * 登录验证
     */
    SysUser login(String userName, String password);
    
    /**
     * 根据用户名查询用户
     */
    SysUser getByUserName(String userName);
    
    /**
     * 根据ID查询用户
     */
    SysUser getById(Integer userId);
    
    /**
     * 分页查询用户列表
     */
    Map<String, Object> getPageList(SysUserQuery query);
    
    /**
     * 获取所有用户列表
     */
    List<SysUser> getAllList();
    
    /**
     * 根据科室查询用户列表
     */
    List<SysUser> getByDepartment(String department);
    
    /**
     * 创建用户
     */
    boolean create(SysUser user);
    
    /**
     * 更新用户
     */
    boolean update(SysUser user);
    
    /**
     * 修改密码
     */
    boolean updatePassword(Integer userId, String oldPassword, String newPassword);
    
    /**
     * 重置密码
     */
    boolean resetPassword(Integer userId, String newPassword);
    
    /**
     * 删除用户
     */
    boolean delete(Integer userId);
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUserName(String userName, Integer excludeId);
    
    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(String phone, Integer excludeId);
}
