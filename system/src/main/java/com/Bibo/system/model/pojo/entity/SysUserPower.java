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
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserPower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 权限id
     */
    private String powerId;
}
