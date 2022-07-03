package com.zeal.zeal_oa.service.impl;

import com.zeal.zeal_oa.model.dao.EmployeeMapper;
import com.zeal.zeal_oa.model.pojo.Employee;
import com.zeal.zeal_oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:员工模块实现类
 * @date: 2022-06-19 9:35
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Override
    public Employee selectById(Long employeeId){
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        return employee;
    }



}
