package com.Bibo.business.service;

import com.Bibo.common.response.Response;

import java.util.Map;

public interface ShippingService {

    Response getShipmasterData(Map<String,Object> data);
}
