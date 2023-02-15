package com.Bibo.system.model.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRolePower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String powerId;


}
