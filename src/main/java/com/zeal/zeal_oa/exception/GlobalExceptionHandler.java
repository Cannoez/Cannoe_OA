package com.zeal.zeal_oa.exception;

import com.zeal.zeal_oa.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:处理统一异常的handler
 * @date: 2022-06-17 18:05
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        log.error("Default Exception",e);
        e.getStackTrace();
        return ApiRestResponse.error(ZealOAExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ZealOAException.class)
    @ResponseBody
    public Object handleZealOAException(ZealOAException e){
        log.error("ZealOAException",e);
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
//        log.error("MethodArgumentNotValidException",e);
//        //return ApiRestResponse.error();
//    }

    private ApiRestResponse handleBindingResult(BindingResult result){
        //把异常处理为对外暴露的提示
        List<String> list=new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size()==0){
            return ApiRestResponse.error(ZealOAExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(ZealOAExceptionEnum.REQUEST_PARAM_ERROR.getCode(),list.toString());
    }





}
