package com.Bibo.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private static String YMDHMS1 = "yyyyMMddHHmmss";
    private static String YMD = "yyyyMMdd";
    private static String YMD1 = "yyyy-MM-dd";



    public static String getSDF(){
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
        String format = sdf.format(date);
        return format;
    }


    public static String getDateToStr(Date date){
        DateFormat dateStr = new SimpleDateFormat(YMDHMS);
        return dateStr.format(date);
    }

    public static String getDateDayToStr(Date date){
        DateFormat dateStr = new SimpleDateFormat(YMD);
        return dateStr.format(date);
    }
    public static String getDateTimeToStr(Date date){
        DateFormat dateStr = new SimpleDateFormat(YMDHMS1);
        return dateStr.format(date);
    }

    public static String getDateDayToStr1(Date date){
        DateFormat dateStr = new SimpleDateFormat(YMD1);
        return dateStr.format(date);
    }

    /**
     * 时间格式转化
     * @param dataStr
     * @return
     */
    public static Date getDateByStr(String dataStr){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 时间格式转化
     * @param dataStr
     * @return
     */
    public static Date getDateTimeByStr(String dataStr){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long getTime(Date nowDate,Date beforeDate){
       long endTime =nowDate.getTime();
       long startTime =beforeDate.getTime();
       long diff = (endTime-startTime)/1000;
        return diff;
    }


    public static void main(String[] args) {
        System.out.println(getTime(new Date(),getDateTimeByStr("2022-12-01 10:30:47")));
    }
}
