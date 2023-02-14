package com.service.file.dofile.controller;

import com.service.file.dofile.entity.FileInfo;
import com.service.file.dofile.mapper.FileInfoMapper;
import com.service.file.dofile.repository.DeleteRespository;
import com.service.file.dofile.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.controller
 * @ClassName: DeleteController
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 17:37
 * @Version: 1.0
 */
@RestController
@Api(value="删除服务",tags={"删除服务"})
@RequestMapping(value = "/delete")
public class DeleteController {
    private final FileInfoMapper fileInfoMapper;
    private  final DeleteRespository deleteRespository;

    public DeleteController(FileInfoMapper fileInfoMapper, DeleteRespository deleteRespository) {
        this.fileInfoMapper = fileInfoMapper;
        this.deleteRespository = deleteRespository;
    }
    /**
     *@描述 删除文件
     *@参数
     *@返回值
     *@创建人 yuanbo
     *@创建时间 2020/4/10 0010
     */
    @GetMapping(value = "deleteSingleFile" )
    @ApiOperation(value = "删除上传附件",notes = "根据文件id删除附件信息并删除附件")
    public Response deleteSingleFile(@RequestParam("fileId") String fileId){
        FileInfo fileInfo= fileInfoMapper.findFileById(fileId);
        if(fileInfo!=null){
           boolean isSuccess=  deleteRespository.deleteFile(fileInfo.getFileUrl());
           if (isSuccess){
               fileInfoMapper.deleteFileInfo(fileId);
               return Response.ok("删除成功");
           }else{
               return Response.error("删除失败");
           }
        }else{
            return Response.error("文件不存在");
        }
    }
}
