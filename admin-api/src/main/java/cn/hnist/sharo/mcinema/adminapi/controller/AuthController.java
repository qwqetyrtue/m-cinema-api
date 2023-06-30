package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.annotation.RequestLog;
import cn.hnist.sharo.mcinema.adminapi.util.ApiUtil;
import cn.hnist.sharo.mcinema.core.validator.Login;
import cn.hnist.sharo.mcinema.adminapi.config.DatabaseExceptionHandler;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.adminapi.vo.AdminRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.LogEnum;
import cn.hnist.sharo.mcinema.core.util.*;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import cn.hnist.sharo.mcinema.db.service.admin.PermissionService;
import cn.hnist.sharo.mcinema.db.service.admin.RoleService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Api(tags = "权限验证接口")
@RestController
@RequestMapping(value = "/api/admin/auth")
public class AuthController {
    @Resource
    RoleService roleService;
    @Resource
    RedisUtil redisUtil;
    @Resource
    PermissionService permissionService;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);
    @Value(value = "${mcinema.redis-key.captcha-admin}")
    private String captchaRoot;

    @RequestLog(action = LogEnum.Action.LOGIN, type = LogEnum.Type.SECURITY)
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "1.1 管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true),
            @ApiImplicitParam(name = "uuid", value = "UUID", required = true)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HttpRes adminLoginHandler(@ApiIgnore @RequestBody @Validated({Login.class}) AdminRec rec, @ApiIgnore NativeWebRequest request) {
        try {
            Subject admin = SecurityUtils.getSubject();
            String captcha;
            if (!redisUtil.hasKey(captchaRoot + rec.getUUID()))
                return HttpResUtil.fail(ErrorEnum.MISSING_GENERATE_CAPTCHA);
            if (redisUtil.getExpire(captchaRoot + rec.getUUID()) < 0)
                return HttpResUtil.fail("验证码已过期", ErrorEnum.EXPIRED_CAPTCHA);
            captcha = redisUtil.getString(captchaRoot + rec.getUUID());
            if (!DataUtil.checkEmpty(captcha)) return HttpResUtil.fail(ErrorEnum.MISSING_GENERATE_CAPTCHA);
            if (!captcha.equals(rec.getCaptcha())) return HttpResUtil.fail(ErrorEnum.WRONG_CAPTCHA);

            UsernamePasswordToken token = new UsernamePasswordToken(rec.getUsername(), rec.getPassword());
            admin.login(token);
            return HttpResUtil.succeed("登陆成功");
        } catch (UnknownAccountException e) {
            return HttpResUtil.fail("账号或密码错误", new RuntimeException("账号或密码错误", e), ErrorEnum.INVALID_ACCOUNT);
        } catch (AuthenticationException e) {
            return HttpResUtil.fail("认证失败", new RuntimeException("认证失败", e), ErrorEnum.UNKNOWN_EXCEPTION);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResUtil.fail("未知错误", e, ErrorEnum.UNKNOWN_EXCEPTION);
        }
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "2.1 查看管理员个人信息")
    @RequiresAuthentication
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public HttpRes adminInfoHandler() {
        return HttpResUtil.succeed("管理员信息", SecurityUtils.getSubject().getPrincipal());
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "2.2 查看管理员权限")
    @RequiresAuthentication
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public HttpRes adminApiHandler() {
        AdminBase admin = (AdminBase) SecurityUtils.getSubject().getPrincipal();
        Integer[] roleList = admin.getRoleIdList();
        Set<String> roleSet = roleService.selectRoleSet(roleList);
        Set<String> permissionSet = permissionService.selectPermissionSet(roleList);
        Map<String, Object> res = new HashMap<>();
        res.put("roles", roleSet);
        res.put("permissions", permissionSet);
        return HttpResUtil.succeed("授权和角色", res);
    }

    @ApiOperationSupport(order = 4)
    @RequestLog(type = LogEnum.Type.SECURITY, action = LogEnum.Action.LOGOUT)
    @ApiOperation(value = "3.1 管理员退出登录")
    @RequiresAuthentication
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public HttpRes adminLogoutHandler() {
        Subject admin = SecurityUtils.getSubject();
        admin.logout();
        return HttpResUtil.succeed("退出登录");
    }


    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "4.1 修改密码")
    @RequiresAuthentication
    @RequestMapping(value = "/update/pwd", method = RequestMethod.POST)
    public HttpRes adminUpdatePwdHandler(@ApiIgnore @RequestBody @Validated(UpdateFor.Pwd.class) AdminRec rec) {
        return HttpResUtil.fail(ErrorEnum.UNDEPLOY_SERVICES);
    }
}
