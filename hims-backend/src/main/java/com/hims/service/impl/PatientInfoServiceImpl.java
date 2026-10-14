package com.hims.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hims.domain.PatientInfo;
import com.hims.mapper.PatientInfoMapper;
import com.hims.query.PatientInfoQuery;
import com.hims.service.PatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 患者信息服务实现
 */
@Service
public class PatientInfoServiceImpl implements PatientInfoService {

    @Autowired
    private PatientInfoMapper patientInfoMapper;

    @Override
    public Map<String, Object> getPageList(PatientInfoQuery query) {
        // TODO 学生实现：使用 PageHelper + patientInfoMapper.selectList + PageInfo 封装分页结果
        return null;
    }

    @Override
    public PatientInfo getById(String patientId) {
        // TODO 学生实现
        return patientInfoMapper.selectById(patientId);
    }

    @Override
    public PatientInfo getByIdCard(String idCard) {
        // TODO 学生实现
        return patientInfoMapper.selectByIdCard(idCard);
    }

    @Override
    public PatientInfo getByInhospitalNo(String inhospitalNo) {
        // TODO 学生实现
        return patientInfoMapper.selectByInhospitalNo(inhospitalNo);
    }

    @Override
    @Transactional
    public PatientInfo create(PatientInfo patient, Integer operatorId) {
        // TODO 学生实现：生成患者ID/编号、查重身份证、设置时间、调用 mapper.insert
        return null;
    }

    @Override
    public boolean update(PatientInfo patient) {
        // TODO 学生实现
        return patientInfoMapper.update(patient) > 0;
    }

    @Override
    public boolean delete(String patientId) {
        // TODO 学生实现
        return patientInfoMapper.deleteById(patientId) > 0;
    }

    @Override
    public boolean existsByIdCard(String idCard, String excludeId) {
        // TODO 学生实现
        return patientInfoMapper.countByIdCard(idCard, excludeId) > 0;
    }

    @Override
    public String generatePatientId() {
        // TODO 学生实现：时间戳 + 随机数生成患者唯一ID
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return "P" + sdf.format(new Date());
    }
}
