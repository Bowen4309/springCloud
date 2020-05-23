package com.service.file.dofile.controller;

import com.service.file.dofile.entity.FileInfo;
import com.service.file.dofile.repository.DownResponsitory;
import com.service.file.dofile.mapper.FileInfoMapper;
import com.service.file.dofile.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.controller
 * @ClassName: DownController
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 15:43
 * @Version: 1.0
 */
@Api(value = "下载服务",tags={"下载服务"})
@RestController
@RequestMapping(value = "/down")
public class DownController {

    private final FileInfoMapper fileInfoMapper;

    private final  DownResponsitory downResponsitory;

   // private HttpServletResponse response;

    public DownController(FileInfoMapper uploadInfoRepository, DownResponsitory downResponsitory) {
        this.fileInfoMapper = uploadInfoRepository;
        this.downResponsitory = downResponsitory;
    }

    @GetMapping(path = "/downFile")
    @ApiOperation(value = "下载文件" ,notes = "通过文件ID下载文件")
    public void downFile(@RequestParam("fileId") String fileId, HttpServletResponse response) {
        FileInfo fileInfo = fileInfoMapper.findFileById(fileId);
        if(fileInfo!=null&&fileInfo.getStates().equals("2")) {

            try {
                 downResponsitory.downFile(fileInfo.getFileUrl(), fileInfo.getFileName(), response);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }
}
