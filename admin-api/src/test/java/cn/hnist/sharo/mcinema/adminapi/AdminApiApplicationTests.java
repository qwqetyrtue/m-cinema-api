package cn.hnist.sharo.mcinema.adminapi;

import cn.hnist.sharo.mcinema.core.service.MailSendService;
import cn.hnist.sharo.mcinema.adminapi.vo.FilmRec;
import cn.hnist.sharo.mcinema.db.dao.AdminBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.custom.SceneCustomMapper;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;
import cn.hnist.sharo.mcinema.db.pojo.AdminBaseExample;
import cn.hnist.sharo.mcinema.db.redis.RedisSessionUtil;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import org.apache.shiro.session.mgt.SimpleSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class AdminApiApplicationTests {
    @Resource
    AdminBaseMapper adminMapper;
    @Resource
    JavaMailSenderImpl mailSender;
    @Resource
    MailSendService mailSendService;

    @Resource
    RedisSessionUtil redisSessionUtil;
    @Resource
    RedisUtil redisUtil;
    @Resource
    private SceneCustomMapper sceneCustomMapper;

    @Test
    void contextLoads(){
        Set<String> keys = redisUtil.getKeys("mcinema:ticket:4:*");
        keys.forEach(System.out::println);
        redisUtil.get("mcinema:ticket:4:ypqCFFAFm");
    }

    private void extendTest() {
        S s = new S();
        s.setAge("11");
        s.setName("S");
        SC sc = new SC();
        sc.setAge("11");
        sc.setName("SC");
        System.out.println(s);
        System.out.println(sc);
    }

    private void sendMail() throws InterruptedException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("sharo9999@163.com");
        mailMessage.setFrom("sharo9999@163.com");
        mailMessage.setSubject("测试");
        mailMessage.setText("测试文本");
        mailSender.send(mailMessage);

        mailSendService.send("sharo9999@163.com","测试Service","测试成功");
        System.out.println("yes");
        Thread.sleep(10000); // 等待邮件发送完在结束test
    }

    public class S{
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "S{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }

    public class SC extends S {
        private String name;

        @Override
        public String toString() {
            return "SC{" +
                    "name='" + name + '\'' +
                    ", age='" + getAge() + '\'' +
                    '}';
        }
    }

}
