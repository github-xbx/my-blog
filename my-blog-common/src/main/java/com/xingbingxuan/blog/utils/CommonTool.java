package com.xingbingxuan.blog.utils;

import cn.hutool.core.util.IdUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * 公共的工具类
 * @author : xbx
 * @date : 2022/4/4 16:31
 */
public class CommonTool {

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
     * <p>获取properties文件对象</p>
     *
     * @param propertiesName
     * @return : java.util.Properties
     * @author : xbx
     * @date : 2022/5/8 22:36
     */
    public  static Properties getPropertiesContent(String propertiesName) throws IOException {
        InputStream inputStream = CommonTool.class.getClassLoader().getResourceAsStream(propertiesName);

        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

}
