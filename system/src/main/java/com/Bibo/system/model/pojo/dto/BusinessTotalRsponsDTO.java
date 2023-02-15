package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusinessTotalReqDTO对象",description = "业务指标请求参数")
public class BusinessTotalRsponsDTO {

    @ApiModelProperty("id")
    private String id;
    
    @ApiModelProperty("指标名称")
    private String name;
    
    @ApiModelProperty("指标类型(统计指标、分析指标等)")
    private String type;
    
    @ApiModelProperty("指标统计层级（0:个人;1:中队;2:大队;3:支队）")
    private String level;
    
//    @ApiModelProperty("指标父级")
//    private String parentId;
    
    @ApiModelProperty("指标父级名称")
    private String parentName;
    
    @ApiModelProperty("指标使用状态(0:停用;1:正常)")
    private String status;
    
    @ApiModelProperty("备注注解")
    private String remark;
    
    @ApiModelProperty("创建时间")
    private Date createTime;
    
    @ApiModelProperty("修改时间")
    private Date updateTime;
    
    @ApiModelProperty("展示类型(饼图，柱状图，列表等前端自定义规则)")
    private String showType;
    
    @ApiModelProperty("指标数据秘钥(通过秘钥从数据域取数据)")
    private String key;
    
    @ApiModelProperty("数据类型(个人数据、部门统计数据、分析数据)")
    private String dataType;
    
    @ApiModelProperty("指标统计时间类型(小时、日、月、年)")
    private String timeType;
    
    @ApiModelProperty("时间值")
    private String timeNum;

    @ApiModelProperty("指标图标")
    private String log;

    @ApiModelProperty("指标例图")
    private String showPicture;

    @ApiModelProperty("是否默认选择(0:不默认1:默认)")
    private String choose;

    @ApiModelProperty("创建人")
    private String createUser;
    
    @ApiModelProperty("使用总次数")
    private String useCn;

    @ApiModelProperty("最后更新时间")
    private String endUpdateTime;

    @ApiModelProperty("指标数据更新时间")
    private String upateDate;

    @ApiModelProperty("是否已选择(1:审核中;2:已通过;3:被驳回;4:未申请;5：未选择)")
    private String ifApprovePass;
}
