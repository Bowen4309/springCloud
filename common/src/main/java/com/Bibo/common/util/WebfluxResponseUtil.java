package com.Bibo.common.util;

import com.alibaba.fastjson.JSONObject;
import com.Bibo.common.response.Response;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;


public class WebfluxResponseUtil {
    public static Mono<Void> resposeWeriter(Boolean success, ServerWebExchange exchange, int httpStatus, String msg){
        Response response = null;
        if(success){
            response =Response.success();
        }else{
            response =Response.error(msg);
        }
        return responseWrite(exchange,httpStatus,response);
    }

    public static Mono<Void> responseFailed(ServerWebExchange exchange, String msg){
        Response response =Response.error(msg);
        return responseWrite(exchange,HttpStatus.INTERNAL_SERVER_ERROR.value(),response);
    }
    public static Mono<Void> responseFailed(ServerWebExchange exchange,int httpStatus, String msg){
        Response response =Response.error(msg);
        return responseWrite(exchange,HttpStatus.INTERNAL_SERVER_ERROR.value(),response);
    }




    public static Mono<Void> responseWrite(ServerWebExchange exchange,int httpStatus,Response result){

        if(httpStatus == 0){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setAccessControlAllowCredentials(true);
        response.getHeaders().setAccessControlAllowOrigin("*");
        response.setStatusCode(HttpStatus.valueOf(httpStatus));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer).doOnError((error)->{
            DataBufferUtils.release(buffer);
        }));
    }
}
