package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.CaptchaUtil;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.core.util.StorageUtil;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.Login;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import cn.hnist.sharo.mcinema.userapi.vo.UserRec;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码生成接口")
@ApiSupport(order = 6)
@RestController
@RequestMapping(value = "/api/user/captcha", method = RequestMethod.POST)
public class CaptchaController {
    @Value(value = "${mcinema.redis-key.captcha-user}")
    private String captchaRoot;

    @Resource(name = "captchaProducer")
    DefaultKaptcha captchaProducer;
    @Resource(name = "captchaMathProducer")
    DefaultKaptcha captchaMathProducer;
    @Resource
    RedisUtil redisUtil;

    @ApiOperation(value = "生成手机验证码")
    @RequestMapping(value = "/sms")
    public HttpRes phoneCaptchaHandler(@ApiIgnore @RequestBody @Validated({Login.Captcha.class}) UserRec rec) {
        CaptchaUtil.CaptchaInfo info = CaptchaUtil.createCaptcha(captchaProducer);
        redisUtil.hset(captchaRoot + info.getUUID(),"verify",info.getCaptchaVerify());
        redisUtil.hset(captchaRoot + info.getUUID(),"phone",rec.getPhone());
        redisUtil.expire(captchaRoot + info.getUUID(),60);
        Map<String, String> data = new HashMap<>();
        data.put("UUID", info.getUUID());
        return HttpResUtil.succeed("生成成功", data);
    }
}
