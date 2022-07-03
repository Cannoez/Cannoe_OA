package com.zeal.zeal_oa.controller;

import com.zeal.zeal_oa.common.ApiRestResponse;
import com.zeal.zeal_oa.common.Constant;
import com.zeal.zeal_oa.model.pojo.Notice;
import com.zeal.zeal_oa.model.pojo.User;
import com.zeal.zeal_oa.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-20 21:22
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping("/list")
    @ResponseBody
    public Map getNoticeList(HttpSession session){
        User user = (User) session.getAttribute(Constant.ZEAL_OA_USER);
        List<Notice> noticeList = noticeService.getNoticeList(user.getEmployeeId());
        Map result=new HashMap();
        result.put("code","0");
        result.put("msg","");
        result.put("count",noticeList.size());
        result.put("data",noticeList);
        return result;
    }

}
