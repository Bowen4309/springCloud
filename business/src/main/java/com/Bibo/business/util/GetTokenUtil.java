package com.Bibo.business.util;

import com.Bibo.business.constant.TaiTBusinessUrl;
import com.Bibo.common.util.HttpRequestUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class GetTokenUtil {

    public  static String getTaiTToken() throws IOException {
        String result = HttpRequestUtil.doApiGet(TaiTBusinessUrl.TAIT_TOKEN_URL,"","");
        JSONObject tokenReponse = JSONObject.parseObject(result);
        String token = JSONObject.parseObject(tokenReponse.get("data").toString()).getString("token");
        return token;
    }
}
