package com.Bibo.common.request;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.Bibo.common.util.ServletUtils;
import com.Bibo.common.response.Response;
import com.Bibo.common.constant.ResponseEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * web层通用数据处理
 */
@Slf4j
public class BaseController {

    /**
     * 获取Request
     */
    public HttpServletRequest getRequest(){
        return ServletUtils.getRequest();
    }

    /**
     * 获取Response
     */
    public HttpServletResponse getResponse(){
        return ServletUtils.getResponse();
    }

    /**
     * 获取Session
     */
    public HttpSession getSession(){
        return ServletUtils.getSession();
    }

    /**
     * 响应返回结果
     * @param rows 影响行数
     * @return 操作结果
     */
    public Response toResponse(int rows) {
        return rows>0 ? success() : error();
    }

    /**
     * 响应返回结果
     * @param result 结果
     * @return 操作结果
     */
    public Response toResponse(Boolean result) {
        return result ? success() : error();
    }

    /**
     * 响应返回分页数据
     */
    public Response getDataTable(IPage<?> page){
        Response response = success();
        response.setCount(page.getTotal());
        response.setTotalPages(page.getPages());
        response.setData(page.getRecords());
        return response;
    }

    /**
     * 返回成功
     */
    public Response success() { return Response.success();}

    /**
     * 返回失败
     */
    public Response error() { return Response.error();}


    /**
     * 返回成功信息
     */
    public Response success(String message) { return Response.success(message);}

    /**
     * 返回失败信息
     */
    public Response error(String message) { return Response.error(message);}

    /**
     * 自定义返回信息
     */
    public Response custom(ResponseEnum code) { return Response.custom(code);}
}
