package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.vo.PostRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.PostBase;
import cn.hnist.sharo.mcinema.db.service.admin.PostBaseService;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "文章的操作接口")
@RestController
@RequestMapping(value = "/api/admin/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Resource
    PostBaseService postBaseService;

    @ApiOperationSupport(order = 1)
    @ApiOperation("1.1 审核文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "文章编号", required = true),
            @ApiImplicitParam(name = "pass", value = "文章审核结果", required = true, type = "Boolean"),
    })
    @RequiresPermissions("admin:post:update")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public HttpRes postReviewHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class, UpdateFor.Review.class}) PostRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = postBaseService.releasePost(rec.getPostId(), rec.getPass());
            if (res >= 1)
                return HttpResUtil.succeed("文章审核成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("2.1 查询文章列表-信息筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleted", value = "是否被删除", required = false),
            @ApiImplicitParam(name = "title", value = "文章标题", required = false)
    })
    @RequiresPermissions("admin:post:list")
    @RequestMapping(value = "/list/profile", method = RequestMethod.GET)
    public HttpRes postListByProfileHandler(@ApiIgnore PostRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<PostBase> list = postBaseService.listByProfile(rec);
        PageInfo<PostBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 3)
    @ApiOperation("2.2 查询文章列表-分类筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleted", value = "是否被删除", required = false),
            @ApiImplicitParam(name = "postClassify", value = "文章类型", required = false),
            @ApiImplicitParam(name = "postStatus", value = "文章状态", required = false),
    })
    @RequiresPermissions("admin:post:list")
    @RequestMapping(value = "/list/classify", method = RequestMethod.GET)
    public HttpRes postListByClassifyHandler(@ApiIgnore PostRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<PostBase> list = postBaseService.listByClassify(rec);
        PageInfo<PostBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation("2.3 查询文章列表-时间筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleted", value = "是否被删除", required = false),
            @ApiImplicitParam(name = "releaseTimeUp", value = "发布时间", required = false),
            @ApiImplicitParam(name = "releaseTimeDown", value = "发布时间", required = false),
            @ApiImplicitParam(name = "reviewTimeUp", value = "发布时间", required = false),
            @ApiImplicitParam(name = "reviewTimeDown", value = "发布时间", required = false)
    })
    @RequiresPermissions("admin:post:list")
    @RequestMapping(value = "/list/time", method = RequestMethod.GET)
    public HttpRes postListByTimeHandler(@ApiIgnore PostRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<PostBase> list = postBaseService.listByTime(
                rec,
                rec.getReleaseTimeUp(), rec.getReleaseTimeDown(),
                rec.getReviewTimeUp(), rec.getReviewTimeDown());
        PageInfo<PostBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 5)
    @ApiOperation("2.4 查询文章列表-外键筛-用户筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleted", value = "是否被删除", required = false),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = false),
            @ApiImplicitParam(name = "postStatus", value = "文章状态", required = false)
    })
    @RequiresPermissions("admin:post:list")
    @RequestMapping(value = "/list/user", method = RequestMethod.GET)
    public HttpRes postListByFKHandler(@ApiIgnore PostRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<PostBase> list = postBaseService.listByFK(rec);
        PageInfo<PostBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("3.1 由文章Id查询单个文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "文章编号", required = true),
    })
    @RequiresPermissions("admin:post:one")
    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public HttpRes postOneHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) PostRec rec) {
        PostBase res = postBaseService.oneByPK(rec.getPostId());
        return HttpResUtil.succeed("查询成功", res);
    }


    @ApiOperationSupport(order = 7)
    @ApiOperation("3.1 退回文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "文章编号", required = true),
    })
    @RequiresPermissions("admin:post:update")
    @RequestMapping(value = "/back", method = RequestMethod.POST)
    public HttpRes postBackHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) PostRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = postBaseService.backPost(rec.getPostId());
            if (res >= 1)
                return HttpResUtil.succeed("文章退回成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }
}
