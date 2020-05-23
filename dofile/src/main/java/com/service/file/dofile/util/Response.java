package com.service.file.dofile.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ApiModel(value = "统一数据返回对象")
public class Response<T>  implements Serializable{
    //返回数据
    @ApiModelProperty(value = "返回数据")
    private T data;

    //返回状态编码
    @ApiModelProperty(value = "返回状态码")
    private String code;

    //返回信息
    @ApiModelProperty(value = "返回信息")
    private String message;


    private Response(T data, String code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    /**
     * 成功模板
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response ok(T data){
        return Response.builder().code("0")
                .message("")
                .data(data).build();
    }


    /**
     * 错误模板
     * @param message
     * @return
     */
    public static  Response error(String message){
        return Response.builder().code("-1")
                .message(message)
                .data(null).build();
    }

    /**
     * 保存数据模板
     * @param isSuccess
     * @return
     */
    public static Response saveSuccess(boolean isSuccess){
        if(isSuccess){
            return  Response.builder().code("0")
                    .message("保存成功")
                    .data(null).build();
        }else{
            return  Response.builder().code("-1")
                    .message("保存失败")
                    .data(null).build();
        }
    }

}
