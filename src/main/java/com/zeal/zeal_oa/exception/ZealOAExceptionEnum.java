package com.zeal.zeal_oa.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:异常枚举
 * @date: 2022-06-17 17:57
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ZealOAExceptionEnum {
    WRONG_USERNAME(10001,"该用户名不存在"),
    REQUEST_PARAM_ERROR(10002,"参数错误"),
    NEED_USER_NAME(10003,"用户名不能为空"),
    NEED_PASSWORD(10004,"密码不能为空"),
    WRONG_PASSWORD(10006,"密码错误"),
    NEED_LOGIN(10007,"用户未登录"),
    WRONG_AUDIT(10008,"无效的审批流程"),
    NOT_FOUND_AUDIT(10009,"未找到待处理任务"),
    NO_ENUM(10019,"未找到对应的枚举类"),
    SYSTEM_ERROR(20000,"系统异常");
    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    ZealOAExceptionEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
