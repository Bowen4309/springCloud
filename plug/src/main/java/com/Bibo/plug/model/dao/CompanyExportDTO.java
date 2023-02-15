package com.Bibo.plug.model.dao;

import com.Bibo.common.annotation.ExportAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CompanyExportDTO",description = "企业导出封装类")
public class CompanyExportDTO {

    @ApiModelProperty("企业编号")
    @ExportAnnotation("企业编号")
    private String code;

    @ApiModelProperty("企业名称")
    @ExportAnnotation("企业名称")
    private String name;

    @ApiModelProperty("企业地址")
    @ExportAnnotation("企业地址")
    private String address;

    @ApiModelProperty("联系方式")
    @ExportAnnotation("联系方式")
    private String phone;

    @ApiModelProperty("责任大队")
    @ExportAnnotation("责任大队")
    private String placeDept;

    @ApiModelProperty("车辆数")
    @ExportAnnotation("车辆数")
    private String carNum;

    @ApiModelProperty("活跃大队")
    @ExportAnnotation("活跃大队")
    private String activeDept;

    @ApiModelProperty("标签")
    @ExportAnnotation("标签")
    private String tag;


}
