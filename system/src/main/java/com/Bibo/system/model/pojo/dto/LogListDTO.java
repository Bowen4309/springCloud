package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/27 14:29
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LogListDTO对象",description = "日志查询列表对象")
public class LogListDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作状态
     */
    @ApiModelProperty(example = "0", value = "操作状态")
    private String operStatus;

    /**
     * 最小操作时间
     */
    @ApiModelProperty(example = "2021-10-27 14:59:00", value = "最小操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date minOperTime;

    /**
     * 最大操作时间
     */
    @ApiModelProperty(example = "2020-10-27 14:59:00", value = "最大操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxOperTime;

    /**
     * 操作人
     */
    @ApiModelProperty(example = "admin", value = "操作人")
    private String operName;


}
