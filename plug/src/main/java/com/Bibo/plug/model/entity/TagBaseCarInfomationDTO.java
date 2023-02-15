package com.Bibo.plug.model.entity;

import com.Bibo.common.annotation.ExportAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TagBaseCarInfomationDTO", description = "标签车辆基本信息")
public class TagBaseCarInfomationDTO {
    @ApiModelProperty("id")
    @ExportAnnotation("id")
    private String id;
    @ApiModelProperty("中文品牌")
    @ExportAnnotation("中文品牌")
    private String brandName;
    @ApiModelProperty("车辆型号")
    @ExportAnnotation("车辆型号")
    private String carMode;
    @ApiModelProperty("车辆类型")
    @ExportAnnotation("车辆类型")
    private String carType;
    @ApiModelProperty("车辆识别编码")
    @ExportAnnotation("车辆识别编码")
    private String carCode;
    @ApiModelProperty("发动机号")
    @ExportAnnotation("发动机号")
    private String engineNumber;
    @ApiModelProperty("车身颜色")
    @ExportAnnotation("车身颜色")
    private String carColour;
    @ApiModelProperty("使用性质")
    @ExportAnnotation("使用性质")
    private String useWay;
    @ApiModelProperty("出厂时间")
    @ExportAnnotation(value = "出厂时间",type = "date")
    private Date outFactoryTime;
    @ApiModelProperty("强制报废时间")
    @ExportAnnotation(value = "强制报废时间",type = "date")
    private Date mustScrapTime;
    @ApiModelProperty("检测有效截止日期")
    @ExportAnnotation(value = "检测有效截止日期",type = "date")
    private Date checkEndTime;
    @ApiModelProperty("保险终止日期")
    @ExportAnnotation(value = "保险终止日期",type = "date")
    private Date safeEndTime;
    @ApiModelProperty("机动车状态")
    @ExportAnnotation("机动车状态")
    private String carStatus;
    @ApiModelProperty("机动车所有人")
    @ExportAnnotation("机动车所有人")
    private String ownerCarName;
    @ApiModelProperty("详细住址")
    @ExportAnnotation("详细住址")
    private String liveDetailAddress;
    @ApiModelProperty("初次登记时间")
    @ExportAnnotation(value = "初次登记时间",type = "date")
    private Date startLoginTime;
    @ApiModelProperty("手机号码")
    @ExportAnnotation("手机号码")
    private String phoneNumber;
    @ApiModelProperty("国产/进口")
    @ExportAnnotation("国产/进口")
    private String ifModestic;
    @ApiModelProperty("事故逃逸记录")
    @ExportAnnotation("事故逃逸记录")
    private String eventLog;
    @ApiModelProperty("套牌车")
    @ExportAnnotation("套牌车")
    private String coverCar;
    @ApiModelProperty("被盗抢嫌疑记录")
    @ExportAnnotation("被盗抢嫌疑记录")
    private String loseLog;
    @ApiModelProperty("合格标志编号")
    @ExportAnnotation("合格标志编号")
    private String qualifiedCode;

    @ApiModelProperty("行驶证新品编号")
    @ExportAnnotation("行驶证新品编号")
    private String driveChipCode;

    @ApiModelProperty("车牌号码")
    @ExportAnnotation("车牌号码")
    private String carNumber;

    @ApiModelProperty("号牌种类代码")
    @ExportAnnotation("号牌种类代码")
    private String carNumberTypeCode;
    @ApiModelProperty("号牌颜色")
    @ExportAnnotation("号牌颜色")
    private String carNumberColor;

    @ApiModelProperty("标签")
    @ExportAnnotation("标签")
    private  String tags;

}
