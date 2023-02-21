package com.Bibo.business.controller;

import com.Bibo.business.dto.OrderShipRequestDto;
import com.Bibo.business.dto.ShipmasterReqeustDto;
import com.Bibo.business.service.ShippingService;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "航运业务接口对接")
@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @SysLog(title="订阅船司数据获取", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "订阅船司数据获取")
    @PostMapping("/orderShipmasterData")
    public Response orderShipmasterData(@RequestBody OrderShipRequestDto orderShipRequestDTO){
        return shippingService.orderShipmasterData(orderShipRequestDTO);
    }

    @SysLog(title="接收推送的船司数据", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "接收推送的船司数据")
    @PostMapping("/getShipmasterData")
    public Response getShipmasterData(@RequestBody List<ShipmasterReqeustDto> shipmasterReqeustDtoList){
        return shippingService.getShipmasterData(shipmasterReqeustDtoList);
    }
}
