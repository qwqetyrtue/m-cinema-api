package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.vo.SceneRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.SceneBase;
import cn.hnist.sharo.mcinema.db.pojo.VScene;
import cn.hnist.sharo.mcinema.db.service.admin.SceneBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "排场操作接口")
@RestController
@RequestMapping(value = "/api/admin/scene")
public class SceneController {
    @Resource
    SceneBaseService sceneService;

    @ApiOperation("1.1 添加排场")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true),
            @ApiImplicitParam(name = "filmId", value = "影片编号", required = true),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = true),
            @ApiImplicitParam(name = "price", value = "基础价格", required = true),
            @ApiImplicitParam(name = "sceneType", value = "放映类型", required = true),
            @ApiImplicitParam(name = "language", value = "放映语言", required = true),
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public HttpRes sceneInsertHandler(@ApiIgnore @RequestBody @Validated(InsertBy.class) SceneRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = sceneService.insert(rec.adder());
            if (res >= 1)
                return HttpResUtil.succeed("添加成功");
            return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("2.1 修改排场-数据信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "排场编号", required = true),
            @ApiImplicitParam(name = "filmId", value = "影片编号", required = true),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = true),
    })
    @RequestMapping(value = "/update/secure", method = RequestMethod.POST)
    public HttpRes sceneUpdateForSecureHandler(@ApiIgnore @RequestBody @Validated({UpdateFor.Secure.class, OneBy.PK.class}) SceneRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = sceneService.updateSecure(rec.secureUpdater());
            if (res >= 1)
                return HttpResUtil.succeed("修改成功");
            return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("2.2 修改排场-价格")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "排场编号", required = true),
            @ApiImplicitParam(name = "price", value = "基础价格", required = true)
    })
    @RequestMapping(value = "/update/price", method = RequestMethod.POST)
    public HttpRes sceneUpdateForPriceHandler(@ApiIgnore @RequestBody @Validated({UpdateFor.Profile.class, OneBy.PK.class}) SceneRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = sceneService.updatePrice(rec.priceUpdater());
            if (res >= 1)
                return HttpResUtil.succeed("修改成功");
            return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("3.1 查询排场-时间范围")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dayUp", value = "查询日期上限", required = true),
            @ApiImplicitParam(name = "dayDown", value = "查询日期下限", required = true)
    })
    @RequestMapping(value = "/list/time", method = RequestMethod.GET)
    public HttpRes sceneListByTimeHandler(@ApiIgnore @Validated({ListBy.Time.class}) SceneRec rec,PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VScene> list = sceneService.listVByRecent(rec.getDayUp(),rec.getDayDown());
        PageInfo<VScene> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("3.2 查询排场-影厅+时间范围")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hallId", value = "影厅编号", required = true),
            @ApiImplicitParam(name = "dayUp", value = "查询日期上限", required = true),
            @ApiImplicitParam(name = "dayDown", value = "查询日期下限", required = true)
    })
    @RequestMapping(value = "/list/hall", method = RequestMethod.GET)
    public HttpRes sceneListByHallTimeHandler(@ApiIgnore @Validated({ListBy._1.class, ListBy.Time.class}) SceneRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VScene> list = sceneService.listByHall(rec.getHallId(), rec.getDayUp(), rec.getDayDown());
        PageInfo<VScene> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("3.3 查询排场-影片+时间")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "影片编号", required = true),
            @ApiImplicitParam(name = "dayUp", value = "查询日期上限", required = true),
            @ApiImplicitParam(name = "dayDown", value = "查询日期下限", required = true)
    })
    @RequestMapping(value = "/list/film", method = RequestMethod.GET)
    public HttpRes sceneListByFilmHandler(@ApiIgnore @Validated({ListBy._2.class, ListBy.Time.class}) SceneRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VScene> list = sceneService.listByFilm(rec.getFilmId(), rec.getDayUp(), rec.getDayDown());
        PageInfo<VScene> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("4.1 删除排场")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "影片编号", required = true)
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes sceneDeleteHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, ListBy.Time.class}) SceneRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = sceneService.delete(rec.getSceneId());
            if (res >= 1)
                return HttpResUtil.succeed("删除成功");
            return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }
}
