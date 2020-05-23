package com.service.file.dofile.mapper;

import com.service.file.dofile.entity.FileInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.repository
 * @ClassName: UploadInfo
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 14:05
 * @Version: 1.0
 */
@Mapper
@Component
public interface FileInfoMapper {

    /**
     *@描述 新增文件信息
     *@参数
     *@返回值
     *@创建人 yuanbo
     *@创建时间 2020/4/10 0010
     */
    @Insert({"insert into T_SERVICE_FILE(FILE_ID, FILE_NAME, FILE_URL, UPLOAD_USER, UPLOAD_TIME, FILE_SIZE, FILE_TYPE,FILE_NEW_NAME,STATES)" +
            "values(#{fileId},#{fileName},#{fileUrl},#{uploadUser},#{uploadTime},#{fileSize},#{fileType},#{fileNewName},#{states})"})
    int saveFileInfo(FileInfo fileInfo);

    /**
     *@描述 修改文件信息和状态
     *@参数
     *@返回值
     *@创建人 yuanbo
     *@创建时间 2020/4/10 0010
     */
    @Update({"<script>update T_SERVICE_FILE <set>" +
            "<if test='states!=null'>STATES = #{states},</if>" +
            "<if test='fileNewName!=null'>FILE_NEW_NAME =#{fileNewName},</if>" +
            "<if test='fileUrl!=null'>FILE_URL=#{fileUrl},</if>" +
            "<if test='fileType!=null'>FILE_TYPE=#{fileType},</if>"+
            "<if test='previewUrl!=null'>PREVIEW_URL=#{previewUrl},</if>"+
            "</set> where FILE_ID = #{fileId}</script>"})
    int updateFileState(FileInfo fileInfo);

    /**
     *@描述 删除文件信息
     *@参数
     *@返回值
     *@创建人 yuanbo
     *@创建时间 2020/4/10 0010
     */
    @Delete({"delete from T_SERVICE_FILE where FILE_ID = #{fileId}"})
    int deleteFileInfo(String fileId);

    /**
     *@描述 查询单个文件信息
     *@参数
     *@返回值
     *@创建人 yuanbo
     *@创建时间 2020/4/10 0010
     */
    @Select({"select FILE_ID," +
            "FILE_NAME," +
            "FILE_URL," +
            "UPLOAD_USER," +
            "UPLOAD_TIME," +
            "FILE_SIZE," +
            "FILE_TYPE," +
            "FILE_NEW_NAME," +
            "PREVIEW_URL,"+
            "STATES from T_SERVICE_FILE where FILE_ID = #{fileId}"})
    @Results({
        @Result(property = "fileId", column = "FILE_ID"),
        @Result(property = "fileName", column = "FILE_NAME"),
        @Result(property = "fileUrl", column = "FILE_URL"),
        @Result(property = "uploadUser", column = "UPLOAD_USER"),
        @Result(property = "uploadTime", column = "UPLOAD_TIME"),
        @Result(property = "fileSize", column = "FILE_SIZE"),
        @Result(property = "fileType", column = "FILE_TYPE"),
        @Result(property = "fileNewName", column = "FILE_NEW_NAME"),
        @Result(property = "previewUrl", column = "PREVIEW_URL"),
        @Result(property = "states", column = "STATES")
    })
    FileInfo findFileById(String fileId);
}
