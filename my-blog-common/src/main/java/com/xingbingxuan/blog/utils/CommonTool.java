package com.xingbingxuan.blog.utils;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * 公共的工具类
 * @author : xbx
 * @date : 2022/4/4 16:31
 */
public class CommonTool {


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
