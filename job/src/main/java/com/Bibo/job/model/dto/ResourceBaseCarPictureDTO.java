package com.Bibo.job.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceBaseCarPictureDTO {

    private String id;
    private String carNumber;
    private String carNumberTypeCode;
    private Date uploadTime;
    private Date createTime;
    private String path;

}
