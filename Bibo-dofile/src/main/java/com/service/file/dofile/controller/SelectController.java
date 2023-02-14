package com.service.file.dofile.controller;

import com.service.file.dofile.entity.FileInfo;
import com.service.file.dofile.mapper.FileInfoMapper;
import com.service.file.dofile.util.Response;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.controller
 * @ClassName: SelectController
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/16 0016 16:35
 * @Version: 1.0
 */
@Api(value = "查询服务",tags={"查询服务"})
@RestController
@RequestMapping(value = "/select")
public class SelectController {

    private final FileInfoMapper fileInfoMapper;

    public SelectController(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }

    @ResponseBody
    @GetMapping(value = "/selectInfoById")
    @ApiImplicitParam(value = "附件ID",name = "fileId",required = true,paramType = "query",dataType = "String")
    @ApiOperation(value = "查询附件信息",notes = "根据附件ID查询附件信息")
    public Response selectInfoById(String fileId) {
        //根据fileId查询附件信息
        FileInfo fileInfo = fileInfoMapper.findFileById(fileId);

        return Response.ok(fileInfo);
    }


    @GetMapping(value = "selectInfoByIds")
    @ApiImplicitParam(value = "附件Id的集合",name = "fileIds", required=true)
    @ApiOperation(value = "查询多个附件信息",notes = "根据多个附件ID的集合查询附件信息")
    public Response selectInfoByIds(){
        return Response.ok("");
    }


}
