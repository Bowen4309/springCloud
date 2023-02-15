package com.Bibo.plug.model.dao;

import com.Bibo.common.annotation.ExportAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FacilityExportDTO",description = "交通设施导出封装类")
public class FacilityExportDTO {

    @ApiModelProperty("设施名称")
    @ExportAnnotation("设施名称")
    private String name;

    @ApiModelProperty("设施编号")
    @ExportAnnotation("设施编号")
    private String facilityId;

    @ApiModelProperty("标签")
    @ExportAnnotation("标签")
    private String tag;

    @ApiModelProperty("设施地址")
    @ExportAnnotation("设施地址")
    private String address;

    @ApiModelProperty("所属网格编号")
    @ExportAnnotation("所属网格编号")
    private String gridId;

    @ApiModelProperty("所属网格名称")
    @ExportAnnotation("所属网格名称")
    private String gridName;

    @ApiModelProperty("所属部门编号")
    @ExportAnnotation("所属部门编号")
    private String deptId;

    @ApiModelProperty("所属部门名称")
    @ExportAnnotation("所属部门名称")
    private String deptName;

    @ApiModelProperty("纬度")
    @ExportAnnotation("纬度")
    private String latitude;

    @ApiModelProperty("经度")
    @ExportAnnotation("经度")
    private String longitude;



}
