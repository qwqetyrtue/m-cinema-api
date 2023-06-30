package cn.hnist.sharo.mcinema.storage.service;

import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.model.FileInfo;
import cn.hnist.sharo.mcinema.core.model.StorageEnum;
import cn.hnist.sharo.mcinema.db.pojo.StorageBase;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class StorageService {
    private Storage Storage;

    // 返回图片资源
    public Resource getResource(String fullName, StorageEnum type) {
        return Storage.loadResource(fullName, type);
    }

    /**
     * <h3>存储资源</h3>
     *
     * @param in   输入流
     * @param type 文件存储类型
     * @param info 文件信息
     * @return
     * @throws FileException
     */
    public StorageBase store(InputStream in, StorageEnum type, FileInfo info) throws FileException,URISyntaxException {
        String key = Storage.generateUniKey(info.getName());

        Storage.store(in, type, info.getFullKeyName(key));

        StorageBase storageInfo = new StorageBase();
        storageInfo.setContentType(info.getContentType());
        storageInfo.setContentLength(info.getContentLength());
        storageInfo.setName(info.getName());
        storageInfo.setKey(info.getFullKeyName(key));
        LocalDateTime now = LocalDateTime.now();
        storageInfo.setCreateTime(now);
        storageInfo.setUpdateTime(now);
        System.out.println(Storage.getBaseUrl() + " " + type.getPath() + " " + info.getFullKeyName(key));
        storageInfo.setUrl(
                Storage.getBaseUrl() + "/"  +type.getPath() + "/" + info.getFullKeyName(key)
        );
        storageInfo.setType(type.getType());
        return storageInfo;
    }

    public Storage getStorage() {
        return Storage;
    }

    public void setStorage(Storage storage) {
        this.Storage = storage;
    }
}
