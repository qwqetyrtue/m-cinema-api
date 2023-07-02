package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.exception.UserException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.util.IPUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.Login;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.UserBase;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import cn.hnist.sharo.mcinema.db.service.user.U_UserBaseService;
import cn.hnist.sharo.mcinema.userapi.annotation.UserJWT;
import cn.hnist.sharo.mcinema.userapi.util.jwt.JwtHelper;
import cn.hnist.sharo.mcinema.userapi.vo.UserRec;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "权限验证接口")
@ApiSort(1)
@RestController
@RequestMapping("/api/user/auth")

public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Value(value = "${mcinema.redis-key.captcha-user}")
    private String captchaRoot;
    @Resource
    U_UserBaseService uUserBaseService;
    @Resource
    RedisUtil redisUtil;
    @Resource
    JwtHelper jwtHelper;
    @Resource
    NativeWebRequest webRequest;

    @ApiOperation("1.1 用户账号登录")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
    })
    @RequestMapping(value = "/login/account", method = RequestMethod.POST)
    public HttpRes userAccountLoginHandler(@ApiIgnore @RequestBody @Validated({Login.Account.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            String username = rec.getUsername();
            String password = rec.getPassword();

            UserBase user = uUserBaseService.oneByAuth(username, password);
            UserBase login = new UserBase();
            login.setUserId(user.getUserId());
            login.setLastLoginIp(IPUtil.getIPAddress(webRequest));
            login.setLastLoginTime(LocalDateTime.now());
            uUserBaseService.update(login);
            String token = jwtHelper.createUserIdToken(user.getUserId());
            Map<String, String> data = new HashMap<String, String>() {{
                put("token", token);
            }};
            return HttpResUtil.succeed("登陆成功", data);
        });
    }

    @ApiOperation("1.2 用户手机验证码登录")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true),
            @ApiImplicitParam(name = "UUID", value = "uuid", required = true),
    })
    @RequestMapping(value = "/login/sms", method = RequestMethod.POST)
    public HttpRes userSMSLoginHandler(@ApiIgnore @RequestBody @Validated({Login.SMS.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            String UUID = rec.getUUID();

            if (!redisUtil.hasKey(captchaRoot + UUID))
                throw new UserException(ErrorEnum.MISSING_GENERATE_CAPTCHA);
            if (redisUtil.getExpire(captchaRoot + UUID) < 0)
                throw new UserException("验证码已过期", ErrorEnum.EXPIRED_CAPTCHA);
            String verity = (String) redisUtil.hget(captchaRoot + UUID, "verify");
            String phone = (String) redisUtil.hget(captchaRoot + UUID, "phone");
            System.out.println(verity + " " + phone);
            if (!rec.getCaptcha().equals(verity) || !rec.getPhone().equals(phone))
                throw new UserException("验证码错误", ErrorEnum.WRONG_CAPTCHA);
            // 登录或注册
            UserBase user = uUserBaseService.oneByAuthOrInsert(phone);
            UserBase login = new UserBase();
            login.setUserId(user.getUserId());
            login.setLastLoginIp(IPUtil.getIPAddress(webRequest));
            login.setLastLoginTime(LocalDateTime.now());
            uUserBaseService.update(login);
            String token = jwtHelper.createUserIdToken(user.getUserId());
            Map<String, String> data = new HashMap<String, String>() {{
                put("token", token);
            }};
            return HttpResUtil.succeed("登陆成功", data);
        });
    }

    @ApiOperation("2.1 用户退出登录")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public HttpRes userLogoutHandler(@ApiIgnore @UserJWT Long userId) {
        return HttpResUtil.succeed("登出成功");
    }

    @ApiOperation("3.1 用户查看个人信息")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public HttpRes userInfoHandler(@ApiIgnore @UserJWT Long userId) {
        return HttpResUtil.hasError(() -> {
            UserBase user = uUserBaseService.oneByPK(userId);
            if (user == null) {
                logger.error("传入的token包含的User不存在");
                return HttpResUtil.fail(ErrorEnum.INVALID_TOKEN);
            }
            return HttpResUtil.succeed("查询成功", user);
        });
    }

    @ApiOperation("4.1 修改密码")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "旧密码", required = true),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true)
    })
    @RequestMapping(value = "/update/pwd", method = RequestMethod.POST)
    public HttpRes userUpdatePwdHandler(@ApiIgnore @UserJWT Long userId, @ApiIgnore @RequestBody @Validated({UpdateFor.Pwd.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uUserBaseService.updatePwd(rec.updaterPwd(userId), rec.getNewPassword());
            if (res >= 1) return HttpResUtil.succeed("修改成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("5.1 创建账号-手机号")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "captcha", value = "手机验证码", required = true)
    })
    @RequestMapping(value = "/create/phone", method = RequestMethod.POST)
    public HttpRes userCreateByPhoneHandler(@ApiIgnore @RequestBody @Validated({InsertBy.Phone.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            return HttpResUtil.fail(ErrorEnum.UNDEPLOY_SERVICES);
        });
    }

    @ApiOperation("5.1 创建账号-邮箱")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "captcha", value = "手机验证码", required = true)
    })
    @RequestMapping(value = "/create/email", method = RequestMethod.POST)
    public HttpRes userCreateByEmailHandler(@ApiIgnore @RequestBody @Validated({InsertBy.Email.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            return HttpResUtil.fail(ErrorEnum.UNDEPLOY_SERVICES);
        });
    }
}
