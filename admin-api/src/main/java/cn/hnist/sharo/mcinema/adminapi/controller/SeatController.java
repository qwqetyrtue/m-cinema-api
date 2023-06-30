package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.vo.SeatRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.HallSeat;
import cn.hnist.sharo.mcinema.db.service.admin.HallSeatService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "座位的操作接口")
@RestController
@RequestMapping(value = "/api/admin/seat")
public class SeatController {
    @Resource
    HallSeatService hallSeatService;

    @ApiOperation(value = "1.1 添加座位")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true),
            @ApiImplicitParam(name = "inHallIndex", value = "厅内编号", required = true),
            @ApiImplicitParam(name = "level", value = "座位分级", required = false)
    })
    @RequiresPermissions(value = "admin:seat:insert")
    @RequestMapping(value = "/insert/one", method = RequestMethod.POST)
    public HttpRes seatInsertHandler(@ApiIgnore @RequestBody @Validated({InsertBy.class}) SeatRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = hallSeatService.insert(rec.oneAdder());
            if (res >= 1) return HttpResUtil.succeed("添加成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation(value = "1.2 为影厅批量添加座位", notes = "出现添加位置有座位的情况不会停止,只有出现其他数据库错误的时候才会回滚")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true)
    })
    @RequiresPermissions(value = "admin:seat:insert")
    @RequestMapping(value = "/insert/batch/hall", method = RequestMethod.POST)
    public HttpRes seatBatchInsertHandler(@ApiIgnore @RequestBody @Validated({InsertBy.Batch.class}) SeatRec rec) {
        return HttpResUtil.hasError(() -> {
            Map<String, Object> res = hallSeatService.batchInsert(rec.getHallId());
            return HttpResUtil.succeed("添加成功", res);
        });
    }

    @ApiOperation(value = "2.1 修改座位信息")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seatId", value = "座位编号", required = true),
            @ApiImplicitParam(name = "level", value = "座位分级", required = false),
    })
    @RequiresPermissions(value = "admin:seat:update")
    @RequestMapping(value = "/update/one/classify", method = RequestMethod.POST)
    public HttpRes seatUpdateForOneHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Classify.class}) SeatRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = hallSeatService.update(rec.classifyUpdater());
            if (res >= 1)
                return HttpResUtil.succeed("修改成功");
            return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation(value = "2.2 使座位可用")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seatId", value = "座位编号", required = true),
    })
    @RequiresPermissions(value = "admin:seat:update")
    @RequestMapping(value = "/update/one/usable", method = RequestMethod.POST)
    public HttpRes seatUpdateForOneUsableHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) SeatRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = hallSeatService.deletedUpdate(rec.getSeatId());
            if (res >= 1)
                return HttpResUtil.succeed("修改成功");
            return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation(value = "2.3 批量修改座位信息-修改为可用")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seatIdList", value = "座位编号列表", required = true),
    })
    @RequiresPermissions(value = "admin:seat:update")
    @RequestMapping(value = "/update/batch", method = RequestMethod.POST)
    public HttpRes seatUpdateForBatchHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) SeatRec rec) {
        return HttpResUtil.hasError(() -> {
            Map<String, Object> res = hallSeatService.batchUpdate(rec.getSeatIdList());
            return HttpResUtil.succeed("添加成功", res);
        });
    }

    @ApiOperation(value = "3.1 由座位编号查询单个座位信息")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seatId", value = "座位编号", required = true)
    })
    @RequiresPermissions(value = "admin:seat:one")
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public HttpRes seatOnByIdHandler(@ApiIgnore @Validated({OneBy.PK.class}) SeatRec rec) {
        return HttpResUtil.succeed("查询成功", hallSeatService.oneByPK(rec.getSeatId()));
    }

    @ApiOperation(value = "3.2 由影厅编号和厅内序号查询单个座位信息")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "座位编号", required = true),
            @ApiImplicitParam(name = "inHallIndex", value = "厅内序号", required = true)
    })
    @RequiresPermissions(value = "admin:seat:one")
    @RequestMapping(value = "/one/hall", method = RequestMethod.GET)
    public HttpRes seatOneByHallHandler(@ApiIgnore @Validated({OneBy.Index.class, OneBy.FK.class}) SeatRec rec) {
        return HttpResUtil.succeed("查询成功", hallSeatService.one(rec.getHallId(), rec.getInHallIndex()));
    }

    @ApiOperation(value = "3.3 查询座位列表")
    @ApiOperationSupport(order = 8)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true),
            @ApiImplicitParam(name = "deleted", value = "是否可用", required = false),
            @ApiImplicitParam(name = "level", value = "座位等级", required = false)
    })
    @RequiresPermissions(value = "admin:seat:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public HttpRes seatListHandler(@ApiIgnore @Validated({ListBy.Filter.class}) SeatRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<HallSeat> list = hallSeatService.list(rec);
        PageInfo<HallSeat> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }
}
