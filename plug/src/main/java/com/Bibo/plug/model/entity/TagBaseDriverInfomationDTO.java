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
@ApiModel(value = "TagBaseDriverInfomationDTO", description = "标签驾驶人基本信息")
public class TagBaseDriverInfomationDTO {
    @ExportAnnotation("主键id")
    @ApiModelProperty("主键id")
    private String id;
    @ExportAnnotation("姓名")
    @ApiModelProperty("姓名")
    private String name;
    @ExportAnnotation("身份证")
    @ApiModelProperty("身份证")
    private String idCard;
    @ExportAnnotation("性别")
    @ApiModelProperty("性别")
    private String sex;
    @ExportAnnotation(value = "出生日期",type = "date")
    @ApiModelProperty("出生日期")
    private Date birthTime;
    @ExportAnnotation("地址")
    @ApiModelProperty("地址")
    private String liveAddress;
    @ExportAnnotation("电话号码")
    @ApiModelProperty("电话号码")
    private String phone;
    @ExportAnnotation("数据来源")
    @ApiModelProperty("数据来源")
    private String dataFrom;
    @ExportAnnotation("车牌号")
    @ApiModelProperty("车牌号")
    private String carNumber;
    @ExportAnnotation("驾驶证状态")
    @ApiModelProperty("驾驶证状态")
    private String drivingLicenceStatus;
    @ExportAnnotation("驾驶证状态中文")
    @ApiModelProperty("驾驶证状态中文")
    private String drivingLicenceStatusCn;
    @ExportAnnotation(value = "驾驶证有效期止",type = "date")
    @ApiModelProperty("驾驶证有效期止")
    private Date drivingLicenceEndtime;
    @ExportAnnotation("准驾车型")
    @ApiModelProperty("准驾车型")
    private String permitCarType;
    @ExportAnnotation("准驾车型中文")
    @ApiModelProperty("准驾车型中文")
    private String permitCarTypeCn;
    @ExportAnnotation(value = "初次领证日期",type = "date")
    @ApiModelProperty("初次领证日期")
    private Date firstGetLicenceTime;
    @ExportAnnotation(value = "入库时间",type = "date")
    @ApiModelProperty("入库时间")
    private Date createTime;
    @ExportAnnotation(value = "更新时间",type = "date")
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ExportAnnotation("发证机关")
    @ApiModelProperty("发证机关")
    private String licenceIssuingAuthority;
    @ExportAnnotation("档案编号")
    @ApiModelProperty("档案编号")
    private String archivesNo;
    @ExportAnnotation("驾驶证期限")
    @ApiModelProperty("驾驶证期限")
    private String drivingLicenceDeadline;
    @ExportAnnotation("累计计分")
    @ApiModelProperty("累计计分")
    private String allScore;
    @ExportAnnotation("联系地址")
    @ApiModelProperty("联系地址")
    private String contactAddress;
    @ExportAnnotation("驾驶证编号")
    @ApiModelProperty("驾驶证编号")
    private String drivingLicenceNumber;
    @ExportAnnotation(value = "驾驶证有效期始",type = "date")
    @ApiModelProperty("驾驶证有效期始")
    private Date drivingLicenceStarttime;
    @ExportAnnotation("证芯编号")
    @ApiModelProperty("证芯编号")
    private String chipNo;
    @ExportAnnotation(value = "下一次体检日期",type = "date")
    @ApiModelProperty("下一次体检日期")
    private Date nextPhysicalExaminationTime;
    @ExportAnnotation(value = "审验有效期止",type = "date")
    @ApiModelProperty("审验有效期止")
    private Date auditValidEndtime;
    @ExportAnnotation("邮政编码")
    @ApiModelProperty("邮政编码")
    private String postalCode;
    @ExportAnnotation("暂时居住证明")
    @ApiModelProperty("暂时居住证明")
    private String transientLiveCerify;
    @ExportAnnotation("国籍")
    @ApiModelProperty("国籍")
    private String nationality;
    @ExportAnnotation("驾校名称")
    @ApiModelProperty("驾校名称")
    private String drivingSchoolName;

    @ExportAnnotation(value = "清分日期",type = "date")
    @ApiModelProperty("清分日期")
    private Date clearScoreTime;
    @ExportAnnotation(value = "驾驶证状态更新时间",type = "date")
    @ApiModelProperty("驾驶证状态更新时间")
    private Date licenceUpdateTime;

    @ExportAnnotation(value = "标签")
    @ApiModelProperty("标签")
    private String tags;
    
}
