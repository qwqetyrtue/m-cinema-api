package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.adminapi.config.DatabaseExceptionHandler;
import cn.hnist.sharo.mcinema.adminapi.vo.UserRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.service.admin.UserBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.hnist.sharo.mcinema.db.pojo.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "用户的操作接口")
@RestController
@RequestMapping(value = "/api/admin/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);

    @Resource
    UserBaseService userBaseService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "1.1 添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "昵称", required = true),
            @ApiImplicitParam(name = "gender", value = "性别|数据库默认为3(隐藏)", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false),
            @ApiImplicitParam(name = "age", value = "年龄", format = "10~100", required = false)
    })
    @RequiresPermissions(value = "admin:user:insert")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public HttpRes userInsertHandler(@ApiIgnore @RequestBody @Validated({InsertBy.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = userBaseService.insert(rec.adder());
            if (code == 1)
                return HttpResUtil.succeed("添加成功");
            else {
                return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "2.1 修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "name", value = "昵称", required = false),
            @ApiImplicitParam(name = "gender", value = "性别|0~2", required = false),
            @ApiImplicitParam(name = "sign", value = "个性签名", required = false),
            @ApiImplicitParam(name = "age", value = "年龄|8~100", required = false)
    })
    @RequiresPermissions(value = "admin:user:update")
    @RequestMapping(value = "/update/profile", method = RequestMethod.POST)
    public HttpRes userUpdateProfileHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Profile.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = userBaseService.update(rec.profileUpdater());
            if (code == 1)
                return HttpResUtil.succeed("修改成功");
            else {
                return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "2.2 修改用户账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false),
            @ApiImplicitParam(name = "pwd", value = "密码|(格式)", required = false),
            @ApiImplicitParam(name = "email", value = "邮箱|(格式)", required = false),
    })
    @RequiresPermissions(value = "admin:user:update")
    @RequestMapping(value = "/update/account", method = RequestMethod.POST)
    public HttpRes userUpdateAccountHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Secure.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = userBaseService.update(rec.accountUpdater());
            if (code == 1)
                return HttpResUtil.succeed("修改成功");
            else {
                return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "2.2 修改用户头像", notes = "服务未部署")
    @RequiresPermissions(value = "admin:user:update")
    @RequestMapping(value = "/update/avatar", method = RequestMethod.POST)
    public HttpRes userUpdateAvatarHandler(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return HttpResUtil.fail(ErrorEnum.UNDEPLOY_SERVICES);
    }

    @ApiOperationSupport(order = 4)
    @RequiresPermissions(value = "admin:user:list")
    @ApiOperation(value = "3.1 查询用户列表-简介筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "昵称", required = false),
            @ApiImplicitParam(name = "gender", value = "性别|0~2", required = false),
            @ApiImplicitParam(name = "age", value = "年龄|8~100", required = false),
            @ApiImplicitParam(name = "ageUp", value = "年龄上限", required = false),
            @ApiImplicitParam(name = "ageDown", value = "年龄下限", required = false),
            @ApiImplicitParam(name = "deleted", value = "是否注销", required = false)
    })
    @RequestMapping(value = "/list/profile", method = RequestMethod.GET)
    public HttpRes userListByProfileHandler(@ApiIgnore UserRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<UserBase> list = userBaseService.listByProfile(rec, rec.getAgeUp(), rec.getAgeDown());
        PageInfo<UserBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 5)
    @RequiresPermissions(value = "admin:user:list")
    @ApiOperation(value = "3.2 查询用户列表-账号筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = false),
            @ApiImplicitParam(name = "email", value = "邮箱|(格式)", required = false),
            @ApiImplicitParam(name = "lastLoginIp", value = "最后登录ip", required = false),
            @ApiImplicitParam(name = "deleted", value = "是否注销", required = false)
    })
    @RequestMapping(value = "/list/account", method = RequestMethod.POST)
    public HttpRes userListByAccountHandler(@ApiIgnore @RequestBody UserRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<UserBase> list = userBaseService.listByAccount(rec);
        PageInfo<UserBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 6)
    @RequiresPermissions(value = "admin:user:list")
    @ApiOperation(value = "3.3 查询用户列表-时间筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTimeUp", value = "创建时间上限", required = false),
            @ApiImplicitParam(name = "createTimeDown", value = "创建时间下限", required = false),
            @ApiImplicitParam(name = "lastLoginTimeUp", value = "最后登录时间上限", required = false),
            @ApiImplicitParam(name = "lastLoginTimeDown", value = "最后登录时间下限", required = false),
            @ApiImplicitParam(name = "deleted", value = "是否注销", required = false)
    })
    @RequestMapping(value = "/list/time", method = RequestMethod.GET)
    public HttpRes userListByTimeHandler(@ApiIgnore UserRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<UserBase> list = userBaseService.listByTime(rec,
                rec.getCreateTimeUp(), rec.getCreateTimeDown(),
                rec.getLastLoginTimeUp(), rec.getLastLoginTimeDown());
        PageInfo<UserBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 7)
    @RequiresPermissions(value = "admin:user:count")
    @ApiOperation(value = "3.4 统计用户人数")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public HttpRes userCountHandler(@ApiIgnore UserRec rec, PagingRec paging) {
        Map<String, Object> res = userBaseService.statistics();
        return HttpResUtil.succeed("查询成功", res);
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "4.1 查询用户选项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", required = true)
    })
    @RequiresPermissions(value = "admin:user:one")
    @RequestMapping(value = "/list/options", method = RequestMethod.GET)
    public HttpRes userOptionsListHandler(@ApiIgnore @Validated({ListBy.Name.class}) UserRec rec) {
        List<UserBase> list = userBaseService.listFormOptionsByFilter(rec);
        return HttpResUtil.succeedList("查询成功", list);
    }

    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "4.1 根据用户ID查询单个用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @RequiresPermissions(value = "admin:user:one")
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public HttpRes userOneHandler(@ApiIgnore @Validated({OneBy.PK.class}) UserRec rec) {
        UserBase user = userBaseService.oneByPK(rec.getUserId());
        return HttpResUtil.succeed("查询成功", user);
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "5.1 删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @RequiresPermissions(value = "admin:user:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes userDeleteHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = userBaseService.delete(rec.getUserId());
            if (code >= 1) {
                return HttpResUtil.succeed("删除成功");
            } else {
                return HttpResUtil.fail("删除失败", ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

}
