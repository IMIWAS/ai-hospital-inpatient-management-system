package com.hims.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hims.domain.BedInfo;
import com.hims.mapper.BedInfoMapper;
import com.hims.query.BedInfoQuery;
import com.hims.service.BedInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 床位管理服务实现
 */
@Service
public class BedInfoServiceImpl implements BedInfoService {

    @Autowired
    private BedInfoMapper bedInfoMapper;

    @Override
    public Map<String, Object> getPageList(BedInfoQuery query) {
        // TODO 学生实现：使用 PageHelper.startPage + bedInfoMapper.selectList + PageInfo 封装分页结果
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<BedInfo> bedInfos = bedInfoMapper.selectList(query);
        PageInfo pageInfo = new PageInfo(bedInfos);
        
        HashMap<String ,Object>map =new HashMap<>();
        map.put("pageNum",query.getPageNum());
        map.put("pageSize",query.getPageSize());
        map.put("total",pageInfo.getTotal());
        map.put("list",bedInfos);
        map.put("pages",pageInfo.getPages());



        return map;
    }

    @Override
    public BedInfo getById(Integer bedId) {
        // TODO 学生实现
        return bedInfoMapper.selectById(bedId);
    }

    @Override
    public Map<String, Object> getEmptyBeds(String department, String bedType) {
        // TODO 学生实现：查询空床位并封装结果
        return null;
    }

    @Override
    @Transactional
    public BedInfo allocateBed(String department, String patientId, Integer inhospitalId, String bedType) {
        // TODO 学生实现：分配床位业务（查询空床、选择、改状态、返回）
        return null;
    }

    @Override
    @Transactional
    public boolean releaseBed(Integer bedId) {
        // TODO 学生实现
        return bedInfoMapper.updateStatus(bedId, 0, null, null) > 0;
    }

    @Override
    public boolean create(BedInfo bed) {
        // TODO 学生实现：设置默认值 + 调用 mapper.insert
        bed.setCreateTime(new Date());
        return  bedInfoMapper.insert(bed)>0;

    }

    @Override
    public boolean update(BedInfo bed) {
        // TODO 学生实现
        return bedInfoMapper.update(bed)>0;

    }

    @Override
    public boolean delete(Integer bedId) {
        // TODO 学生实现：判断床位状态决定能否删除
        return bedInfoMapper.deleteById(bedId)>0;
    }

    @Override
    public Map<String, Object> getBedStats() {
        // TODO 学生实现：统计床位数据（总数、空床、占用、入住率）
        return null;
    }
}
