package com.zeal.zeal_oa.controller;

import com.zeal.zeal_oa.common.ApiRestResponse;
import com.zeal.zeal_oa.common.Constant;
import com.zeal.zeal_oa.exception.ZealOAException;
import com.zeal.zeal_oa.exception.ZealOAExceptionEnum;
import com.zeal.zeal_oa.model.pojo.Leave;
import com.zeal.zeal_oa.model.pojo.User;
import com.zeal.zeal_oa.service.LeaveService;
import com.zeal.zeal_oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:请假申请模块
 * @date: 2022-06-19 19:44
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @Autowired
    UserService userService;

    /**
     * 创建清单
     * @param startTime
     * @param endTime
     * @param formType
     * @param reason
     * @param session
     * @return
     * @throws ParseException
     */
    @PostMapping ("/create")
    @ResponseBody
    public ApiRestResponse createLeave(@RequestParam("startTime") String startTime,
                                       @RequestParam("endTime") String endTime,
                                       @RequestParam("formType") String formType,
                                       @RequestParam("reason") String reason,
                                       HttpSession session) throws ParseException {
        //获取User对象
        User currentUser = (User) session.getAttribute(Constant.ZEAL_OA_USER);
        if (currentUser==null){
            throw new ZealOAException(ZealOAExceptionEnum.NEED_LOGIN);
        }
        //日期格式转换
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH");
        Leave leave=new Leave();
            leave.setEmployeeId(currentUser.getEmployeeId());
            leave.setStartTime(sdf.parse(startTime));
            leave.setEndTime(sdf.parse(endTime));
            leave.setFormType(Integer.parseInt(formType));
            leave.setReason(reason);
            leave.setCreateTime(new Date());

            System.out.println(leave.toString());
        Leave addleave = leaveService.createLeave(leave);
        return ApiRestResponse.success(addleave);
    }

    /**
     * 请假表单展示
     * @param session
     * @return
     */
    @GetMapping ("/list")
    @ResponseBody
    public Map getLeaveFormList(HttpSession session){
        User user = (User) session.getAttribute(Constant.ZEAL_OA_USER);
        List<Map<String, Object>> leaveFormList = leaveService.getLeaveFormList("process", user.getEmployeeId());
        Map result=new HashMap();
        result.put("code","0");
        result.put("msg","");
        result.put("count",leaveFormList.size());
        result.put("data",leaveFormList);
        return result;
    }

    /**
     * 审批功能
     * @param formId
     * @param result
     * @param reason
     * @param session
     * @return
     */
    @PostMapping("/audit")
    @ResponseBody
    public ApiRestResponse audit(@RequestParam("formId") Long formId,
                                 @RequestParam("result") String result,
                                 @RequestParam("reason") String reason,
                                 HttpSession session){
        User user = (User) session.getAttribute(Constant.ZEAL_OA_USER);
        leaveService.audit(formId,user.getEmployeeId(),result,reason);
        return ApiRestResponse.success();
    }



}
