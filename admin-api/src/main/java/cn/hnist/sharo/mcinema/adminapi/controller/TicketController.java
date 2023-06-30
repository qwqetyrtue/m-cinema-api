package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.adminapi.vo.TicketRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.TicketBase;
import cn.hnist.sharo.mcinema.db.pojo.VTicket;
import cn.hnist.sharo.mcinema.db.service.admin.TicketBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "票务管理接口")
@RestController
@RequestMapping("/api/admin/ticket")
public class TicketController {
    @Resource
    TicketBaseService ticketService;

    @ApiOperation("1.1 添加票务")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "", value = "", required = true)
    })
    @RequiresPermissions("admin:ticket:insert")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public HttpRes ticketInsertHandler(@RequestBody @Validated({InsertBy.class}) TicketRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = ticketService.insert(rec.adder());
            if (res >= 1)
                return HttpResUtil.succeed("添加成功");
            return HttpResUtil.fail("添加失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("2.3 修改票务中的场次和座位")
    @ApiOperationSupport(order = 2)
    @RequiresPermissions("admin:ticket:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HttpRes ticketSceneUpdateHandler(@RequestBody @Validated({UpdateFor._1.class}) TicketRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = ticketService.update(rec.sceneUpdater());
            if (res >= 1)
                return HttpResUtil.succeed("修改成功");
            return HttpResUtil.fail("修改失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("3.1 查询票务列表-时间筛选")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ticketStatus", value = "票务状态", required = true),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = false),
            @ApiImplicitParam(name = "useTime", value = "检票时间", required = false),
            @ApiImplicitParam(name = "refundTime", value = "退款时间", required = false),
            @ApiImplicitParam(name = "deleted", value = "逻辑删除", required = false),
    })
    @RequiresPermissions("admin:ticket:list")
    @RequestMapping(value = "/list/time", method = RequestMethod.GET)
    public HttpRes ticketListByTimeHandler(@ApiIgnore @Validated({ListBy.Time.class}) TicketRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VTicket> list = ticketService.listByTime(rec,
                rec.getCreateTimeUp(), rec.getCreateTimeDown(),
                rec.getUseTimeUp(), rec.getUseTimeDown(),
                rec.getRefundTimeUp(), rec.getRefundTimeDown());
        PageInfo<VTicket> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @ApiOperation("3.2 查询票务列表-影厅+状态筛选")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅Id", required = true),
            @ApiImplicitParam(name = "ticketStatus", value = "票务状态", required = false),
            @ApiImplicitParam(name = "deleted", value = "逻辑删除", required = false),
    })
    @RequiresPermissions("admin:ticket:list")
    @RequestMapping(value = "/list/hall", method = RequestMethod.GET)
    public HttpRes ticketLisByHallHandler(@ApiIgnore @Validated({ListBy._1.class}) TicketRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VTicket> list = ticketService.listByHall(rec, rec.getHallId());
        PageInfo<VTicket> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @ApiOperation("3.3 查询票务列表-用户+状态筛选")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "用户Id", required = true),
            @ApiImplicitParam(name = "ticketStatus", value = "票务状态", required = false),
            @ApiImplicitParam(name = "deleted", value = "逻辑删除", required = false),
    })
    @RequiresPermissions("admin:ticket:list")
    @RequestMapping(value = "/list/user", method = RequestMethod.GET)
    public HttpRes ticketLisByUserHandler(@ApiIgnore @Validated({ListBy._2.class}) TicketRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VTicket> list = ticketService.listByUser(rec);
        PageInfo<VTicket> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @ApiOperation("3.4 查询票务列表-电影+状态筛选")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影Id", required = true),
            @ApiImplicitParam(name = "ticketStatus", value = "票务状态", required = false),
            @ApiImplicitParam(name = "deleted", value = "逻辑删除", required = false),
    })
    @RequiresPermissions("admin:ticket:list")
    @RequestMapping(value = "/list/film", method = RequestMethod.GET)
    public HttpRes ticketListBySeatHandler(@ApiIgnore @Validated({ListBy._3.class}) TicketRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VTicket> list = ticketService.listByFilm(rec,rec.getFilmId());
        PageInfo<VTicket> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("删除票务")
    @ApiOperationSupport(order = 7)
    @RequiresPermissions("admin:ticket:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes ticketDeleteHandler(@RequestBody @Validated({OneBy.PK.class}) TicketRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = ticketService.delete(rec.getTicketId());
            if (res >= 1)
                return HttpResUtil.succeed("删除成功");
            return HttpResUtil.fail("删除失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

}
