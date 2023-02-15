package com.Bibo.plug.model.service;

import com.Bibo.common.response.Response;
import com.Bibo.plug.model.dao.EsSearchDataByTagsDTO;
import com.Bibo.plug.model.dao.EsSearchTagByKeyWordsDTO;
import com.Bibo.plug.model.dao.SysFileDown;
import com.Bibo.plug.model.entity.EsCarTag;

import javax.servlet.http.HttpServletResponse;

public interface EsService {

    Response saveTag(EsCarTag esCarTag);

    Response  findDataByTag(EsSearchDataByTagsDTO esSearchDataByTagsDTO);

    Response  findTagByKeyWrods(EsSearchTagByKeyWordsDTO esSearchDataByTagsDTO);

    Response syncDataToEs();


    Response findPoliceInfo(String policeCode);

    Response findCompanyInfo(String code,String name);


    /**
     *  导出交通设施
     * @param esSearchDataByTagsDTO
     */
    Response  exportDataByTag(EsSearchDataByTagsDTO esSearchDataByTagsDTO, HttpServletResponse response, SysFileDown sysFileDown);

}
