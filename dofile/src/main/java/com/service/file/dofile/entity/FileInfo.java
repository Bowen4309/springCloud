package com.service.file.dofile.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.bean
 * @ClassName: FileInfo
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 9:34
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "附件信息")
public class FileInfo {

    @ApiModelProperty(value = "fileId")
    private String fileId;

    @ApiModelProperty(value = "上传后文件名称")
    private String fileNewName;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件地址")
    private String fileUrl;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "原文件名")
    private String fileName;

    @ApiModelProperty(value = "上传用户")
    private String uploadUser;

    @ApiModelProperty(value = "文件状态")
    private String states;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "预览地址")
    private String previewUrl;

}
