package com.zeal.zeal_oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:页面跳转模块
 * @date: 2022-06-20 14:19
 */
@Controller
@RequestMapping("/forward")
public class ForwardController {
    /**
     * 请假申请单页面
     * @return
     */
    @RequestMapping("/form")
    public String create(){
        return "/form";
    }

    /**
     * 请假申请审批页面
     */
    @RequestMapping("/audit")
    public String list(){
        return "/audit";
    }

    /**
     * 公告页面
     */
    @RequestMapping("/notice")
    public String notice(){
        return "/notice";
    }


}
