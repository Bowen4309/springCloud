package com.Bibo.system.model.controller;

import com.Bibo.system.model.pojo.entity.SysKeyManage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.JwtUtils;
import com.Bibo.system.model.pojo.dto.KeyManageDTO;
import com.Bibo.system.model.pojo.dto.KeyManagePageReqDTO;
import com.Bibo.system.model.pojo.dto.KeyManageReqDTO;
import com.Bibo.system.model.pojo.dto.KeyManageResDTO;
import com.Bibo.system.model.service.ISysKeyManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "密钥管理")
@RestController
@RequestMapping(value = "/checkKey")
public class CheckKeyController extends BaseController {

    @Autowired
    private ISysKeyManageService iSysKeyManageService;

    @SysLog(title="获取密钥用户", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取密钥用户")
    @GetMapping("/getKeyWords")
    public Response<KeyManageResDTO> getKey(String id){
        SysKeyManage sysKeyManage =iSysKeyManageService.getById(id);
        KeyManageResDTO keyManageResDTO = new KeyManageResDTO();
        if(null != sysKeyManage){
            BeanUtils.copyProperties(sysKeyManage,keyManageResDTO);
        }
        return Response.success().data(keyManageResDTO);
    }

    @SysLog(title="修改或新增密钥用户数据", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "修改或新增密钥用户数据")
    @PostMapping("/addOrUpdateKey")
    public Response addOrUpdateKey(@RequestBody KeyManageReqDTO keyManageReqDTO){
        SysKeyManage sysKeyManage = new SysKeyManage();
        BeanUtils.copyProperties(keyManageReqDTO,sysKeyManage);
        if(null != sysKeyManage.getId() &&  !sysKeyManage.getId().equals("")){
            sysKeyManage.setUpdateTime(new Date());
        }else{
            sysKeyManage.setCreateTime(new Date());
        }
        iSysKeyManageService.saveOrUpdate(sysKeyManage);
        return Response.success();
    }

    @SysLog(title="密钥用户分页列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "密钥用户分页列表")
    @GetMapping("/page")
    public Response<List<KeyManageDTO>> page(KeyManagePageReqDTO dto){
        IPage<KeyManageDTO> keyManageDTOIPage = iSysKeyManageService.selectKeyManagePageList(dto);
        return getDataTable(keyManageDTOIPage);
    }

    @SysLog(title="删除密钥用户", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除密钥用户")
    @GetMapping("/deleteKey")
    public Response deleteKey(String id){
        iSysKeyManageService.removeById(id);
        return Response.success();
    }

    @SysLog(title="验证密钥", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "验证密钥")
    @GetMapping("/keyCheck")
    public Response keyCheck(String keyWords){
        String idToken =  (String) RedisUtil.get("KEY_MANAGE_USER:"+keyWords);
        if(StringUtils.isNotEmpty(idToken)){
           // String token =JwtUtils.getJwtToken(id);
            return  Response.success().data(idToken);
        }else{
            SysKeyManage sysKeyManage = iSysKeyManageService.getById(keyWords);
            if(null != sysKeyManage){
                String token =JwtUtils.getJwtToken(keyWords);
                RedisUtil.set("KEY_MANAGE_USER:"+keyWords,token);
                return  Response.success().data(token);
            }
        }
        return  Response.success().data("密钥错误,请联系管理员！");
    }
}
