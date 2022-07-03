package com.zeal.zeal_oa.exception;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-17 17:53
 */
public class ZealOAException extends RuntimeException {
    private  Integer code;
    private  String message;

    public ZealOAException(Integer code,String message){
        super(code+":"+message);
        this.code=code;
        this.message=message;
    }

    public ZealOAException(ZealOAExceptionEnum zealOAExceptionEnum){
        this(zealOAExceptionEnum.getCode(), zealOAExceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
