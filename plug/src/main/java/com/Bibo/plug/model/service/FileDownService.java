package com.Bibo.plug.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.request.PageRequest;
import com.Bibo.common.response.Response;
import com.Bibo.plug.model.dao.SysFileDown;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileDownService extends IService<SysFileDown> {


   void uploadDataFile(List<?> list, HttpServletResponse response, SysFileDown sysFileDown);

    void downFileData(HttpServletResponse response, HttpServletRequest request, String id);

    SysFileDown addDownDile(String fileName);

    Response getDownFileList(PageRequest pageRequest);
}
