package com.example.server.service;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    //文件本身，文件名称，文件路径
    default Boolean save(MultipartFile file, String fileName, String filePath) {
        return null;
    }
}
