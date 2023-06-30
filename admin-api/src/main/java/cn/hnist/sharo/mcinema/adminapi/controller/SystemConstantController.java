package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.annotation.RequestLog;
import cn.hnist.sharo.mcinema.adminapi.config.DatabaseExceptionHandler;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.adminapi.vo.SystemConstantRec;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.LogEnum;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;
import cn.hnist.sharo.mcinema.db.pojo.SystemConstant;
import cn.hnist.sharo.mcinema.db.service.system.ConstantService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "系统变量操作接口")
@RestController
@RequiresPermissions("*")
@RequestMapping(value = "/api/system/constant", method = RequestMethod.POST)
public class SystemConstantController {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);
    @Resource
    ConstantService constantService;


    @RequestLog(type = LogEnum.Type.SECURITY, action = LogEnum.Action.SET_SYSTEM_CONSTANT)
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "1.1 设置一个系统变量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "变量名", required = true),
            @ApiImplicitParam(name = "value", value = "变量值", required = true)
    })
    @RequestMapping(value = "/set")
    public HttpRes setConstantHandler(@ApiIgnore @RequestBody @Validated({InsertBy.class}) SystemConstantRec rec) {
        return HttpResUtil.hasError(true, () -> {
            rec.setCreateTime(LocalDateTime.now());
            rec.setUpdateTime(rec.getCreateTime());
            constantService.insert(rec);
            return HttpResUtil.succeed("变量设置成功");
        });
    }

    @ApiOperation(value = "2.1 查看系统变量")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleted", value = "是否被删除", required = true)
    })
    @RequestMapping(value = "/list")
    public HttpRes listAllConstantHandler(@ApiIgnore @RequestBody @Validated({ListBy.Deleted.class}) SystemConstantRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<SystemConstant> list = constantService.listAll(rec.getDeleted());
        PageInfo<SystemConstant> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @RequestLog(type = LogEnum.Type.SECURITY, action = LogEnum.Action.DELETE_SYSTEM_CONSTANT)
    @ApiOperation(value = "3.1 删除系统变量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "constantId", value = "变量编号", required = true)
    })
    @RequestMapping(value = "/delete")
    public HttpRes deleteConstantHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) SystemConstantRec rec) {
        return HttpResUtil.hasError(true, () -> {
            AdminBase admin = (AdminBase) SecurityUtils.getSubject().getPrincipal();
            rec.setUpdateTime(LocalDateTime.now());
            constantService.delete(rec.getConstantId());
            return HttpResUtil.succeed("变量删除成功");
        });
    }
}
