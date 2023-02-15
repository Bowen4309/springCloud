//package com.xdh.traffic_system.aspectj;
//
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import com.xdh.traffic_common.annotation.DataScope;
//import com.xdh.traffic_common.constant.DataScopeEnum;
//import com.xdh.traffic_common.pojo.BaseDTO;
//import com.xdh.traffic_common.pojo.UserDTO;
//import com.xdh.traffic_common.pojo.entity.SysRole;
//import com.xdh.traffic_common.util.JwtUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class DataScopeAspect {
//    /**
//     * 配置切入点
//     */
////    @Pointcut("@annotation(com.xdh.traffic_common.annotation.DataScope)")
////    public void dataScopePointCut(){}
//
//    /**
//     * 处理前请求执行
//     * @param joinPoint 切点
//     */
//    @Before("@annotation(controllerDataScope)")
//    public void doBefore(JoinPoint joinPoint, DataScope controllerDataScope) {
//        clearDataScope(joinPoint);
//        handleDataScope(joinPoint, controllerDataScope);
//    }
//
//    public void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope){
//        // 获取当前用户
//        UserDTO user = JwtUtils.getUser();
//        if (ObjectUtil.isNotNull(user)){
//            dataScopeFilter(joinPoint, user, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
//        }
//    }
//
//    /**
//     * 数据范围过滤
//     * @param joinPoint 切点
//     * @param userDTO 用户信息
//     * @param deptAlias 注解的部门别名
//     * @param userAlias 注解的用户别名
//     */
//    public static void dataScopeFilter(JoinPoint joinPoint, UserDTO userDTO, String deptAlias, String userAlias){
//        StringBuilder sqlString = new StringBuilder();
//        for (SysRole role : userDTO.getRoleList()){
//            String dataScope = role.getDataScope();
//            switch (dataScope){
//                case DataScopeEnum.DATA_SCOPE_ALL:
//                    break;
//                case DataScopeEnum.DATA_SCOPE_DEPT:
//                    sqlString.append(StrUtil.format(" AND {}.dept_id='{}'", deptAlias, userDTO.getDeptId()));
//                case DataScopeEnum.DATA_SCOPE_DEPT_AND_CHILE:
//                    sqlString.append(StrUtil.format(" AND {}.dept_id IN (SELECT dept_id FROM sys_dept WHERE dept_id='{}' OR POSITION('{}' in ancestors)>0 )", deptAlias, userDTO.getDeptId(), userDTO.getDeptId()));
//                case DataScopeEnum.DATA_SCOPE_SELF:
//                    if (StrUtil.isNotBlank(userAlias)){
//                        sqlString.append(StrUtil.format(" AND {}.user_id='{}'", userAlias, userDTO.getUserId()));
//                    }else {
//                        // 数据权限为仅本人且没有别名，不查询数据
//                        sqlString.append(" AND 0=1");
//                    }
//            }
//            if (StrUtil.isNotBlank(sqlString.toString())){
//                Object params = joinPoint.getArgs()[0];
//                if (ObjectUtil.isNotNull(params) && params instanceof BaseDTO){
//                    BaseDTO baseDTO = (BaseDTO) params;
//                    baseDTO.setDataScope(sqlString.toString());
//                }
//            }
//        }
//    }
//
//    /**
//     * 拼接权限sql前先清空dataScope参数防止注入
//     * @param joinPoint
//     */
//    public void clearDataScope(final JoinPoint joinPoint){
//        Object params = joinPoint.getArgs()[0];
//        if (ObjectUtil.isNotEmpty(params) && params instanceof BaseDTO){
//            BaseDTO baseDTO = (BaseDTO) params;
//            baseDTO.setDataScope("");
//        }
//    }
//
//}
