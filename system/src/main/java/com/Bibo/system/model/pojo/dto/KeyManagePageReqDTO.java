package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "KeyManagePageReqDTO对象",description = "密钥列表入参对象")
public class KeyManagePageReqDTO extends PageRequest {

    @ApiModelProperty(value = "使用部门")
    private String depart;

}
