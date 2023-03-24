package com.xingbingxuan.blog.thirdparty.file;

import com.xingbingxuan.blog.thirdparty.service.OssFileService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author : xbx
 * @date : 2023/3/23 22:08
 */
@RestController
@RequestMapping("oss/file")
public class OssFileController {


    @Autowired
    private OssFileService ossFileService;


    @PostMapping("uploadFile")
    public Result<String> uploadFile(MultipartFile file,String prefixPath) throws Exception {


        String upload = ossFileService.upload(file,prefixPath);

        return Result.success(upload);

    }

}
