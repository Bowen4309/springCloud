package com.Bibo.business.service;

import com.Bibo.business.dto.OrderShipRequestDto;
import com.Bibo.business.dto.ShipmasterReqeustDto;
import com.Bibo.common.response.Response;

import java.util.List;

public interface ShippingService {

    Response orderShipmasterData(OrderShipRequestDto orderShipRequestDTO);

    Response getShipmasterData(List<ShipmasterReqeustDto> shipmasterReqeustDtoList);
}
