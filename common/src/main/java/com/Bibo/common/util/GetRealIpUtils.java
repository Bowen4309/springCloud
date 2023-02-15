package com.Bibo.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetRealIpUtils {

    public static boolean getRealIp(HttpServletRequest request, List<String> ips){
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for:"+ip);
        if(null != ip && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)){
            if(ip.indexOf(",")!=-1){
                ip = ip.split(",")[0];
            }
        }
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
          ip = request.getHeader("Proxy-Client-IP");
        }
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Real-IP");
        }
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }

        if(null == ip || !ips.contains(ip)){
            return false;
        }
        return true;
    }
}
