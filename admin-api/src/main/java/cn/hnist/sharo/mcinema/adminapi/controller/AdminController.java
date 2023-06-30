package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.annotation.RequestLog;
import cn.hnist.sharo.mcinema.adminapi.config.DatabaseExceptionHandler;
import cn.hnist.sharo.mcinema.core.model.LogEnum;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.adminapi.vo.AdminRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;
import cn.hnist.sharo.mcinema.db.service.admin.AdminBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "人员操作接口")
@RestController
@RequestMapping(value = "/api/admin/admin")
@RequiresAuthentication
@RequiresPermissions(value = "*")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);

    @Resource
    AdminBaseService adminBaseService;

    @ApiOperationSupport(order = 1)
    @RequestLog(type = LogEnum.Type.SECURITY, action = LogEnum.Action.INSERT_ADMIN)
    @ApiOperation(value = "1.1 添加管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "account", value = "账户", required = true),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public HttpRes adminInsertHandler(@ApiIgnore @RequestBody @Validated({InsertBy.class}) AdminRec rec) {
        return HttpResUtil.hasError(true, () -> {
            int code = adminBaseService.insert(rec.adder());
            if (code >= 1) {
                return HttpResUtil.succeed("管理员添加成功");
            } else {
                return HttpResUtil.fail("管理员添加失败", ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

    @ApiOperationSupport(order = 2)
    @RequestLog(type = LogEnum.Type.GENERAL, action = LogEnum.Action.UPDATE_ADMIN_INFO)
    @ApiOperation(value = "2.1 修改管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员编号", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = false)
    })
    @RequestMapping(value = "/update/profile", method = RequestMethod.POST)
    public HttpRes adminUpdateProfileHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) AdminRec rec) {
        return HttpResUtil.hasError(true, () -> {
            int code = adminBaseService.update(rec.profileUpdater());
            if (code == 1) {
                return HttpResUtil.succeed("信息更新成功");
            } else {
                return HttpResUtil.fail("信息更新失败", ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

    @ApiOperationSupport(order = 3)
    @RequestLog(type = LogEnum.Type.SECURITY, action = LogEnum.Action.UPDATE_ADMIN_ACCOUNT)
    @ApiOperation(value = "2.2 修改管理员账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员编号", required = true),
            @ApiImplicitParam(name = "account", value = "账号|(格式)", required = false),
            @ApiImplicitParam(name = "phone", value = "手机号|(格式)", required = false),
            @ApiImplicitParam(name = "pwd", value = "密码|(格式)", required = false)
    })
    @RequestMapping(value = "/update/account", method = RequestMethod.POST)
    public HttpRes adminUpdateAccountHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Secure.class}) AdminRec rec) {
        return HttpResUtil.hasError(true, () -> {
            int code = adminBaseService.update(rec.accountUpdater());
            if (code == 1) {
                return HttpResUtil.succeed("账号更新成功");
            } else {
                return HttpResUtil.fail("账号更新失败", ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

    @ApiOperationSupport(order = 4)
    @RequestLog(type = LogEnum.Type.SECURITY, action = LogEnum.Action.UPDATE_ADMIN_ROLE)
    @ApiOperation(value = "2.3 修改管理员角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员编号", required = true),
            @ApiImplicitParam(name = "roleIdList", value = "角色列表|(格式)", required = false),
    })
    @RequestMapping(value = "/update/role", method = RequestMethod.POST)
    public HttpRes adminUpdateRoleHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Secure.class}) AdminRec rec) {
        return HttpResUtil.hasError(true, () -> {
            int code = adminBaseService.update(rec.roleListUpdater());
            if (code == 1) {
                return HttpResUtil.succeed("账号更新成功");
            } else {
                return HttpResUtil.fail("账号更新失败", ErrorEnum.UNKNOWN_EXCEPTION);
            }
        });
    }

    @ApiOperation(value = "2.4 修改管理员头像", notes = "服务未部署")
    @RequestMapping(value = "/update/avatar", method = RequestMethod.POST)
    public HttpRes adminUpdateAvatarHandler(@RequestBody @Validated({OneBy.PK.class}) AdminRec rec) {
        return HttpResUtil.fail(ErrorEnum.UNDEPLOY_SERVICES);
    }


    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "3.1 查询管理员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = false),
            @ApiImplicitParam(name = "role", value = "角色", required = false),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false),
            @ApiImplicitParam(name = "deleted", value = "是否注销账号", required = false)
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public HttpRes adminListHandler(@ApiIgnore @RequestBody @Validated({ListBy.Filter.class}) AdminRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<AdminBase> list = adminBaseService.listByFilter(rec);
        PageInfo<AdminBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "3.2 由ID查询管理员详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员ID", required = true)
    })
    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public HttpRes adminOneHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) AdminRec rec) {
        AdminBase admin = adminBaseService.oneByPK(rec.getAdminId());
        return HttpResUtil.succeed("查询成功", admin);
    }

    @ApiOperationSupport(order = 8)
    @RequestLog(type = LogEnum.Type.SECURITY, action = LogEnum.Action.DELETE_ADMIN)
    @ApiOperation(value = "4.1 删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员ID", required = true)
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes adminDeleteHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) AdminRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = adminBaseService.delete(rec.getAdminId());
            if (res >= 1)
                return HttpResUtil.succeed("管理员删除成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }
}
