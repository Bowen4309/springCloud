package com.service.file.dofile.controller;

import com.service.file.dofile.entity.FileInfo;
import com.service.file.dofile.mapper.FileInfoMapper;
import com.service.file.dofile.repository.DownResponsitory;
import com.service.file.dofile.repository.PreviewRespository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.controller
 * @ClassName: PreviewController
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/21 0021 9:22
 * @Version: 1.0
 */
@Api(value = "预览服务",tags={"预览服务"})
@RestController
@RequestMapping(value = "/preview")
public class PreviewController {

    private final FileInfoMapper fileInfoMapper;

    private final DownResponsitory downResponsitory;

    private final PreviewRespository previewRespository;
    public PreviewController(FileInfoMapper fileInfoMapper, DownResponsitory downResponsitory, PreviewRespository previewRespository) {
        this.fileInfoMapper = fileInfoMapper;
        this.downResponsitory = downResponsitory;
        this.previewRespository = previewRespository;
    }

    // private HttpServletResponse response;


    @GetMapping(path = "/previewFile")
    @ApiOperation(value = "预览word文档" ,notes = "查询文档信息后下载通过kkfileview插件预览")
    public void previewDocFile(@RequestParam("fileId") String fileId, HttpServletResponse response) {
        FileInfo fileInfo = fileInfoMapper.findFileById(fileId);
        if(fileInfo!=null&&fileInfo.getStates().equals("2")) {
            try {
               // if(fileInfo.getFileType().equals(".doc")||fileInfo.getFileType().equals(".docx")){//判断是wodrd
                   downResponsitory.downFile(fileInfo.getFileUrl(), fileInfo.getFileName(), response);
                //}
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    @GetMapping(path = "/previewPdfFile")
    @ApiOperation(value = "预览PDF文档" ,notes = "通过文件ID查询文档信息后预览")
    public void previewPdfFile(@RequestParam("fileId") String fileId, HttpServletResponse response) {
        FileInfo fileInfo = fileInfoMapper.findFileById(fileId);
        if(fileInfo!=null&&fileInfo.getStates().equals("2")) {
                    previewRespository.previePdfFile(fileInfo.getFileUrl(), fileInfo.getFileName(), response);
        }
    }
}
