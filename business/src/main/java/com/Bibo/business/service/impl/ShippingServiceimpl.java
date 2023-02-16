package com.Bibo.business.service.impl;

import com.Bibo.business.service.ShippingService;
import com.Bibo.common.response.Response;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShippingServiceimpl implements ShippingService {
    @Override
    public Response getShipmasterData(Map<String, Object> data) {
        return Response.success();
    }
}
