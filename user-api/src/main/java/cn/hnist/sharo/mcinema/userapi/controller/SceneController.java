package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.db.pojo.VScene;
import cn.hnist.sharo.mcinema.db.service.user.U_SceneBaseService;
import cn.hnist.sharo.mcinema.userapi.vo.SceneRec;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "场次信息查询接口")
@ApiSort(5)
@RestController
@RequestMapping(value = "/api/user/scene")
public class SceneController {
    @Resource
    U_SceneBaseService u_sceneBaseService;

    @ApiOperation("1.1 查询场次-电影+日期")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filmId", value = "电影编号", required = true),
            @ApiImplicitParam(name = "day", value = "日期", required = true),
    })
    @RequestMapping(value = "/list/film_day", method = RequestMethod.GET)
    public HttpRes sceneListByFilmIdDayHandler(@ApiIgnore @Validated({ListBy.Time.class}) SceneRec rec) {
        List<VScene> list = u_sceneBaseService.listByFilmIdDay(rec.getFilmId(), rec.getDay());
        return HttpResUtil.succeedList("查询成功", list);
    }

    @ApiOperation("2.1 查询场次详情-场次编号")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场次编号", required = true),
    })
    @RequestMapping(value = "/one/pk", method = RequestMethod.GET)
    public HttpRes sceneOneByPKHandler(@ApiIgnore @Validated({OneBy.PK.class}) SceneRec rec) {
        VScene scene = u_sceneBaseService.oneByPk(rec.getSceneId());
        return HttpResUtil.succeed("查询成功", scene);
    }
}
