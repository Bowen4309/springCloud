package com.Bibo.system.model.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysTag {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String tagName;
    private String tagTypeId;
    private String tagTypeName;
    private String status;
    private Integer index;
    private String describe;
    private String modelName;
    private String isCalculation;
    private String objectName;
    private String objectId;
    private Date createTime;
    private String createUser;
    private String detail;
}
