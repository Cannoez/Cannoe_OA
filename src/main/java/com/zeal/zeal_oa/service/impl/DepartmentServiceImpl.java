package com.zeal.zeal_oa.service.impl;

import com.zeal.zeal_oa.model.dao.DepartmentMapper;
import com.zeal.zeal_oa.model.pojo.Department;
import com.zeal.zeal_oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:部门模块实现类
 * @date: 2022-06-19 9:56
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department selectById(Long departmentId){
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        return department;
    }
}
