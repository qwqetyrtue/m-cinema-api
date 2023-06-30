package cn.hnist.sharo.mcinema.userapi.controller;


import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.db.pojo.FilmBase;
import cn.hnist.sharo.mcinema.db.pojo.FilmCollect;
import cn.hnist.sharo.mcinema.db.pojo.custom.FilmCollectCount;
import cn.hnist.sharo.mcinema.db.service.user.U_FilmBaseService;
import cn.hnist.sharo.mcinema.userapi.annotation.UserJWT;
import cn.hnist.sharo.mcinema.userapi.vo.FilmCollectRec;
import cn.hnist.sharo.mcinema.userapi.vo.FilmRec;
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

@Api(tags = "电影信息查询接口")
@ApiSort(4)
@RestController

@RequestMapping("/api/user/film")
public class FilmController {
    @Resource
    U_FilmBaseService uFilmBaseService;


    @ApiOperation("1.1 按首页的分类查询电影信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "首页分类", required = true),
    })
    @RequestMapping(value = "/list/index/type", method = RequestMethod.GET)
    public HttpRes listByIndexTypeHandler(@ApiIgnore @Validated({ListBy.Index.class}) FilmRec rec) {
        PageHelper.startPage(1, 10);
        List<FilmBase> list = uFilmBaseService.listByType(rec.getType());
        PageInfo<FilmBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("1.2 按电影页的分类查询电影信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "首页分类", required = true),
    })
    @RequestMapping(value = "/list/film/type", method = RequestMethod.GET)
    public HttpRes listByFilmTypeHandler(@ApiIgnore @Validated({ListBy.Index.class}) FilmRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<FilmBase> list = uFilmBaseService.listByType(rec.getType());
        PageInfo<FilmBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("1.3 按名称和品类查询")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "filmType", value = "电影品类", required = true),
    })
    @RequestMapping(value = "/list/search/name", method = RequestMethod.GET)
    public HttpRes listByNameHandler(@ApiIgnore @Validated({ListBy.Name.class}) FilmRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<FilmBase> list = uFilmBaseService.listByNameType(rec.getName(), rec.getFilmType());
        PageInfo<FilmBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("1.4 按名称查询 演员筛选")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
    })
    @RequestMapping(value = "/list/search/actor", method = RequestMethod.GET)
    public HttpRes listByActorHandler(@ApiIgnore @Validated({ListBy.Name.class}) FilmRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<FilmBase> list = uFilmBaseService.listByActor(rec.getName());
        PageInfo<FilmBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }


    @ApiOperation("2.1 查询电影详细信息")
    @ApiOperationSupport(order = 10)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true),
    })
    @RequestMapping(value = "/one/id", method = RequestMethod.GET)
    public HttpRes oneByIdHandler(@ApiIgnore @Validated({OneBy.PK.class}) FilmRec rec) {
        FilmBase film = uFilmBaseService.oneById(rec.getFilmId());
        return HttpResUtil.succeed("查询成功", film);
    }


    @ApiOperation("3.1 添加看过和想看电影")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true),
            @ApiImplicitParam(name = "type", value = "类型", required = true),
    })
    @RequestMapping(value = "/insert/collect", method = RequestMethod.POST)
    public HttpRes insertCollectHandler(@UserJWT Long userId, @ApiIgnore @RequestBody @Validated({InsertBy.class}) FilmCollectRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uFilmBaseService.insertCollect(rec.adder(userId));
            if (res >= 1)
                return HttpResUtil.succeed("添加成功");
            else return HttpResUtil.fail("添加失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("3.2 查询收藏电影")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", required = true),
    })
    @RequestMapping(value = "/list/collect/user", method = RequestMethod.GET)
    public HttpRes listByUserCollectHandler(
            @UserJWT Long userId,
            @ApiIgnore @Validated({ListBy.Type.class}) FilmCollectRec rec,
            PagingRec paging
    ) {

        PageInfo<FilmBase> info = uFilmBaseService.listByUserCollect(userId, rec.getType(), rec.getFilmType(), paging);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("3.2 查询收藏电影数量")
    @ApiOperationSupport(order = 21)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", required = true),
    })
    @RequestMapping(value = "/count/collect/user", method = RequestMethod.GET)
    public HttpRes countByUserCollectHandler(
            @UserJWT Long userId
    ) {
        List<FilmCollectCount> counts = uFilmBaseService.countCollect(userId);
        return HttpResUtil.succeed("查询成功", counts);
    }

    @ApiOperation("3.2 查询某部电影收藏情况")
    @ApiOperationSupport(order = 21)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "类型", required = true),
    })
    @RequestMapping(value = "/one/collect", method = RequestMethod.GET)
    public HttpRes oneByUserFilmCollectHandler(
            @UserJWT Long userId,
            @ApiIgnore @Validated({OneBy.PK.class}) FilmCollectRec rec
    ) {
        FilmCollect collect = uFilmBaseService.oneByUserFilm(userId, rec.getFilmId());
        return HttpResUtil.succeed("查询成功", collect);
    }
}
