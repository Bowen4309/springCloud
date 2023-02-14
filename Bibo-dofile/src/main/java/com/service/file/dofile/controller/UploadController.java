package com.service.file.dofile.controller;

import com.service.file.dofile.entity.FileInfo;
import com.service.file.dofile.mapper.FileInfoMapper;
import com.service.file.dofile.repository.UploadRepository;
import com.service.file.dofile.util.IdGenerate;
import com.service.file.dofile.util.Response;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.controller
 * @ClassName: uploadController
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/9 0009 18:54
 * @Version: 1.0
 */

@RestController
@Api(value = "上传服务",tags={"上传服务"})
@RequestMapping("/upload")
public class UploadController {

    private final UploadRepository uploadRepository;
    private final FileInfoMapper fileInfoMapper;

    public UploadController(UploadRepository uploadRepositoryImpl, FileInfoMapper uploadInfo) {
        this.uploadRepository = uploadRepositoryImpl;
        this.fileInfoMapper = uploadInfo;
    }

    /**
     *@描述 单上传文件
     *@参数
     *@返回值
     *@创建人 yuanbo
     *@创建时间 2020/4/9 0009
     */
    @ResponseBody
    @ApiOperation(value = "上传附件",notes = "单文件上传附件")
    @PostMapping(value = "singleFile/{pro}", consumes =MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "项目名称或上传文件的根目录", name="pro",required = true,paramType = "path",dataType = "String")
    })
    public Response singleFile(@ApiParam(value = "上传的文件", required = true) @RequestParam("file") MultipartFile file,@PathVariable String pro){
        if(file.isEmpty()){
            return Response.error("上传文件为空");
        }else{
           String fileId = IdGenerate.uuid();
           //新增上传数据状态为未上传 0
            fileInfoMapper.saveFileInfo(FileInfo.builder().
                    fileId(fileId)
                    .states("0")
                    .fileName(file.getOriginalFilename())
                    .uploadTime(LocalDateTime.now())
                    .fileSize(file.getSize())
                    .build());

           //上传文件
            FileInfo fileInfo= uploadRepository.uploadFileSingle(file,pro,fileId);

            if(fileInfo!=null){
                //修改上传状态为上传成功 1 并更新上传文件得信息
                fileInfoMapper.updateFileState(FileInfo.builder()
                                            .fileId(fileId)
                                            .fileNewName(fileInfo.getFileNewName())
                                            .fileType(fileInfo.getFileType())
                                            .fileUrl(fileInfo.getFileUrl())
                                            .previewUrl(fileInfo.getPreviewUrl())
                                            .states("1").build());
                return Response.ok(fileId);
            }else{
                //上传失败删除上传数据
                fileInfoMapper.deleteFileInfo(fileId);
                return Response.error("上传失败");
            }
        }
    }


    @ResponseBody
    @ApiOperation(value = "确认保存",notes = "确认保存附件")
    @GetMapping(value = "sureSaveSuccess")
    public Response sureSaveSuccess(String fileId){

         fileInfoMapper.updateFileState(FileInfo.builder().fileId(fileId).states("2").build());

        return Response.ok("上传成功");
    }

}
