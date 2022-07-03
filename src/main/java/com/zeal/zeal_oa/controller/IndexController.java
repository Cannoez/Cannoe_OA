package com.zeal.zeal_oa.controller;

import com.zeal.zeal_oa.common.ApiRestResponse;
import com.zeal.zeal_oa.common.Constant;
import com.zeal.zeal_oa.model.pojo.Department;
import com.zeal.zeal_oa.model.pojo.Employee;
import com.zeal.zeal_oa.model.pojo.Node;
import com.zeal.zeal_oa.model.pojo.User;
import com.zeal.zeal_oa.service.DepartmentService;
import com.zeal.zeal_oa.service.EmployeeService;
import com.zeal.zeal_oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:首页模块
 * @date: 2022-06-19 10:16
 */
@Controller
public class IndexController {
    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    /**
     * 首页模块
     * @param session
     * @return
     */
    @GetMapping("/index")
    public String Index(HttpSession session){
        //得到当前登录对象
        User user = (User) session.getAttribute(Constant.ZEAL_OA_USER);
        //获取登录用户可用功能模块列表
        List<Node> nodeList = userService.selectNodeByUserId(user.getUserId());
        //保存属性
        session.setAttribute("nodeList",nodeList);
        //获取登录用户的职位信息
        Employee employee = employeeService.selectById(user.getEmployeeId());
        session.setAttribute("current_employee",employee);
        //获取登录用户的部门信息
        Department department = departmentService.selectById(employee.getDepartmentId());
        session.setAttribute("current_dept",department);
        return "/index";
    }

    @GetMapping("/logout")
    public String Logout(HttpSession session){
        //清空session
        session.invalidate();
        return "redirect:/login";
    }
}
