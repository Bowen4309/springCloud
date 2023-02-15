package com.Bibo.system.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.mapper.SysTagMapper;
import com.Bibo.system.model.mapper.SysTagTypeMapper;
import com.Bibo.system.model.pojo.dto.TagTypeDTO;
import com.Bibo.system.model.pojo.dto.TagTypeTreeDTO;
import com.Bibo.system.model.pojo.entity.SysTag;
import com.Bibo.system.model.pojo.entity.SysTagType;
import com.Bibo.system.model.service.ISysTagTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysTagTypeServiceImpl  extends ServiceImpl<SysTagTypeMapper, SysTagType> implements ISysTagTypeService {

    @Autowired
    private SysTagTypeMapper sysTagTypeMapper;

    @Autowired
    private SysTagMapper sysTagMapper;

    @Override
    public void saveTagType(TagTypeDTO tagTypeDTO) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        SysTagType sysTagType = new SysTagType();
        BeanUtils.copyProperties(tagTypeDTO,sysTagType);
        sysTagType.setCreateTime(new Date());
        sysTagType.setCreateUser(userByRedis.getUserName());
        sysTagTypeMapper.insert(sysTagType);
    }

    @Override
    public void updateTagType(TagTypeDTO tagTypeDTO) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        SysTagType sysTagType = new SysTagType();
        BeanUtils.copyProperties(tagTypeDTO,sysTagType);
        sysTagType.setCreateTime(new Date());
        sysTagType.setCreateUser(userByRedis.getUserName());
        sysTagTypeMapper.updateById(sysTagType);
    }

    @Override
    public Response deleteTagType(String id) {
        QueryWrapper<SysTagType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<SysTagType> list = sysTagTypeMapper.selectList(queryWrapper);
        if(list!=null &&list.size()>0){
            return Response.error("该分类含有下级分类,请先删除下级分类!");
        }
        sysTagTypeMapper.deleteById(id);

        QueryWrapper<SysTag> qw = new QueryWrapper<>();
        qw.like("tag_type_id",id);
        sysTagMapper.delete(qw);
        return Response.success();
    }

    @Override
    public Response getTagTypeListTree() {
        QueryWrapper<SysTagType> queryWrapper =new QueryWrapper<>();
        queryWrapper.orderByAsc("index");
        List<SysTagType> sysTagTypes = sysTagTypeMapper.selectList(queryWrapper);
        List<TagTypeTreeDTO> treeList = new ArrayList<TagTypeTreeDTO>();
        sysTagTypes.stream().forEach(sysTagType -> {
            if("1".equals(sysTagType.getLevel())){
                TagTypeTreeDTO tagTypeTreeDTO = new TagTypeTreeDTO();
                BeanUtils.copyProperties(sysTagType,tagTypeTreeDTO);
                List<TagTypeTreeDTO> childList= treeList(sysTagTypes,sysTagType.getId());
                tagTypeTreeDTO.setChildTagType(childList);
                treeList.add(tagTypeTreeDTO);
            }
        });
        return Response.success().data(treeList);
    }

    public List<TagTypeTreeDTO> treeList (List<SysTagType> dataList, String parentId){
        List<TagTypeTreeDTO> treeList = new ArrayList<TagTypeTreeDTO>();
        dataList.forEach(child ->{
            if(StringUtils.isNotBlank(child.getParentId()) && child.getParentId().equals(parentId)){
                List<TagTypeTreeDTO> childList =  treeList(dataList,child.getId());
                TagTypeTreeDTO tagTypeTreeDTO = new TagTypeTreeDTO();
                BeanUtils.copyProperties(child,tagTypeTreeDTO);
                tagTypeTreeDTO.setChildTagType(childList);
                treeList.add(tagTypeTreeDTO);
            }
        });
        return treeList;
    }
}
