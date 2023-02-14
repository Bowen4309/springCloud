package com.service.file.dofile.service;

import com.service.file.dofile.entity.FileInfo;
import com.service.file.dofile.entity.Path;
import com.service.file.dofile.repository.UploadRepository;
import com.service.file.dofile.util.IdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.service
 * @ClassName: UploadImpl
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 10:09
 * @Version: 1.0
 */
@Component
public class UploadImpl  implements UploadRepository {

    @Autowired
    private Path path;

    @Override
    public FileInfo uploadFileSingle(MultipartFile file,String proPath,String fileId) {
        try {
        //文件名
        String fileName = file.getOriginalFilename();
        //文件后缀名（文件类型）
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        //保存的文件的新文件名
        String newFileName = IdGenerate.uuid()+suffixName;
        //基础路径
        String basePath =path.getPath();
        String previewUrl=path.getPreviewServiceUrl();
        String localUrl =path.getLocalUrl();

        //按时间给文件分类
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
        String fileDatePath =df.format(new Date());
       // fileDatePath=fileDatePath.replace("/","\\");
        //文件保存路径
        String path = basePath+proPath+"/"+fileDatePath+"/"+newFileName;
        //创建文件
        File dest = new File(path);
        //判断路径是否存在，不存在则创建
        if(!dest.getParentFile().exists()){
            //判断项目路径+时间路径是否存在
           // String[] pathStr =fileDatePath.split("\\\\");
            String[] pathStr =fileDatePath.split("/");
            String dirPath = basePath+proPath;
            for(String str :pathStr){
                File dir =new File(dirPath);
                if(!dir.exists()){
                    dir.mkdir();
                }
                dirPath =dirPath+"/"+str;
            }
            dest.getParentFile().mkdir();
        }

        file.transferTo(dest);

        FileInfo fileInfo= FileInfo.builder()
                .fileNewName(newFileName)
                .fileType(suffixName)
                .fileUrl(path)
                .previewUrl(previewUrl+"/onlinePreview?url="+
                        URLEncoder.encode(localUrl+"/preview/previewFile?fileId="+fileId+"&fullfilename="+fileId+suffixName, "utf-8"))
                .states("1")
                .build();
        return fileInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void main(String[] args) {
        String encode = null;
        try {
            encode = URLEncoder.encode("GBK编码", "utf-8");
            System.out.println("乱码" + encode);
            String decode = URLDecoder.decode(encode, "utf-8");// GBK解码
            System.out.println(decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
