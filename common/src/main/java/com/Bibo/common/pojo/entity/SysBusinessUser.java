package com.Bibo.common.pojo.entity;

import lombok.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysBusinessUser {

    private String id;
    private String businessTotalId;
    private String userId;
    private String isDefault;
    private String status;
    private Date createTime;
    private String key;
}
