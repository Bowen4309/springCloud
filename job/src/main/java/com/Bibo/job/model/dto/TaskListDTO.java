package com.Bibo.job.model.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TaskListDto对象",description = "任务列表对象")
public class TaskListDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(example = "110", value = "任务编号")
    private String taskNo;

}
