
package com.example.server.service.Impl;

        import com.example.server.service.IStorageService;
        import org.springframework.stereotype.Service;
        import org.springframework.util.FileCopyUtils;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.File;
        import java.io.IOException;
//框架自动加载
@Service
public class StorageServiceImpl implements IStorageService {

    @Override
    public Boolean save(MultipartFile file, String fileName, String filePath) {
        String path=filePath+fileName;
//        创建文件
        File targetFile=new File(path);// E:/Users/jim/Upload/
//        判断目录，不存在创建
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
//        二进制读取文件写入磁盘
        try {
            FileCopyUtils.copy(file.getBytes(),targetFile);
            return true;
        } catch (IOException e) {
        }
        return false;
    }
}
