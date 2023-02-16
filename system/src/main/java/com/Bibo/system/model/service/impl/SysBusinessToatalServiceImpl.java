package com.Bibo.system.model.service.impl;

import com.Bibo.system.model.pojo.dto.*;
import com.Bibo.system.model.pojo.entity.SysBusinessTotal;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.ApiTypeBusinessIndexEnum;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysUser;
import com.Bibo.common.response.ApiResponse;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.*;
import com.Bibo.system.model.mapper.SysBusinessTotalMapper;
import com.xdh.traffic_system.model.pojo.dto.*;
import com.Bibo.common.pojo.entity.SysBusinessUser;
import com.Bibo.system.model.service.ISysBusinessTotalService;
import com.Bibo.system.model.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysBusinessToatalServiceImpl extends ServiceImpl<SysBusinessTotalMapper, SysBusinessTotal> implements ISysBusinessTotalService {


    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysBusinessTotalMapper sysBusinessTotalMapper;

    @Override
    public Response getTypeList() {
        QueryWrapper<SysBusinessTotal> queryWrapper = new QueryWrapper();
        queryWrapper.isNull("parent_name");
        List<SysBusinessTotal> parentList = this.list(queryWrapper);
        List<String> names = parentList.stream().map(SysBusinessTotal::getName).collect(Collectors.toList());
        return Response.success().data(names);
    }

    @Override
    public Response<List<BusinessTotalRsponsDTO>> getBusinessList(BusinessTotalListReqDTO businessTotalReqDTO) {
        QueryWrapper qw = new QueryWrapper();
        qw.isNotNull("parent_name");
        if(null != businessTotalReqDTO.getName() && !businessTotalReqDTO.getName().equals("")){
            qw.like("name",businessTotalReqDTO.getName());
        }

        if(null != businessTotalReqDTO.getStatus() && !businessTotalReqDTO.getStatus().equals("")){
            qw.eq("status",businessTotalReqDTO.getStatus());
        }
        if(StringUtils.isNotBlank(businessTotalReqDTO.getEndDate())){
            qw.le("create_time", DateUtils.getDateByStr(businessTotalReqDTO.getEndDate()));
        }
        if(StringUtils.isNotBlank(businessTotalReqDTO.getStartDate())){
            qw.ge("create_time",DateUtils.getDateByStr(businessTotalReqDTO.getStartDate()));
        }

        qw.orderByDesc("update_time");

        Page page = new Page(businessTotalReqDTO.getPageNum(),businessTotalReqDTO.getPageSize());
        IPage<SysBusinessTotal> sysBusinessTotalIPage = this.page(page,qw);
        List<BusinessTotalRsponsDTO> data = new ArrayList<BusinessTotalRsponsDTO>();
        sysBusinessTotalIPage.getRecords().forEach(sysBusinessTotal -> {
            //业务指标对象转化DTO
            BusinessTotalRsponsDTO businessTotalRsponsDTO = new BusinessTotalRsponsDTO();
            BeanUtils.copyProperties(sysBusinessTotal,businessTotalRsponsDTO);
            data.add(businessTotalRsponsDTO);
        });
        return Response.success().data(data);
    }


    @Override
    public Response<List<BusinessTotalListResponDTO>> getTypeAndChildList() {
        QueryWrapper<SysBusinessTotal> queryWrapper = new QueryWrapper();
        queryWrapper.isNull("parent_name");
        List<SysBusinessTotal> parentList = this.list(queryWrapper);
        QueryWrapper<SysBusinessTotal> queryWrapper1 = new QueryWrapper();
        queryWrapper1.isNotNull("parent_name");
        queryWrapper1.eq("status","1");
        List<SysBusinessTotal> childList = this.list(queryWrapper1);
        List<BusinessTotalListResponDTO> data = new ArrayList<BusinessTotalListResponDTO>();
        //使用总人数
        List<Map<String,Object>> useTotal = sysBusinessTotalMapper.totalUse();
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        List<BusinessTotalApproveDTO> approveIndexs = sysBusinessTotalMapper.findApproveData(userByRedis.getUserId());
        //指标类型匹配指标
        parentList.forEach(parent ->{
            List<BusinessTotalRsponsDTO> responsDTO = new ArrayList<BusinessTotalRsponsDTO>();
            //业务指标对象转化DTO
            childList.forEach(child -> {
                if(child.getParentName().equals(parent.getName())){
                    BusinessTotalRsponsDTO businessTotalRsponsDTO = new BusinessTotalRsponsDTO();
                    BeanUtils.copyProperties(child,businessTotalRsponsDTO);

                    useTotal.forEach(ut ->{
                        if(ut.get("business_total_id").toString().equals(child.getId())){
                            businessTotalRsponsDTO.setUseCn(ut.get("cn").toString());
                        }
                    });
                    businessTotalRsponsDTO.setIfApprovePass("5");
                    approveIndexs.forEach(approveIndex ->{
                        if(approveIndex.getId().equals(child.getId())){
                            businessTotalRsponsDTO.setIfApprovePass(approveIndex.getStatus());
                        }
                    });
                    responsDTO.add(businessTotalRsponsDTO);
                }

            });
            //赋值
            BusinessTotalListResponDTO businessTotalListResponDTO =  BusinessTotalListResponDTO.builder()
                    .childBusinessTotal(responsDTO)
                    .id(parent.getId())
                    .name(parent.getName()).build();
            data.add(businessTotalListResponDTO);
        });
        return Response.success().data(data);
    }

    @Override
    public Response findById(String id) {
        SysBusinessTotal sysBusinessTotal= this.getById(id);
        BusinessTotalRsponsDTO businessTotalRsponsDTO = new BusinessTotalRsponsDTO();
        BeanUtils.copyProperties(sysBusinessTotal,businessTotalRsponsDTO);
        return Response.success().data(businessTotalRsponsDTO);
    }

    @Override
    public void updateData(BusinessTotalReqDTO businessTotalReqDTO) {
        SysBusinessTotal businessTotal = new SysBusinessTotal();
        BeanUtils.copyProperties(businessTotalReqDTO,businessTotal);
        SysBusinessTotal oldSysBusinessTotal =this.getById(businessTotalReqDTO.getId());
        if(!oldSysBusinessTotal.getChoose().equals(businessTotalReqDTO.getChoose())){
            if(businessTotalReqDTO.getChoose().equals("1")){
                chooseDefaultIndex(businessTotalReqDTO.getId(),businessTotalReqDTO.getKey());
            }else if(businessTotalReqDTO.getChoose().equals("0")){
                //删除用户选中的该指标
                sysBusinessTotalMapper.removeUserChoose(businessTotalReqDTO.getId());
            }
        }
        businessTotal.setUpdateTime(new Date());
        this.updateById(businessTotal);
    }

    @Override
    public void saveData(BusinessTotalReqDTO businessTotalReqDTO) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        SysBusinessTotal businessTotal = new SysBusinessTotal();
        BeanUtils.copyProperties(businessTotalReqDTO,businessTotal);
        String id = UUID.randomUUID().toString().replace("-","");
        businessTotal.setId(id);
        businessTotal.setStatus("0");
        businessTotal.setCreateTime(new Date());
        businessTotal.setUpdateTime(new Date());
        businessTotal.setCreateUser(userByRedis.getUserName());
        this.save(businessTotal);

    }

    public void chooseDefaultIndex(String id,String key){
        List<SysUser> users = userService.list();
        List<SysBusinessUser> sysBusinessUsers =users.stream().map(user ->{
            SysBusinessUser businessUser = SysBusinessUser.builder()
                    .id(UUID.randomUUID().toString().replace("-",""))
                    .userId(user.getUserId())
                    .isDefault("0")
                    .status("1")
                    .createTime(new Date())
                    .key(key)
                    .businessTotalId(id).build();
            return businessUser;
        }).collect(Collectors.toList());
        //新增默认字段
        saveUserBusinessTotal(sysBusinessUsers);
    }

    @Override
    public void deleteData(String id) {
        //删除指标
        this.removeById(id);
        //删除用户选中的该指标
        sysBusinessTotalMapper.removeUserChoose(id);
    }


    @Override
    public void initUserBusinessTotal() {
        //获取未设置默认指标用户
        List<String> userIds = sysBusinessTotalMapper.getNoDefault();
        //获取默认指标
        QueryWrapper<SysBusinessTotal> queryWrapper = new QueryWrapper();
        queryWrapper.eq("choose","1");
        List<SysBusinessTotal> defaultTotal = this.list(queryWrapper);
        userIds.forEach(id -> { //循环用户保存用户的默认选中指标
            List<SysBusinessUser> sysBusinessUsers = defaultTotal.stream().map(sysBusinessTotal ->{
                    SysBusinessUser businessUser = SysBusinessUser.builder()
                            .id(UUID.randomUUID().toString().replace("-",""))
                            .userId(id)
                            .isDefault("0")
                            .status("1")
                            .createTime(new Date())
                            .key(sysBusinessTotal.getKey())
                            .businessTotalId(sysBusinessTotal.getId()).build();
                return businessUser;
            }).collect(Collectors.toList());
            saveUserBusinessTotal(sysBusinessUsers);
        });
    }

    @Override
    public void saveUserBusinessTotal(List<SysBusinessUser> sysBusinessUsers) {
        sysBusinessTotalMapper.saveUserBusinessTotal(sysBusinessUsers);
    }

    @Override
    public void updateStatus(String ids, String status) {
        List<String> idList = Arrays.asList(ids.split(";"));

        if(status.equals("0")){
            //删除用户选中的该指标
            idList.forEach(id ->{
                sysBusinessTotalMapper.removeUserChoose(id);
            });
        }else if(status.equals("1")){
           List<SysBusinessTotal> businessTotals = sysBusinessTotalMapper.findByIndexIds(idList);
            businessTotals.forEach(businessTotal ->{
                if(businessTotal.getChoose().equals("1")){
                    //默认指标添加用户
                    idList.forEach(id ->{
                        chooseDefaultIndex(id,businessTotal.getKey());
                    });
                }
            });

        }
        sysBusinessTotalMapper.updateStatus(idList,status);
    }

    @Override
    public Response<List<BusinessTotalApproveDTO>> findApproveData() {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        return Response.success().data(sysBusinessTotalMapper.findApproveData(userByRedis.getUserId()));
    }

    @Override
    public void addIndexList(String ids) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        String[] idList =ids.split(";");
        List<SysBusinessUser> sysBusinessUsers = new ArrayList<SysBusinessUser>();
        for(String id :idList){
            SysBusinessUser sysBusinessUser =SysBusinessUser.builder()
                    .id(UUID.randomUUID().toString().replace("-",""))
                    .businessTotalId(id)
                    .isDefault("1")
                    .userId(userByRedis.getUserId())
                    .status("0")
                    .createTime(new Date())
                    .build();
            sysBusinessUsers.add(sysBusinessUser);
        }
        sysBusinessTotalMapper.saveUserBusinessTotal(sysBusinessUsers);

    }

    @Override
    public void removeIndex(String ids) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        String[] idList =ids.split(";");
        for(String id :idList){
            sysBusinessTotalMapper.removeIndex(id,userByRedis.getUserId());
        }
    }

    @Override
    public Response uploadPicture(MultipartFile file) {
        try {
            String path =PictureDownUtil.uploadPicture(file);
            return Response.success(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Response.error("上传失败!");
        }
    }


    @Override
    public Response getPicture(String path, HttpServletRequest request, HttpServletResponse response) {
        PictureDownUtil.pictureLook(RequestParamsUtil.getPath()+path,response,request);
        return Response.success();
    }

    @Override
    public Response getIndexData(BusinessIndexReqDTO businessIndexReqDTO) {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        //userByRedis.getUserId()
        List<SysBusinessTotal> indexList= sysBusinessTotalMapper.getUserIndex("123",businessIndexReqDTO.getParentName());
        List<BusinessIndexDataResponDTO> list  = new ArrayList<BusinessIndexDataResponDTO>();
        for(SysBusinessTotal sysBusinessTotal :indexList){
            try {
                BusinessIndexDataReqDTO businessIndexDataReqDTO =new BusinessIndexDataReqDTO();
                BeanUtils.copyProperties(businessIndexReqDTO,businessIndexDataReqDTO);
                businessIndexDataReqDTO.setDataType(sysBusinessTotal.getDataType());
                businessIndexDataReqDTO.setShowType(sysBusinessTotal.getShowType());
                businessIndexDataReqDTO.setKey(sysBusinessTotal.getKey().split("_")[2]);
                businessIndexDataReqDTO.setTimeType(sysBusinessTotal.getTimeType());
                businessIndexDataReqDTO.setTimeNum(sysBusinessTotal.getTimeNum());
                ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                        RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(),
                        ApiRequestParamsUtils.insertParamsObject(ApiTypeBusinessIndexEnum.GET_INDEX_DATA.getApiType(), businessIndexDataReqDTO));
                if(apiResponse.isSuccess()){
                    JSONObject data = JSONObject.parseObject(apiResponse.getData().toString());
                    BusinessIndexDataResponDTO businessIndexDataResponDTO = new BusinessIndexDataResponDTO();
                    businessIndexDataResponDTO.setKey(sysBusinessTotal.getKey());
                    businessIndexDataResponDTO.setLog(sysBusinessTotal.getLog());
                    businessIndexDataResponDTO.setLog(sysBusinessTotal.getLog());
                    businessIndexDataResponDTO.setShowPicture(sysBusinessTotal.getShowPicture());
                    businessIndexDataResponDTO.setName(sysBusinessTotal.getName());
                    businessIndexDataResponDTO.setParentName(sysBusinessTotal.getParentName());
                    businessIndexDataResponDTO.setLevel_1(null != data.get("1") ? JSONObject.parseArray(data.get("1").toString()):new ArrayList<>());
                    businessIndexDataResponDTO.setLevel_2(null != data.get("2") ? JSONObject.parseArray(data.get("2").toString()):new ArrayList<>());
                    businessIndexDataResponDTO.setLevel_3(null != data.get("3") ? JSONObject.parseArray(data.get("3").toString()):new ArrayList<>());
                    businessIndexDataResponDTO.setShowType(sysBusinessTotal.getShowType());
                    businessIndexDataResponDTO.setDataType(sysBusinessTotal.getDataType());
                    businessIndexDataResponDTO.setTimeType(sysBusinessTotal.getTimeType());
                    businessIndexDataResponDTO.setTimeNum(sysBusinessTotal.getTimeNum());
                    businessIndexDataResponDTO.setType(sysBusinessTotal.getType());
                    businessIndexDataResponDTO.setId(sysBusinessTotal.getId());
                    list.add(businessIndexDataResponDTO);
                    //return Response.success().data(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Response.error();
            }
        }

        return Response.success().data(list);
    }


}
