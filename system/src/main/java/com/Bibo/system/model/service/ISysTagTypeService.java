package com.Bibo.system.model.service;

import com.Bibo.system.model.pojo.dto.TagTypeDTO;
import com.Bibo.common.response.Response;

public interface ISysTagTypeService {

    void saveTagType(TagTypeDTO tagTypeDTO);

    void updateTagType(TagTypeDTO tagTypeDTO);

    Response deleteTagType(String id);

    Response getTagTypeListTree();
}
