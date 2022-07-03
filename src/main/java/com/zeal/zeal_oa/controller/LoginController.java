package com.zeal.zeal_oa.controller;


import com.alibaba.fastjson.JSON;
import com.zeal.zeal_oa.common.ApiRestResponse;
import com.zeal.zeal_oa.common.Constant;
import com.zeal.zeal_oa.exception.ZealOAException;
import com.zeal.zeal_oa.exception.ZealOAExceptionEnum;
import com.zeal.zeal_oa.model.dao.NodeMapper;
import com.zeal.zeal_oa.model.dao.UserMapper;
import com.zeal.zeal_oa.model.pojo.Department;
import com.zeal.zeal_oa.model.pojo.Employee;
import com.zeal.zeal_oa.model.pojo.Node;
import com.zeal.zeal_oa.model.pojo.User;
import com.zeal.zeal_oa.service.DepartmentService;
import com.zeal.zeal_oa.service.EmployeeService;
import com.zeal.zeal_oa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:登录模块
 * @date: 2022-06-17 19:03
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    UserService userService;



    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public String Login(){
        return "login";
    }

    /**
     *登录检测
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws ZealOAException
     */
    @PostMapping("/logins")
    @ResponseBody
    public ApiRestResponse Logins(@RequestParam("username") String userName,
                                 @RequestParam("password") String password, HttpSession session)
            throws ZealOAException {
        if (ObjectUtils.isEmpty(userName)) {
            return ApiRestResponse.error(ZealOAExceptionEnum.NEED_USER_NAME);
        }
        if (ObjectUtils.isEmpty(password)) {
            return ApiRestResponse.error(ZealOAExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.checkLogin(userName, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.ZEAL_OA_USER, user);
        return ApiRestResponse.success(user,"/index");
    }









}
