package com.hims.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.hims.domain.SysUser;
import com.hims.mapper.SysUserMapper;
import com.hims.query.SysUserQuery;
import com.hims.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 系统用户服务实现
 *
 * 注意：
 * - login() / register() 保留完整业务逻辑（供学生参考）
 * - 其他方法清空为骨架
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser login(String userName, String password) {
        // ====== 保留登录业务逻辑（教学参考） ======
        SysUser user = sysUserMapper.selectByUserName(userName);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }

        // 简单密码验证（实际项目应使用BCrypt）
        String md5Pwd = DigestUtils.md5DigestAsHex((password).getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(md5Pwd) && !user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }

        // 更新最后登录时间
        sysUserMapper.updateLastLoginTime(user.getUserId(), new Date());

        return user;
    }

    @Override
    public SysUser getByUserName(String userName) {
        // TODO 学生实现
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public SysUser getById(Integer userId) {
        // TODO 学生实现
        return sysUserMapper.selectById(userId);
    }

    @Override
    public Map<String, Object> getPageList(SysUserQuery query) {
        // TODO 学生实现：分页查询用户列表
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
//        开始分页查询
        List<SysUser> sysUsers = sysUserMapper.selectList(query);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);

        HashMap<String, Object> map = new HashMap<>();

        map.put("pageNum", query.getPageNum());
        map.put("pageSize", query.getPageSize());
        map.put("list", sysUsers);
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());

        return map;
    }

    @Override
    public List<SysUser> getAllList() {
        // TODO 学生实现：查询所有用户（用于下拉选项）
        SysUserQuery query=new SysUserQuery();
        List<SysUser>sysUsers=sysUserMapper.selectList(query);
        return sysUsers;
    }

    @Override
    public List<SysUser> getByDepartment(String department) {
        // TODO 学生实现：根据科室查询用户
        return sysUserMapper.selectByDepartment(department);
    }

    @Override
    public boolean create(SysUser user) {
        // TODO 学生实现：检查用户名/手机号、MD5 加密密码、设置时间、插入
        if(StringUtils.isEmpty(user.getUserName())){
            throw  new RuntimeException("用户名不能为空");
        }

        if(existsByUserName(user.getUserName(), null)){
            throw new RuntimeException("用户名不能重复");
        }
        if (existsByPhone(user.getPhone(),null)){
            throw new RuntimeException("手机号不能重复");

        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        user.setCreateTime(new Date());

        user.setUpdateTime(new Date());
        user.setStatus(1);
        int insert=sysUserMapper.insert(user);

        return insert >0;
    }

    @Override
    public boolean update(SysUser user) {
        // TODO 学生实现
        if (StringUtils.isEmpty(user.getUserName())){
            throw new RuntimeException("用户名不能为空");
        }
        if (existsByUserName(user.getUserName(),user.getUserId())){
            throw new RuntimeException("用户名不能重复");
        }
        if (existsByPhone(user.getPhone(),user.getUserId())){
            throw new RuntimeException("手机号不能重复");
        }
        user.setUpdateTime(new Date());
        return sysUserMapper.update(user) > 0;
    }

    @Override
    public boolean updatePassword(Integer userId, String oldPassword, String newPassword) {
        // TODO 学生实现：校验旧密码 + 更新新密码
        SysUser user =sysUserMapper.selectById(userId);
        if (user==null){
            throw new RuntimeException("当前用户不存在");

        }
        String oldMd5 = user.getPassword();
        DigestUtils.md5DigestAsHex(oldPassword.getBytes(StandardCharsets.UTF_8));
        if (!oldMd5.equals(oldMd5)){
            throw new RuntimeException("原密码输入错误");
        }
        String newMd5 = DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8));
        int i = sysUserMapper.updatePassword(userId,newMd5);

        return i>0;

    }

    @Override
    public boolean resetPassword(Integer userId, String newPassword) {
        // TODO 学生实现：重置密码（管理员操作）
        SysUser user = sysUserMapper.selectById(userId);
        if (user==null){
            throw new RuntimeException("用户不存在,不许修改");
        }
        String password = "";
        if (StringUtils.isEmpty(newPassword)){
            password=DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));

        }else {
            password=DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8));
        }

        int i =sysUserMapper.updatePassword(userId,password);

        return i>0;
    }


    @Override
    public boolean delete(Integer userId) {
        // TODO 学生实现
        return sysUserMapper.deleteById(userId) > 0;
    }

    @Override
    public boolean existsByUserName(String userName, Integer excludeId) {
        // TODO 学生实现
        //根据用户id查询 有重复 没有
        SysUser user=sysUserMapper.selectByUserName(userName);
        return user !=null && !user.getUserId().equals(excludeId);
    }

    @Override
    public boolean existsByPhone(String phone, Integer excludeId) {
        // TODO 学生实现
        SysUser user=sysUserMapper.selectByPhone(phone);
        return user !=null && !user.getUserId().equals(excludeId);

    }
}
