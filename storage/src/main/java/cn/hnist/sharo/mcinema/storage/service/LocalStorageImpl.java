package cn.hnist.sharo.mcinema.storage.service;

import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.StorageEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

// 本地存储
public class LocalStorageImpl implements Storage {
    private static final Logger logger = LoggerFactory.getLogger(LocalStorageImpl.class);

    private Path RootPath;
    private StorageEnum DefaultType = StorageEnum.TEMP;
    private final String url;


    public LocalStorageImpl(String path, StorageEnum type, String url) {
        this.RootPath = Paths.get(path);
        this.DefaultType = type;
        this.url = url;
    }

    @Override
    public Path loadPath(String fullName, StorageEnum type) {
        return RootPath.resolve(type.getPath()).resolve(fullName);
    }

    @Override
    public Resource loadResource(String fullName, StorageEnum type) {
        Path path = loadPath(fullName, type);
        try {
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists())
                throw new FileException("文件不存在", ErrorEnum.FAILED_LOAD, fullName);
            if (!resource.isReadable())
                throw new FileException("文件不可读", ErrorEnum.FAILED_LOAD, fullName);
            return resource;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new FileException(e.getMessage(), ErrorEnum.FAILED_LOAD, fullName);
        }
    }

    @Override
    public Boolean delete(String fullName) {
        Path path = RootPath.resolve(fullName);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(e.getMessage(), ErrorEnum.FAILED_DELETED, fullName);
        }
    }

    @Override
    public Boolean store(InputStream in, StorageEnum type, String fullName){
        try {
            System.out.println(type.getPath() + fullName);
            Files.copy(in, RootPath.resolve(type.getPath()).resolve(fullName), REPLACE_EXISTING);
            in.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("文件上传失败" + fullName, ErrorEnum.FAILED_UPLOAD, fullName);
        }
    }

    @Override
    public String getBaseUrl() {
        return url;
    }

    public Path getRootPath() {
        return RootPath;
    }

    public void setRootPath(Path rootPath) {
        this.RootPath = rootPath;
    }

    public StorageEnum getDefaultType() {
        return DefaultType;
    }

    public void setDefaultType(StorageEnum defaultType) {
        this.DefaultType = defaultType;
    }

    public String getUrl() {
        return url;
    }
}
