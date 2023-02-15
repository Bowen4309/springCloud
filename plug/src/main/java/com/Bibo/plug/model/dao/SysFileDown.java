package com.Bibo.plug.model.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_file_down")
public class SysFileDown {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private String url;
    private String userName;
    private String userId;
    private Date createTime;
    private String status;
    private int useTime;
    private float size;

}
