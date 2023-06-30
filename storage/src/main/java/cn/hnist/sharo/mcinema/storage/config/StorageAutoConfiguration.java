package cn.hnist.sharo.mcinema.storage.config;

import cn.hnist.sharo.mcinema.storage.service.LocalStorageImpl;
import cn.hnist.sharo.mcinema.storage.service.StorageService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {
    private final StorageProperties storageProperties;

    public StorageAutoConfiguration(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public StorageService storageService() {
        StorageService storageService = new StorageService();
        switch (storageProperties.getActive()) {
            case "local":
                storageService.setStorage(localStorage());
                break;
//            case "tencent":
//                storageService.getStorage(TencentStorage());
//                break;
//            case "aliyun":
//                storageService.getStorage(AliyunStorage());
//                break;
//            case "qiniu":
//                storageService.getStorage(QiniuStorage());
//                break;
            default:
                storageService.setStorage(localStorage());
                break;
        }
        return storageService;
    }


    @Bean
    public LocalStorageImpl localStorage() {
        StorageProperties.Local config = storageProperties.getLocal();
        return new LocalStorageImpl(
                config.getStoragePath(),
                config.getDefaultType(),
                config.getUrl()
        );
    }
}
