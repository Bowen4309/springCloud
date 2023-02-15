package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.BusinessIndexReqDTO;
import com.Bibo.system.model.pojo.dto.BusinessTotalListReqDTO;
import com.Bibo.system.model.pojo.dto.BusinessTotalReqDTO;
import com.Bibo.system.model.pojo.entity.SysBusinessTotal;
import com.Bibo.common.pojo.entity.SysBusinessUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface ISysBusinessTotalService extends IService<SysBusinessTotal> {

    Response getTypeList();

    Response getBusinessList(BusinessTotalListReqDTO businessTotalReqDTO);

    Response getTypeAndChildList();

    Response findById(String id);

    void updateData(BusinessTotalReqDTO bussinessTotalReqDTO);

    void saveData(BusinessTotalReqDTO bussinessTotalReqDTO);

    void deleteData(String id);


    void initUserBusinessTotal();

    void saveUserBusinessTotal(List<SysBusinessUser> sysBusinessUsers);

    void updateStatus(String ids,String status);

    Response findApproveData();

    void addIndexList(String id);

    void removeIndex(String id);

    Response uploadPicture(MultipartFile file);


    Response getPicture(String path, HttpServletRequest request, HttpServletResponse response);

    Response getIndexData(BusinessIndexReqDTO businessIndexReqDTO);

}
