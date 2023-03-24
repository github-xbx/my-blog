package com.xingbingxuan.blog.thirdparty.service.impl;

import com.xingbingxuan.blog.thirdparty.Config.MinioConfig;
import com.xingbingxuan.blog.thirdparty.service.OssFileService;
import com.xingbingxuan.blog.thirdparty.utils.FileNameUtil;
import com.xingbingxuan.blog.utils.MimeTypeUtils;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author : xbx
 * @date : 2023/3/23 22:22
 */
@Service("ossFileService")
public class OssFileServiceImpl implements OssFileService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient minioClient;

    @Override
    public String upload(MultipartFile file,String prefixPath) throws Exception {

        String bucketName = minioConfig.getBucketName();

        String imageFileName = FileNameUtil.createFileName(file,prefixPath);

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(imageFileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();

        makeBucket(bucketName);

        minioClient.putObject(putObjectArgs);



        return "http://localhost:9000/"+ bucketName + "/" + imageFileName;
    }


    /**
     * description: makeBucket
     * 判断是否存在桶 ，如果不存在创建桶
     * @author xbx
     * @version 1.0
     * @date 2023/3/24 22:27
     */

    private void makeBucket(String bucketName) throws Exception {
        boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

        if (!b) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

}
