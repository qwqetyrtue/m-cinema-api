package cn.hnist.sharo.mcinema.storage.service;

import cn.hnist.sharo.mcinema.core.model.StorageEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;

public interface Storage {
    /**
     * 获取文件存储的Path
     * @param fullName 完整的文件名称(带后缀)
     * @return Path
     */
    Path loadPath(String fullName, StorageEnum type);

    /**
     * <h3>获取文件存储的Resource</h3>
     * @param fullName 完整的文件名称(带后缀)
     * @param type 文件类型
     * @return Resource
     */
    Resource loadResource(String fullName, StorageEnum type);

    /**
     * 删除文件
     * @param fullName 完整的文件名称(带后缀)
     * @return
     * <ul>
     *     <li>true: 删除成功</li>
     *     <li>false: 文件不存在</li>
     * </ul>
     */
    Boolean delete(String fullName);

    /**
     * <h3>存储文件</h3>
     * @param in 输入
     * @param type 文件存储类型
     * @param fullName 完整的文件名称(带后缀)
     * @return 存储结果
     */
    default Boolean store(InputStream in, StorageEnum type, String fullName) {
        return false;
    }

    /**
     * <h3>根据文件名生成唯一的key用于存储</h3>
     * 使用纳秒时间戳转为62进制
     * @param name 文件名(不带后缀)
     * @return key
     */
    default String generateUniKey(String name){
        return DataUtil.hex10To62(System.nanoTime());
    };

    /**
     * <h3>获取存储方式的前缀url</h3>
     * @return url
     */
    String getBaseUrl();
}
