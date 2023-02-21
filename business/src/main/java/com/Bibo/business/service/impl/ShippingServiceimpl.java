package com.Bibo.business.service.impl;

import com.Bibo.business.service.ShippingService;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.HttpRequestUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ShippingServiceimpl implements ShippingService {
    @Override
    public Response getShipmasterData(Map<String, Object> data) {
       // "","",HttpRequestUtil.getGetewayToken("http://api.cargoorbs.com/mobius/api/GetToken")
        try {
               String result = HttpRequestUtil.doApiGet("http://api.cargoorbs.com/mobius/api/GetToken?appid=ZZBH&secret=202105281005246600485D-ED5J-4962-9B29-42009F868290","","");
            return Response.success().data(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.success();
    }
}
