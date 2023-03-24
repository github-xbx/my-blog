package com.xingbingxuan.blog.thirdparty.utils;

import com.xingbingxuan.blog.utils.MimeTypeUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 文件工具类
 * @author : xbx
 * @date : 2023/3/24 22:19
 */
public class FileNameUtil {


    /**
     * description: createFileName
     * 生成 文件的名称 主要由 （当前时间 + 32位uuid + 后缀名 ） 组成
     * @author xbx
     * @version 1.0
     * @date 2023/3/24 22:20
     */

    public static String createFileName(MultipartFile file,String prefixPath){

        //h获取后缀名
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        //拼接文件名称
        String yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");

        if (StringUtils.hasText(prefixPath)){
            return prefixPath+yyyyMMddHHmmss + "-" + uuid + "." + extension;
        }else {
            return yyyyMMddHHmmss + "-" + uuid + "." + extension;
        }



    }

}
