package cn.hnist.sharo.mcinema.storageapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "cn.hnist.sharo.mcinema.core",
        "cn.hnist.sharo.mcinema.db",
        "cn.hnist.sharo.mcinema.storage",
        "cn.hnist.sharo.mcinema.storageapi"
})
@MapperScan(basePackages = {"cn.hnist.sharo.mcinema.db.dao"})
public class StorageApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApiApplication.class, args);
    }

}
