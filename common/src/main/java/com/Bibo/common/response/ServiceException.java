package com.Bibo.common.response;

import com.Bibo.common.constant.ResponseEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    @ApiModelProperty(value = "错误码")
    private String code;
    @ApiModelProperty(value = "提示消息")
    private String msg;

    public ServiceException() {
        this(ResponseEnum.API_ERROR);
    }


    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException(ResponseEnum exception) {
        super(exception.getMsg());
        this.code = exception.getCode();
        this.msg = exception.getMsg();
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}


