package com.zeal.zeal_oa.service;

import com.zeal.zeal_oa.model.pojo.Leave;

import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:请假单流程服务
 * @date: 2022-06-19 17:28
 */
public interface LeaveService {
    Leave createLeave(Leave leave);

    List<Map<String,Object>> getLeaveFormList(String state, Long operatorId);

    void audit(Long formId, Long operatorId, String result, String reason);
}
