package com.small.service.impl;

import com.google.common.collect.Lists;
import com.small.service.IFileService;
import com.small.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by skdwj on 2020/3/10.
 */
@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService{

    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件，上传文件的文件名:{},上传文件的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            log.error("文件上传异常");
            return null;
        }
        return targetFile.getName();
    }

}
