package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.vo.SystemLogRec;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.SystemLog;
import cn.hnist.sharo.mcinema.db.service.system.SystemLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "日志查看接口")
@RestController
@RequestMapping(value = "/api/system/log")
@RequiresAuthentication
@RequiresPermissions(value = "*")
public class LogController {

    @Resource
    SystemLogService systemLogService;

    @ApiOperation(value = "1.1 查询日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logType", value = "日志等级", required = false),
            @ApiImplicitParam(name = "adminId", value = "管理员Id", required = false),
            @ApiImplicitParam(name = "ip", value = "IP地址", required = false),
            @ApiImplicitParam(name = "timeUp", value = "时间上限", required = false),
            @ApiImplicitParam(name = "timeUp", value = "时间下限", required = false),
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public HttpRes roleListHandler(@ApiIgnore SystemLogRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(),paging.getPageSize());
        List<SystemLog> list = systemLogService.listByFilter(rec, rec.getTimeUp(), rec.getTimeDown());
        PageInfo<SystemLog> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功",info);
    }
}
