package com.zeal.zeal_oa.common;

import com.zeal.zeal_oa.exception.ZealOAExceptionEnum;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:通用返回对象
 * @date: 2022-06-17 17:25
 */
public class ApiRestResponse<T> {
    private Integer status;
    private String msg;
    private T data;
    private String url;
    private Integer count;

    private static final int OK_CODE=10000;
    private static final String OK_MSG="SUCCESS";

    public ApiRestResponse(Integer status, String msg, T data, String url, Integer count) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.url = url;
        this.count = count;
    }

    public ApiRestResponse(Integer status, String msg, T data, Integer count) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public ApiRestResponse(Integer status, String msg, T data, String url) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.url = url;
    }

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE,OK_MSG);
    }

    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response=new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    public static <T> ApiRestResponse<T> success(T result,String url){
        ApiRestResponse<T> response=new ApiRestResponse<>();
        response.setData(result);
        response.setUrl(url);
        return response;
    }

    public static <T> ApiRestResponse<T> success(T result,Integer count){
        ApiRestResponse<T> response=new ApiRestResponse<>();
        response.setData(result);
        response.setCount(count);
        return response;
    }

    public static <T> ApiRestResponse<T> error(ZealOAExceptionEnum ex){
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg());
    }

    public static <T> ApiRestResponse<T> error(Integer status,String msg){
        return new ApiRestResponse<>(status,msg);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", url='" + url + '\'' +
                ", count=" + count +
                '}';
    }
}
