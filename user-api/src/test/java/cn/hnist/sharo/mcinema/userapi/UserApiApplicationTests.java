package cn.hnist.sharo.mcinema.userapi;

import cn.hnist.sharo.mcinema.core.util.UrlUtil;
import cn.hnist.sharo.mcinema.userapi.util.jwt.JwtHelper;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootTest
class UserApiApplicationTests {
    @Resource
    JwtHelper jwtHelper;

    @Test
    void contextLoads() {
        System.out.println(UrlUtil.getUrl("dwadwa/dwadawdaw/dwadaw/dwadaw/").resolve("/dwadwa/dwadwa/dwadwa/dwa").resolve("/dwadaw/").toUrl());
    }
}
