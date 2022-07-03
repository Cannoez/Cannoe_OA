package com.zeal.zeal_oa.service.impl;

import com.zeal.zeal_oa.common.Constant;
import com.zeal.zeal_oa.exception.ZealOAException;
import com.zeal.zeal_oa.exception.ZealOAExceptionEnum;
import com.zeal.zeal_oa.model.dao.EmployeeMapper;
import com.zeal.zeal_oa.model.dao.LeaveMapper;
import com.zeal.zeal_oa.model.dao.NoticeMapper;
import com.zeal.zeal_oa.model.dao.ProcessMapper;
import com.zeal.zeal_oa.model.pojo.Employee;
import com.zeal.zeal_oa.model.pojo.Leave;
import com.zeal.zeal_oa.model.pojo.Notice;
import com.zeal.zeal_oa.model.pojo.Process;
import com.zeal.zeal_oa.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:请假单流程模块
 * @date: 2022-06-19 17:28
 */
@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    LeaveMapper leaveMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    ProcessMapper processMapper;
    @Autowired
    NoticeMapper noticeMapper;

    /**
     * 创建请假单
     * @param leave 前端输入的请假单数据
     * @return 持久化后的请假单对象
     */
    @Override
    public Leave createLeave(Leave leave){
        //1.持久化form表单数据,8级以下员工表单状态为processing,8级(总经理)状态为approved
        //通过员工编号,获得员工数据
        Employee employee = employeeMapper.selectByPrimaryKey(leave.getEmployeeId());
        System.out.println(employee.toString());
        if (employee.getLevel()==8){
            leave.setState("approved");
        }else{
            leave.setState("processing");
        }
        leaveMapper.insertSelective(leave);
        //2.增加第一条流程数据,说明表单已提交,状态为complete
        Process pro1=new Process();
        //表单编号
        System.out.println(leave.getFormId());
        pro1.setFormId(leave.getFormId());
        //经办人编号
        pro1.setOperatorId(employee.getEmployeeId());
        System.out.println(pro1.getOperatorId());
        //表单执行的动作
        pro1.setAction("apply");
        //数据创建时间
        pro1.setCreateTime(new Date());
        //表单节点号
        pro1.setOrderNo(1);
        //表单状态
        pro1.setState("complete");
        //是否为最后的节点
        pro1.setIsLast(0);
        processMapper.insertSelective(pro1);
        //3.分情况创建其余流程数据
        //3.1 7级以下员工,生成部门经理审批任务,请假时间大于36小时,还需生成总经理审批任务
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH时");
        if (employee.getLevel()<7){
            //得到部门经理信息
            Employee dmanager = employeeMapper.selectLeader(employee);
            Process pro2=new Process();
            //表单编号
            pro2.setFormId(leave.getFormId());
            //经办人编号
            pro2.setOperatorId(dmanager.getEmployeeId());
            //表单执行的动作
            pro2.setAction("audit");
            //数据创建时间
            pro2.setCreateTime(new Date());
            //表单节点号
            pro2.setOrderNo(2);
            //表单状态
            pro2.setState("process");
            //是否为最后的节点
            long diff = leave.getEndTime().getTime() - leave.getStartTime().getTime();
            float hours = diff / (1000 * 60 * 60) * 1f;
            if (hours>= Constant.MANAGER_AUDIT_HOURS){
                pro2.setIsLast(0);
                processMapper.insertSelective(pro2);
                Employee manager = employeeMapper.selectLeader(dmanager);
                Process pro3=new Process();
                //表单编号
                pro3.setFormId(leave.getFormId());
                //经办人编号
                pro3.setOperatorId(manager.getEmployeeId());
                //表单执行的动作
                pro3.setAction("audit");
                //数据创建时间
                pro3.setCreateTime(new Date());
                //表单节点号
                pro3.setOrderNo(3);
                //表单状态
                pro3.setState("ready");
                pro3.setIsLast(1);
                processMapper.insertSelective(pro3);
            }else{
                pro2.setIsLast(1);
                processMapper.insertSelective(pro2);
            }
            //请假单已提交消息
            String noticeContent=String.format("您的请假申请[%s-%s]已提交,请等待上级审批.",sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()));
            noticeMapper.insertSelective(new Notice(employee.getEmployeeId(),noticeContent));
            //通知部门经理审批消息
            noticeContent=String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                    employee.getTitle(),employee.getName(),sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()));
            noticeMapper.insertSelective(new Notice(dmanager.getEmployeeId(),noticeContent));
            //3.2 7级员工,生成总经理审批任务
        }else if(employee.getLevel()==7){//部门经理
            Employee manager = employeeMapper.selectLeader(employee);
            Process pro=new Process();
            //表单编号
            pro.setFormId(leave.getFormId());
            //经办人编号
            pro.setOperatorId(manager.getEmployeeId());
            //表单执行的动作
            pro.setAction("audit");
            //数据创建时间
            pro.setCreateTime(new Date());
            //表单节点号
            pro.setOrderNo(2);
            //表单状态
            pro.setState("process");
            pro.setIsLast(1);
            processMapper.insertSelective(pro);

            //部门经理请假单已提交消息
            String noticeContent=String.format("您的请假申请[%s-%s]已提交,请等待上级审批.",sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()));
            noticeMapper.insertSelective(new Notice(employee.getEmployeeId(),noticeContent));
            //通知总经理审批消息
            noticeContent=String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                    employee.getTitle(),employee.getName(),sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()));
            noticeMapper.insertSelective(new Notice(manager.getEmployeeId(),noticeContent));

            //3.3 8级员工,生成总经理审批任务,系统自动通过
        }else if(employee.getLevel()==8){
            Process pro=new Process();
            //表单编号
            pro.setFormId(leave.getFormId());
            //经办人编号
            pro.setOperatorId(employee.getEmployeeId());
            //表单执行的动作
            pro.setAction("audit");
           //审批结果
            pro.setResult("approved");
            //审批意见
            pro.setReason("自动通过");
            //创建时间
            pro.setCreateTime(new Date());
            //审批时间
            pro.setAuditTime(new Date());
            //表单状态
            pro.setState("complete");
            //表单节点号
            pro.setOrderNo(2);
            pro.setIsLast(1);
            processMapper.insertSelective(pro);

            //请假单已提交消息
            String noticeContent=String.format("您的请假申请[%s-%s]系统已自动批准通过",sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()));
            noticeMapper.insertSelective(new Notice(employee.getEmployeeId(),noticeContent));

        }
        return leave;
    }


    /**
     * 根据参数获得请假单
     * @param state
     * @param operatorId
     * @return
     */
    @Override
    public List<Map<String,Object>> getLeaveFormList(String state,Long operatorId){
        List<Map<String, Object>> list = leaveMapper.selectByParam(state, operatorId);
        return list;
    }

    /**
     * 请假单审批状态变更
     * @param formId
     * @param operatorId
     * @param result
     * @param reason
     */
    @Override
    public void audit(Long formId, Long operatorId, String result, String reason){
        //1.无论同意/驳回,当前任务状态变更为complete
        List<Process> proList = processMapper.selectByFormId(formId);
        if (proList.size()==0){
            throw new ZealOAException(ZealOAExceptionEnum.WRONG_AUDIT);
        }
        //获取当前任务processFlow对象
        List<Process> processList = proList.stream().filter(p -> p.getOperatorId() == operatorId && p.getState().equals("process")).collect(Collectors.toList());
        Process process=null;
        if (processList.size()==0){
            throw new ZealOAException(ZealOAExceptionEnum.NOT_FOUND_AUDIT);
        }else {
           process=processList.get(0);
           process.setState("complete");
           process.setResult(result);
           process.setReason(reason);
           process.setAuditTime(new Date());
           processMapper.updateByPrimaryKeySelective(process);

        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH时");
        Leave leave = leaveMapper.selectByPrimaryKey(formId);
        Employee employee = employeeMapper.selectByPrimaryKey(leave.getEmployeeId());//表单提交人
        Employee operator = employeeMapper.selectByPrimaryKey(operatorId);//表单经办人
        //2.如果当前任务是最后一个节点,代表流程结束,更新请假单状态为对应的approved/refused
        if (process.getIsLast()==1){
            leave.setState(result);//approved/refused
            leaveMapper.updateByPrimaryKeySelective(leave);
            String strResult=null;
            if (result.equals("approved")){
                strResult="批准";
            }else if (result.equals("refused")){
                strResult="驳回";
            }
            String noticeContent=String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s,审批流程已结束",
                    sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()),
                    operator.getTitle(),operator.getName(),
                    strResult,reason);//发给表单提交人的通知
            noticeMapper.insertSelective(new Notice(leave.getEmployeeId(),noticeContent));
            noticeContent=String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束",
                    employee.getTitle(),employee.getName(),
                    sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()),
                    strResult,reason);//发给表单审批人的通知
            noticeMapper.insertSelective(new Notice(operator.getEmployeeId(),noticeContent));
        }else {
            //readList包含所有后续的节点
            List<Process> readyList = proList.stream().filter(p -> p.getState().equals("ready")).collect(Collectors.toList());
            //3.如果当前任务不是最后一个节点且审批通过,那下一个节点的状态从ready变成process
            if (result.equals("approved")){
                Process readyPro = readyList.get(0);
                readyPro.setState("process");
                processMapper.updateByPrimaryKeySelective(readyPro);
                //消息1:通知表单提交人.部门经理已经审批通过,交由上级继续审批
                String noticeContent1=String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s,请继续等待上级审批",
                        sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()),
                        operator.getTitle(),operator.getName(),reason);
                noticeMapper.insertSelective(new Notice(leave.getEmployeeId(),noticeContent1));
                //消息2: 通知总经理有新的审批任务
                String noticeContent2=String.format("%s-%s提起请假申请[%s-%s],请尽快审批",
                        employee.getTitle(),employee.getName(),
                        sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()));
                noticeMapper.insertSelective(new Notice(readyPro.getOperatorId(),noticeContent2));
                //消息3: 通知部门经理(当前经办人),员工的申请单你已批准,交由上级继续审批
                String noticeContent3 = String.format("%s-%s提起请假申请[%s-%s]您已批准,审批意见:%s,申请转至上级领导继续审批" ,
                        employee.getTitle(),employee.getName(),
                        sdf.format(leave.getStartTime()),sdf.format(leave.getEndTime()),reason);
                noticeMapper.insertSelective(new Notice(operator.getEmployeeId(),noticeContent3));
            }else if (result.equals("refused")){
                //4.如果放弃任务不是最后一个节点且审批驳回,则后续所有任务状态变为cancel,请假单状态变成refused
                for (Process p:readyList){
                    p.setState("cancel");
                    processMapper.updateByPrimaryKeySelective(p);
                }
                leave.setState(result);
                leaveMapper.updateByPrimaryKeySelective(leave);
                //消息1: 通知申请人表单已被驳回
                String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已驳回,审批意见:%s,审批流程已结束" ,
                        sdf.format(leave.getStartTime()) , sdf.format(leave.getEndTime()),
                        operator.getTitle() , operator.getName(),reason);
                noticeMapper.insertSelective(new Notice(leave.getEmployeeId(),noticeContent1));

                //消息2: 通知经办人表单"您已驳回"
                String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s]您已驳回,审批意见:%s,审批流程已结束" ,
                        employee.getTitle() , employee.getName() , sdf.format( leave.getStartTime()) , sdf.format(leave.getEndTime()), reason);
                noticeMapper.insertSelective(new Notice(operator.getEmployeeId(),noticeContent2));
            }
        }
    }
}
