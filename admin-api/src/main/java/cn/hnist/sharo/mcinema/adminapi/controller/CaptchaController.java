package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.CaptchaUtil;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.core.util.StorageUtil;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码生成接口")
@RestController
@RequestMapping(value = "/api/admin/captcha", method = RequestMethod.POST)
public class CaptchaController {
    @Resource(name = "captchaProducer")
    DefaultKaptcha captchaProducer;
    @Resource(name = "captchaMathProducer")
    DefaultKaptcha captchaMathProducer;
    @Resource
    RedisUtil redisUtil;
    @Value(value = "${mcinema.redis-key.captcha-admin}")
    private String captchaRoot;

    @ApiOperation(value = "生成图片验证码")
    @RequestMapping(value = "/img")
    public HttpRes imageCaptchaHandler(@RequestParam(defaultValue = "math") String type) {
        if (!DataUtil.checkEmpty(type)) return HttpResUtil.fail(ErrorEnum.MISSING_REQUEST_PARAMS);
        if (!"math".equals(type) && !"text".equals(type))
            return HttpResUtil.fail(ErrorEnum.WRONG_PARAMS_VALUE);
        CaptchaUtil.CaptchaInfo info;
        switch (type) {
            case "math":
                info = CaptchaUtil.createCaptchaWithImage(captchaMathProducer);
                break;
            case "text":
                info = CaptchaUtil.createCaptchaWithImage(captchaProducer);
                break;
            default:
                return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        }
        redisUtil.set(captchaRoot + info.getUUID(), info.getCaptchaVerify(), 90);
        String image = StorageUtil.bufferImgToBase64(info.getImage(), "jpg");
        Map<String, String> data = new HashMap<>();
        data.put("image", image);
        data.put("uuid", info.getUUID());
        return HttpResUtil.succeed("生成成功", data);
    }

    @ApiOperation(value = "生成邮箱验证码")
    @RequestMapping(value = "/mail")
    public HttpRes emailCaptchaHandler() {
        return HttpResUtil.succeed("");
    }
}
