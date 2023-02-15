package com.Bibo.system.model.service;

import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.TagDTO;

public interface ISysTagService {

    void saveTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    Response deleteTag(String id);

    Response getTagListByTypeId(String typeId,String status);

    Response updateTagSatus(String ids,String status);
}
