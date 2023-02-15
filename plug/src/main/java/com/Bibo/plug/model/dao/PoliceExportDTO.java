package com.Bibo.plug.model.dao;

import com.Bibo.common.annotation.ExportAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PoliceExportDTO",description = "警员导出封装类")
public class PoliceExportDTO {

    @ApiModelProperty("警员姓名")
    @ExportAnnotation("警员姓名")
    private String name;

    @ApiModelProperty("警号")
    @ExportAnnotation("警号")
    private String policeCode;

    @ApiModelProperty("所属单位")
    @ExportAnnotation("所属单位")
    private String deptName;

    @ApiModelProperty("警员性别")
    @ExportAnnotation("警员性别")
    private String sex;

    @ApiModelProperty("标签")
    @ExportAnnotation("标签")
    private String tag;

    @ApiModelProperty("所属网格编号")
    @ExportAnnotation("所属网格编号")
    private String gridId;

    @ApiModelProperty("所属网格名称")
    @ExportAnnotation("所属网格名称")
    private String gridName;


}
