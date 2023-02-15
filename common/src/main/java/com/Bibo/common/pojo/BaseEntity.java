package com.Bibo.common.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    /**
     * 状态(0正常 1停用 2删除)
     */
//    @Dict(dictCode = "sex")
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


}

