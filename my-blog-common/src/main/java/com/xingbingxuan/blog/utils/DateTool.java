package com.xingbingxuan.blog.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * @author : xbx
 * @date : 2022/5/14 18:50
 */
public class DateTool {
    /**
     * 功能描述:
     * <p>获取当前时间</p>
     *
     * @return : java.util.Date
     * @author : xbx
     * @date : 2022/4/21 22:11
     */
    public static Date getNowTime(){
        return Calendar.getInstance().getTime();
    }


    /**
     * 功能描述:
     * <p>h获取当前时间的字符串，根据参数的格式</p>
     *
     * @param stringFormat yyyyMMddHHmmss 、yyyy-MM-dd HH:mm:ss
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/5/8 21:42
     */
    public static String getNowTimeString(String stringFormat){

        return new SimpleDateFormat(stringFormat).format(getNowTime());
    }
    /**
     * 功能描述:
     * <p>将日期转化为指定的格式</p>
     *
     * @param stringFormat 格式
     * @param date 日期
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/5/14 19:00
     */
    public static String DateToString(String stringFormat,Date date){
        return new SimpleDateFormat(stringFormat).format(date);
    }

    /**
     * 功能描述:
     * <p最近7天的日期，以list方式返回</p>
     *
     * @return : java.util.List<java.lang.String>
     * @author : xbx
     * @date : 2022/5/14 18:52
     */
    public static List<String> getThisWeekTime(){
        List<String> list = new ArrayList<String>();
        Calendar nowDate = Calendar.getInstance();
        list.add(DateToString("yyyy-MM-dd",nowDate.getTime()));

        for (int i=0;i<6;i++){
           nowDate.add(Calendar.DAY_OF_YEAR,-1);
           list.add(DateToString("yyyy-MM-dd",nowDate.getTime()));
        }
        return list;
    }
}
