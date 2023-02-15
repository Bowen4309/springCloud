package com.Bibo.plug.model.dao;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "EsSearchTagByKeyWordsDTO",description = "通过关键字查询标签")
public class EsSearchTagByKeyWordsDTO extends PageRequest {

    @ApiModelProperty(value = "关键字(车牌号码、身份证等)", required = true)
    private String keyWords;

    @ApiModelProperty(value = "对象名称(机动车、驾驶人等)", required = true)
    private String objectName;
}
