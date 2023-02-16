package com.Bibo.business.controller;

import com.Bibo.business.service.ShippingService;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "航运业务接口对接")
@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @SysLog(title="获取密钥用户", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取密钥用户")
    @PostMapping("/getShipmasterData")
    public Response getShipmasterData(@RequestBody Map<String,Object> data){
        return shippingService.getShipmasterData(data);
    }
}
