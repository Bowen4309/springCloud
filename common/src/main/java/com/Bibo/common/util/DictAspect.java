package com.Bibo.common.util;

import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Aspect
@Component
@Slf4j
public class DictAspect {

    @Pointcut("execution(* com.xdh.*.model.controller..*(..))")
    public void dict(){

    }
    @Around("dict()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();

        this.pareDictText(result);

        return result;
    }

    private void pareDictText(Object result){
        if(result instanceof Response){
            Response response = (Response)result;
            Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
            if (response.getData()!=null) {
                List<String> dictList = (List<String>) RedisUtil.get("dict");
                if(!ObjectUtils.isEmpty(dictList)){
                    dictList.forEach(dictStr ->{
                        Map<String,String> dictMap = (Map<String, String>) RedisUtil.get(dictStr);
                        map.put(dictStr,dictMap);
                    });
                    //初始化值
                    DictionaryConversionUtil.redisData=map;
                    DictionaryConversionUtil.converter(response.getData());
                }
            }
        }

    }
}
