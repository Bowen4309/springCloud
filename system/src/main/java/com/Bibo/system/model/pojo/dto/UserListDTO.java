package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/26 19:28
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeptListDTO对象",description = "部门扩展列表对象")
public class UserListDTO  extends PageRequest {

    /**
     * 警员用户姓名
     */
    @ApiModelProperty(example = "陈", value = "警员用户姓名")
    private String userName;

    @ApiModelProperty(example = "", value = "警员编号")
    private String policeCode;

    @ApiModelProperty(example = "", value = "部门ID")
    private String deptId;

    @ApiModelProperty(example = "4401*****",value = "网格ID")
    private String gridId;

    @ApiModelProperty(example = "",value = "角色Id")
    private List<String> roleIds;

    @ApiModelProperty(example = "",value = "修改时间")
    private String updateTime;
}
