package com.xingbingxuan.blog.thirdparty.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : xbx
 * @date : 2023/3/23 22:21
 */
public interface OssFileService {


    /**
     * description: upload
     * 文件删上传到minio
     * @author xbx
     * @version 1.0
     * @date 2023/3/24 22:37
     * @param file 上传的文件
     * @param prefixPath 文件储存在桶中的前置路径 如果是根路径 就传null
     * @return 上传成功会返回文件的访问地址，上传失败会抛出异常
     * @throws Exception
     */

    String upload(MultipartFile file,String prefixPath) throws Exception;
}
