package com.Bibo.system.model.service.impl;

import com.Bibo.system.model.pojo.dto.TagDTO;
import com.Bibo.system.model.pojo.entity.SysTag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.mapper.SysTagMapper;
import com.Bibo.system.model.service.ISysTagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysTagServiceImpl extends ServiceImpl<SysTagMapper, SysTag> implements ISysTagService {

    @Autowired
    private SysTagMapper sysTagMapper;

    @Override
    public void saveTag(TagDTO tagDTO) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        SysTag sysTag = new SysTag();
        BeanUtils.copyProperties(tagDTO,sysTag);
         sysTag.setCreateTime(new Date());
        sysTag.setCreateUser(userByRedis.getUserName());
        sysTagMapper.insert(sysTag);
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        SysTag sysTag = new SysTag();
        BeanUtils.copyProperties(tagDTO,sysTag);
        sysTag.setCreateTime(new Date());
        sysTag.setCreateUser(userByRedis.getUserName());
        sysTagMapper.updateById(sysTag);
    }

    @Override
    public Response deleteTag(String id) {
        sysTagMapper.deleteById(id);
        return Response.success();
    }

    @Override
    public Response getTagListByTypeId(String typeId,String status) {
        QueryWrapper<SysTag> queryWrapper = new QueryWrapper();
        queryWrapper.like("tag_type_id",typeId);
        queryWrapper.orderByAsc("index");
        if(StringUtils.isNotEmpty(status)){
            queryWrapper.eq("status",status);
        }
        List<SysTag> tagList = sysTagMapper.selectList(queryWrapper);
        return Response.success().data(tagList);
    }

    @Override
    public Response updateTagSatus(String ids, String status) {
        String[] idStrs = ids.split(";");
        for(String id:idStrs){
            sysTagMapper.updateTagStatus(id,status);
        }
        return Response.success();
    }

    public static void main(String[] args) {
        
    }
}
