package com.sucsoft.toes.uitls;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中文工具
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 */
public class ChineseUtils {

    private static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * es ik中文分词器
     * @param data data
     */
    public static void setChineseAnalyzer(Map data){
        data.put("analyzer","ik_smart");
        data.put("search_analyzer","ik_smart");
    }
}
