package com.Bibo.system.aspectj;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysOperLog;
import com.Bibo.common.pojo.entity.SysUser;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.ServletUtils;
import com.Bibo.system.model.service.ISysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 切面插入日志
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Autowired
    private ISysOperLogService operLogService;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.xdh.traffic_common.annotation.SysLog)")
    public void logPointCut(){}

    /**
     * 处理完请求执行
     * @param joinPoint 切点
     * @param response 返回数据
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "response")
    public void doAfterReturning(JoinPoint joinPoint, Object response){

        handleLog(joinPoint, null, response);
    }

    /**
     * 拦截异常操作
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e){
        handleLog(joinPoint, e, null);
    }

    public void handleLog(final JoinPoint joinPoint, final Exception e, Object result){
        try {

            Response responseData = (Response) result;
            //获得注解信息
            SysLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            //获取当前用户
            SysUser currentUser = new SysUser();

            SysOperLog operLog = new SysOperLog();
            operLog.setOperStatus("0");
            if (currentUser!=null){
                operLog.setOperName(currentUser.getUserName());
            }
            if (e!=null){
                operLog.setOperStatus("1");
                operLog.setErrorMsg(StrUtil.sub(e.getMessage(), 0, 2000));
            }else if(!responseData.getCode().equals("200")){
                operLog.setOperStatus("0");
                operLog.setErrorMsg(responseData.getMsg());
            }
            //设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className+"."+methodName+"()");
            // 设置请求方式
            operLog.setOperMethod(ServletUtils.getRequest().getMethod());
            // 处理设置在注释上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 添加时间
            operLog.setCreateTime(new Date());
            LoginUser user = RedisUtil.getUserByRedis();
            if (ObjectUtil.isNotNull(user)){
                operLog.setCreateBy(user.getUserName());
            }
            // 异步存储到数据库
            operLogService.save(operLog);
        } catch (Exception exception) {
            log.error("日志AOP切点异常信息：{}", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息
     * @param log 切点注释
     * @param operLog 操作日志
     * @throws Exception
     */
    private void getControllerMethodDescription(SysLog log, SysOperLog operLog) throws Exception{
        // 设置标题
        operLog.setOperTitle(log.title());
        // 设置action动作
        operLog.setOperType(log.operatorType().name());
        // 是否保存request参数与值
        if (log.isSaveRequestData()){
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(SysOperLog operLog) throws Exception{
        Map<String, String[]>  map = ServletUtils.getRequest().getParameterMap();
        if(ObjectUtil.isNotEmpty(map)){
            PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = new PropertyPreFilters().addFilter();
            // 添加排除敏感属性字段
            excludeFilter.addExcludes("password");
            String params = JSONObject.toJSONString(map, excludeFilter);
            operLog.setOperParam(StrUtil.sub(params, 0, 2000));

        }
    }

    /**
     * 判断是否存在注解，存在则获取信息
     * @param joinPoint 切点
     */
    private SysLog getAnnotationLog(JoinPoint joinPoint) throws Exception{
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (ObjectUtil.isNotNull(method)){
            return method.getAnnotation(SysLog.class);
        }
        return null;
    }
}
