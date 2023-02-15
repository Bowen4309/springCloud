package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessIndexDataReqDTO {

    private String parentName;

    private Integer level;

    private String depart;

    private String dataType;

    private String showType;

    private String key;

    private String timeType;

    private String timeNum;
}
