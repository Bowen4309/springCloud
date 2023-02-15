package com.Bibo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.Bibo.common.constant.ResponseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
* @Description: 数据返回工具类
* @Author: yuanbo
* @Date: 2021/7/22
*/
@Data
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    //状态编码
    @ApiModelProperty("状态编码")
    private String code;

    //消息说明
    @ApiModelProperty("消息说明")
    private String msg;

    //数据条数
    @ApiModelProperty("数据条数")
    private Long count;

    //数据总页数
    @ApiModelProperty("数据总页数")
    private Long totalPages;

    //数据对象
    @ApiModelProperty("数据对象")
    private T data;

    //统计数据
    @ApiModelProperty("统计数据")
    private List<Map<String,Object>> totalRow;

    //使用自定义状态
    public static Response custom(ResponseEnum resultCode){
        Response result  = new Response();
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMsg());
        return result;
    }

    //操作成功
    public static Response success(){
        Response result  = new Response();
        result.setCode(ResponseEnum.SUCCESS.getCode());
        result.setMsg(ResponseEnum.SUCCESS.getMsg());
        return result;
    }
    public static Response success(String msg){
        Response result  = new Response();
        result.setCode(ResponseEnum.SUCCESS.getCode());
        result.setMsg(msg);
        return result;
    }

    //操作失败
    public static Response error(){
        Response result  = new Response();
        result.setCode(ResponseEnum.ERROR.getCode());
        result.setMsg(ResponseEnum.ERROR.getMsg());
        return result;
    }
    public static Response error(String msg){
        Response result  = new Response();
        result.setCode(ResponseEnum.ERROR.getCode());
        result.setMsg(msg);
        return result;
    }

    //链式添加data
    public Response data(T data){
       // DictionaryConversionUtil.converter(data);
        this.setData(data);
        return this;
    }

    /**
     * 链式添加data
     * @param data 数组数据
     * @param count 总数
     * @param totalPage 总页数
     */
    public Response data(T data, Long count, Long totalPage){
      //  DictionaryConversionUtil.converter(data);
        this.setData(data);
        this.setCount(count);
        this.setTotalPages(totalPage);
        return this;
    }

    //链式添加totalRow
    public Response totalRow(List<Map<String,Object>> totalRow){
        this.setTotalRow(totalRow);
        return this;
    }

    public Response(){}
    public Response(T data) {
        this(ResponseEnum.SUCCESS, data);
    }

    public Response(ResponseEnum resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Response(ResponseEnum resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

}
