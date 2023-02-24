package com.Bibo.business.service.impl;

import com.Bibo.business.constant.TaiTBusinessUrl;
import com.Bibo.business.dto.OrderShipRequestDto;
import com.Bibo.business.dto.ShipmasterReqeustDto;
import com.Bibo.business.service.ShippingService;
import com.Bibo.business.util.GetTokenUtil;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.HttpRequestUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ShippingServiceimpl implements ShippingService {
    @Override
    public Response orderShipmasterData(List<OrderShipRequestDto> orderShipRequestDTO) {
        try {
            String token = GetTokenUtil.getTaiTToken();
            String  response = HttpRequestUtil.doApiPost(TaiTBusinessUrl.TAIT_ORDER_SHIP_URL, JSONObject.toJSONString(orderShipRequestDTO),token);
            String data = JSONObject.parseObject(response).getString("data");
            List<ShipmasterReqeustDto> shipmasterReqeustDtos = JSONObject.parseArray(data,ShipmasterReqeustDto.class);
            return Response.success().data(shipmasterReqeustDtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.success();
    }

    @Override
    public Response getShipmasterData(List<ShipmasterReqeustDto> shipmasterReqeustDtoList) {
        System.out.println(JSONObject.toJSONString(shipmasterReqeustDtoList));
        return Response.success();
    }


}
