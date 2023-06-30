package cn.hnist.sharo.mcinema.userapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {
        "cn.hnist.sharo.mcinema.db",
        "cn.hnist.sharo.mcinema.core",
        "cn.hnist.sharo.mcinema.userapi",
        "cn.hnist.sharo.mcinema.storage.config",
        "cn.hnist.sharo.mcinema.storage.service"
})
@MapperScan("cn.hnist.sharo.mcinema.db.dao")
// 事务管理
@EnableTransactionManagement
// 多线程任务
@EnableAsync
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

}
