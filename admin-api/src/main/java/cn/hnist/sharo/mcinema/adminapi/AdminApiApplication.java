package cn.hnist.sharo.mcinema.adminapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication(
        scanBasePackages = {
                "cn.hnist.sharo.mcinema.db",
                "cn.hnist.sharo.mcinema.adminapi",
                "cn.hnist.sharo.mcinema.core",
                "cn.hnist.sharo.mcinema.storage.config",
                "cn.hnist.sharo.mcinema.storage.service"
        })
@MapperScan(basePackages = {"cn.hnist.sharo.mcinema.db.dao"})
// 开启事务管理
@EnableTransactionManagement
// 开启多线程任务
@EnableAsync
public class AdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }

}
