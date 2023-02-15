package com.Bibo.common.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeInitUtils {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static String initCode(String code){
        if(null == code){
            return "id";
        }
        Matcher matcher = humpPattern.matcher(code);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()){
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(initCode("createTime"));
    }
}
