package com.zeal.zeal_oa.service;

import com.zeal.zeal_oa.model.pojo.Employee;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-19 9:35
 */
public interface EmployeeService {

    Employee selectById(Long employeeId);


}
