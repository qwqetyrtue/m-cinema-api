package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.adminapi.config.DatabaseExceptionHandler;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.adminapi.vo.FilmRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.FilmBase;
import cn.hnist.sharo.mcinema.db.service.admin.FilmBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "影片操作接口")
@RestController
@RequiresAuthentication
@RequestMapping(value = "/api/admin/film")
public class FilmController {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);

    @Resource
    FilmBaseService filmBaseService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "1.1 添加影片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "影片名称", required = true),
            @ApiImplicitParam(name = "filmType", value = "影片品类|影片,歌剧等", required = true),
            @ApiImplicitParam(name = "filmClassify", value = "分类|悬疑,惊悚,科幻等", required = true),
            @ApiImplicitParam(name = "age", value = "年代", required = true),
            @ApiImplicitParam(name = "area", value = "片源地", required = true),
            @ApiImplicitParam(name = "language", value = "语言", required = true),
            @ApiImplicitParam(name = "duration", value = "时长", required = true),
            @ApiImplicitParam(name = "synopsis", value = "简介", required = false),
            @ApiImplicitParam(name = "action", value = "演员", required = false),
            @ApiImplicitParam(name = "foreignName", value = "外文名", required = false),
            @ApiImplicitParam(name = "alias", value = "别名", required = false),
            @ApiImplicitParam(name = "version", value = "影片版本", required = false),
            @ApiImplicitParam(name = "otherInfo", value = "其他信息", required = false),
    })
    @RequiresPermissions(value = "admin:film:insert")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public HttpRes filmInsertHandler(@ApiIgnore @RequestBody @Validated({InsertBy.class}) FilmRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = filmBaseService.insert(rec.adder());
            if (code == 1)
                return HttpResUtil.succeed("添加成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });

    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "2.1 修改影片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true),
            @ApiImplicitParam(name = "name", value = "影片名称", required = true),
            @ApiImplicitParam(name = "synopsis", value = "简介", required = false),
            @ApiImplicitParam(name = "action", value = "演员", required = false),
            @ApiImplicitParam(name = "foreignName", value = "外文名", required = false),
            @ApiImplicitParam(name = "alias", value = "别名", required = false),
            @ApiImplicitParam(name = "otherInfo", value = "其他信息", required = false),
    })
    @RequiresPermissions(value = "admin:film:update")
    @RequestMapping(value = "/update/profile", method = RequestMethod.POST)
    public HttpRes filmUpdateProfileHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.class}) FilmRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = filmBaseService.update(rec.profileUpdater());
            if (code == 1)
                return HttpResUtil.succeed("修改信息成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });

    }

    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "2.2 修改影片分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true),
            @ApiImplicitParam(name = "filmClassify", value = "分类", required = false),
            @ApiImplicitParam(name = "area", value = "片源地", required = false),
            @ApiImplicitParam(name = "filmType", value = "品类", required = false),
            @ApiImplicitParam(name = "age", value = "年代", required = false),
            @ApiImplicitParam(name = "language", value = "语言", required = false),
            @ApiImplicitParam(name = "version", value = "版本", required = false)
    })
    @RequiresPermissions(value = "admin:film:update")
    @RequestMapping(value = "/update/classify", method = RequestMethod.POST)
    public HttpRes filmUpdateClassifyHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.class}) FilmRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = filmBaseService.update(rec.classifyUpdater());
            if (code == 1)
                return HttpResUtil.succeed("修改分类信息成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });

    }

    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "2.3 修改影片时间信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true),
            @ApiImplicitParam(name = "duration", value = "时长", required = false),
    })
    @RequiresPermissions(value = "admin:film:update")
    @RequestMapping(value = "/update/time", method = RequestMethod.POST)
    public HttpRes filmUpdateTimeHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.class}) FilmRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = filmBaseService.update(rec.timeUpdater());
            if (code == 1)
                return HttpResUtil.succeed("修改时间信息成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });

    }

    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "3.1 查询影片-信息筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleted", value = "是否删除", required = false),
            @ApiImplicitParam(name = "name", value = "影片名称", required = true),
            @ApiImplicitParam(name = "foreignName", value = "外文名", required = false),
            @ApiImplicitParam(name = "alias", value = "别名", required = false),
    })
    @RequiresPermissions(value = "admin:film:list")
    @RequestMapping(value = "/list/profile", method = RequestMethod.GET)
    public HttpRes filmListByProfileHandler(@ApiIgnore FilmRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<FilmBase> list = filmBaseService.listByProfile(rec);
        PageInfo<FilmBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "3.2 查询影片-分类筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleted", value = "是否删除", required = false),
            @ApiImplicitParam(name = "filmClassify", value = "分类", required = false),
            @ApiImplicitParam(name = "area", value = "片源地", required = false),
            @ApiImplicitParam(name = "filmType", value = "品类", required = false),
            @ApiImplicitParam(name = "ageUp", value = "年代上限", required = false),
            @ApiImplicitParam(name = "ageDown", value = "年代下限", required = false),
            @ApiImplicitParam(name = "language", value = "语言", required = false),
            @ApiImplicitParam(name = "version", value = "版本", required = false)

    })
    @RequiresPermissions(value = "admin:film:list")
    @RequestMapping(value = "/list/classify", method = RequestMethod.GET)
    public HttpRes filmListByClassifyHandler(@ApiIgnore FilmRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<FilmBase> list = filmBaseService.listByClassify(rec, rec.getAgeUp(), rec.getAgeDown());
        PageInfo<FilmBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "3.3 查询影片-时间筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTimeUp", value = "添加时间上限", required = false),
            @ApiImplicitParam(name = "createTimeDown", value = "添加时间下限", required = false),
            @ApiImplicitParam(name = "durationUp", value = "时长上限", required = false),
            @ApiImplicitParam(name = "durationDown", value = "时长下限", required = false),
    })
    @RequiresPermissions(value = "admin:film:list")
    @RequestMapping(value = "/list/time", method = RequestMethod.GET)
    public HttpRes filmListByTimeHandler(@ApiIgnore FilmRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<FilmBase> list = filmBaseService.listByTime(rec, rec.getDurationUp(), rec.getDurationDown(), rec.getCreateTimeUp(), rec.getCreateTimeDown());
        PageInfo<FilmBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "3.4 查询影片选项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "影厅名", required = true),
    })
    @RequiresPermissions("admin:hall:list")
    @RequestMapping(value = "/list/options", method = RequestMethod.GET)
    public HttpRes filmOptionsListHandler(@ApiIgnore @Validated({ListBy.Name.class}) FilmRec rec) {
        List<FilmBase> list = filmBaseService.listFormOptionsByFilter(rec);
        return HttpResUtil.succeedList("查询成功", list);
    }

    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "4.1 根据影片ID查询单个影片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true)
    })
    @RequiresPermissions(value = "admin:film:one")
    @RequestMapping(value = "/one", method = RequestMethod.GET)
    public HttpRes filmOneHandler(@ApiIgnore @Validated({OneBy.PK.class}) FilmRec rec) {
        FilmBase film = filmBaseService.oneByPK(rec.getFilmId());
        return HttpResUtil.succeed("查询成功", film);
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "5.1 删除影片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true)
    })
    @RequiresPermissions(value = "admin:film:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes filmTestHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) FilmRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = filmBaseService.delete(rec.getFilmId());
            if (code >= 1) {
                return HttpResUtil.succeed("删除成功");
            } else return HttpResUtil.fail("删除失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "5.2 恢复影片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true)
    })
    @RequiresPermissions(value = "admin:film:delete")
    @RequestMapping(value = "/recover", method = RequestMethod.POST)
    public HttpRes filmRecoverHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) FilmRec rec) {
        return HttpResUtil.hasError(() -> {
            int code = filmBaseService.recover(rec.getFilmId());
            if (code >= 1) {
                return HttpResUtil.succeed("恢复成功");
            } else return HttpResUtil.fail("恢复失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }
}
