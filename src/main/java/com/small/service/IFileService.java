package com.small.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by skdwj on 2020/3/10.
 */
public interface IFileService {

    public String upload(MultipartFile file, String path);
}
