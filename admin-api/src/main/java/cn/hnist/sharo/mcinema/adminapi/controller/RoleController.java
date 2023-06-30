package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.AdminRole;
import cn.hnist.sharo.mcinema.db.service.admin.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "角色操作接口")
@RestController
@RequiresPermissions("*")
@RequestMapping("/api/admin/role")
public class RoleController {
    @Resource
    RoleService roleService;

    @ApiOperation(value = "1.1 查询角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public HttpRes roleListHandler(PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<AdminRole> list = roleService.selectAll();
        PageInfo<AdminRole> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation(value = "1.2 查询角色选项列表")
    @RequestMapping(value = "/list/option", method = RequestMethod.GET)
    public HttpRes roleOptionListHandler() {
        List<AdminRole> list = roleService.selectAllOption();
        return HttpResUtil.succeedList("查询成功", list);
    }
}
