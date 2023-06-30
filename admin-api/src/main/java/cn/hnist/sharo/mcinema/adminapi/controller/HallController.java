package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.config.DatabaseExceptionHandler;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.adminapi.vo.HallRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.HallBase;
import cn.hnist.sharo.mcinema.db.service.admin.HallBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "影厅的操作接口")
@RestController
@RequestMapping(value = "/api/admin/hall")
public class HallController {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);
    @Resource
    HallBaseService hallBaseService;

    @ApiOperation(value = "1.1 添加影厅", notes = "添加影厅草稿,默认deleted为true")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "影厅名", required = true),
            @ApiImplicitParam(name = "hallType", value = "影厅类型", required = false),
            @ApiImplicitParam(name = "location", value = "位置", required = false)
    })
    @RequiresPermissions("admin:hall:insert")
    @RequestMapping(value = "/insert/draft", method = RequestMethod.POST)
    public HttpRes hallInsertHandler(@ApiIgnore @RequestBody @Validated({InsertBy.Draft.class}) HallRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = hallBaseService.insert(rec.draftAdder());
            if (code != 1)
                return HttpResUtil.fail("添加失败", ErrorEnum.UNKNOWN_EXCEPTION);
            return HttpResUtil.succeed("添加成功");
        });
    }

    @ApiOperation(value = "2.1 修改影厅信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true),
            @ApiImplicitParam(name = "name", value = "影厅名", required = false),
            @ApiImplicitParam(name = "hallType", value = "影厅类型", required = false),
            @ApiImplicitParam(name = "location", value = "位置", required = false)
    })
    @RequiresPermissions("admin:hall:update")
    @RequestMapping(value = "/update/profile", method = RequestMethod.POST)
    public HttpRes hallUpdateForProfileHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Profile.class}) HallRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = hallBaseService.updateForProfile(rec.profileUpdater());
            if (code != 1)
                return HttpResUtil.fail("修改失败", ErrorEnum.UNKNOWN_EXCEPTION);
            return HttpResUtil.succeed("修改成功");
        });
    }

    @ApiOperation(value = "2.2 修改影厅座位排布", notes = "传入的rowNum和columNum会与矩阵行列比较,通过后计算出座位数量,座位数量不能通过接口修改")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true),
            @ApiImplicitParam(name = "seatArrange", value = "座位排列矩阵", required = true),
            @ApiImplicitParam(name = "rowNum", value = "座位排列行数", required = true),
            @ApiImplicitParam(name = "columnNum", value = "座位排列列数", required = true),
    })
    @RequiresPermissions("admin:hall:update")
    @RequestMapping(value = "/update/seat_arrange", method = RequestMethod.POST)
    public HttpRes hallUpdateForSeatArrangeHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Secure.class}) HallRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = hallBaseService.updateForArrange(rec.arrangeUpdater());
            if (code >= 1)
                return HttpResUtil.succeed("修改成功");
            return HttpResUtil.fail("修改失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation(value = "2.3 将影厅上线")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true)
    })
    @RequiresPermissions("admin:hall:update")
    @RequestMapping(value = "/update/inline", method = RequestMethod.POST)
    public HttpRes hallUpdateForInlineHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) HallRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = hallBaseService.updateForInline(rec.getHallId());
            if (code >= 1)
                return HttpResUtil.succeed("修改成功");
            return HttpResUtil.fail("修改失败", ErrorEnum.UNKNOWN_EXCEPTION);

        });
    }


    @ApiOperation(value = "3.1 查询影厅列表-筛选")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "影厅名称", required = false),
            @ApiImplicitParam(name = "hallType", value = "影厅类型", required = false),
            @ApiImplicitParam(name = "deleted", value = "是否删除", required = false),
            @ApiImplicitParam(name = "seatNumUp", value = "座位数量上限", required = false),
            @ApiImplicitParam(name = "seatNumDown", value = "座位数量下限", required = false),
    })
    @RequiresPermissions("admin:hall:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public HttpRes hallListHandler(@ApiIgnore HallRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<HallBase> list = hallBaseService.listByFilter(rec, rec.getSeatNumUp(), rec.getSeatNumDown());
        PageInfo<HallBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation(value = "3.2 查询影院选项列表")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "影厅名", required = true),
    })
    @RequiresPermissions("admin:hall:list")
    @RequestMapping(value = "/list/options", method = RequestMethod.GET)
    public HttpRes hallOptionsListHandler(@ApiIgnore @Validated({ListBy.Name.class}) HallRec rec) {
        List<HallBase> list = hallBaseService.listFormOptionsByFilter(rec);
        return HttpResUtil.succeedList("查询成功", list);
    }

    @ApiOperation(value = "4.1 根据电影ID查询单个影厅信息")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true)
    })
    @RequiresPermissions("admin:hall:one")
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public HttpRes hallOneHandler(@ApiIgnore @Validated({OneBy.PK.class}) HallRec rec) {
        if (!DataUtil.checkEmpty(rec.getHallId())) {
            return HttpResUtil.fail(ErrorEnum.WRONG_PARAMS_FORMAT);
        }
        return HttpResUtil.succeed("查询成功", hallBaseService.oneByPK(rec.getHallId()));
    }


    @ApiOperation(value = "5.1 删除影厅")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true)
    })
    @RequiresPermissions("admin:hall:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes hallDeleteHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) HallRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = hallBaseService.delete(rec.getHallId());
            if (code != 1)
                return HttpResUtil.fail("删除失败", ErrorEnum.UNKNOWN_EXCEPTION);
            return HttpResUtil.succeed("删除成功");
        });
    }
}
