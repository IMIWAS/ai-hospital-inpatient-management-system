package com.hims.mapper;

import com.hims.domain.SysUser;
import com.hims.query.SysUserQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 系统用户Mapper接口
 */
public interface SysUserMapper {
    
    /**
     * 根据用户名查询
     */
    SysUser selectByUserName(@Param("userName") String userName);
    
    /**
     * 根据ID查询
     */
    SysUser selectById(@Param("userId") Integer userId);
    
    /**
     * 根据手机号查询
     */
    SysUser selectByPhone(@Param("phone") String phone);
    
    /**
     * 分页查询列表
     */
    List<SysUser> selectList(@Param("q") SysUserQuery query);
    
    /**
     * 查询总数
     */
    Long countList(@Param("q") SysUserQuery query);
    
    /**
     * 根据科室查询用户列表
     */
    List<SysUser> selectByDepartment(@Param("department") String department);
    
    /**
     * 插入用户
     */
    int insert(SysUser user);
    
    /**
     * 更新用户
     */
    int update(SysUser user);
    
    /**
     * 修改密码
     */
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);
    
    /**
     * 更新最后登录时间
     */
    int updateLastLoginTime(@Param("userId") Integer userId, @Param("loginTime") java.util.Date loginTime);
    
    /**
     * 根据ID删除
     */
    int deleteById(@Param("userId") Integer userId);
}
