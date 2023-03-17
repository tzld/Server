package com.example.server.controllers;

import com.example.server.service.IStorageService;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.server.mapper.PoiMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/")

public class CommonController {
    @Autowired
    private IStorageService storageService;
    @Value("${upload.accessPath}")
    private String accessPath;
    @Value("${upload.localPath}")
    private String localPath;
    @PostMapping(value = "/upload")
    public Result upload(HttpServletRequest request, HttpServletResponse reponse){
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;//创建网络请求，接收信息
        MultipartFile file=multipartRequest.getFile("file");//得到信息中的文件
        log.info("Upload file.file={}",file);
        String fileName=file.getOriginalFilename();//得到文件名
        log.info("Upload file.filename={}",fileName);
        storageService.save(file,fileName,localPath);//保存到本地（服务器）
        log.info("localPath={}:",localPath);
        return Result.success(accessPath+fileName);
    }
}
