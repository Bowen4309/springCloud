package com.Bibo.plug.model.dao;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "EsSearchByTagsDTO",description = "通过标签分页查询数据对象")
public class EsSearchDataByTagsDTO extends PageRequest {

    @ApiModelProperty("标签")
    private List<String> tags;

    @ApiModelProperty("对象名称(机动车、驾驶人等)")
    private String objectName;
}
