package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.StatusEnum;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.PostBase;
import cn.hnist.sharo.mcinema.db.service.user.U_PostBaseService;
import cn.hnist.sharo.mcinema.userapi.annotation.UserJWT;
import cn.hnist.sharo.mcinema.userapi.vo.PostRec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "文章的操作接口")
@ApiSort(3)
@RestController
@RequestMapping(value = "/api/user/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Resource
    U_PostBaseService uPostBaseService;

    @ApiOperation("1.1 保存一篇文章草稿")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "文章标题", required = false),
            @ApiImplicitParam(name = "content", value = "文章内容", required = true),
            @ApiImplicitParam(name = "postClassify", value = "文章分类", required = false),
    })
    @RequestMapping(value = "/draft", method = RequestMethod.POST)
    public HttpRes postSaveHandler(@ApiIgnore @UserJWT Long userId, @ApiIgnore @RequestBody @Validated({InsertBy.Draft.class}) PostRec rec) {
        int res = uPostBaseService.insert(rec.draftAdder(userId));
        if (res >= 1) {
            return HttpResUtil.succeed("保存成功");
        } else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
    }

    @ApiOperation("1.2 提交审核一篇文章")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "文章编号", required = true),
    })
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public HttpRes postSubmitHandler(@ApiIgnore @UserJWT Long userId, @ApiIgnore @RequestBody @Validated({OneBy.PK.class}) PostRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uPostBaseService.updateStatus(userId, rec.getPostId(), StatusEnum.PostStatus.REVIEW);
            if (res >= 1) {
                return HttpResUtil.succeed("提交成功");
            } else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("2.1 修改一篇文章草稿")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "文章编号", required = true),
            @ApiImplicitParam(name = "title", value = "文章标题", required = false),
            @ApiImplicitParam(name = "content", value = "文章内容", required = false),
            @ApiImplicitParam(name = "postClassify", value = "文章分类", required = false),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HttpRes postUpdateHandler(@ApiIgnore @UserJWT Long userId, @ApiIgnore @RequestBody @Validated({UpdateFor.class, OneBy.PK.class}) PostRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uPostBaseService.updateDraft(rec.draftUpdater(userId));
            if (res >= 1) {
                return HttpResUtil.succeed("保存成功");
            } else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("3.1 显示我的文章列表")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "文章状态", required = true),
            @ApiImplicitParam(name = "postClassify", value = "文章分类", required = false),
    })
    @RequestMapping(value = "/list/self", method = RequestMethod.GET)
    public HttpRes postListSelfHandler(@ApiIgnore @UserJWT Long userId, @ApiIgnore @RequestBody @Validated({ListBy.Owner.class}) PostRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        rec.setUserId(userId);
        List<PostBase> list = uPostBaseService.listByOwner(rec);
        PageInfo<PostBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("3.2 显示分类文章")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postClassify", value = "文章分类", required = true),
    })
    @RequestMapping(value = "/list/classify", method = RequestMethod.GET)
    public HttpRes postListClassifyHandler(@ApiIgnore @RequestBody @Validated({ListBy.Classify.class}) PostRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<PostBase> list = uPostBaseService.listByFilter(rec);
        PageInfo<PostBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("3.3 显示某人的文章")
    @ApiOperationSupport(order = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
    })
    @RequestMapping(value = "/list/owner", method = RequestMethod.GET)
    public HttpRes postListOwnerHandler(@ApiIgnore @RequestBody @Validated({OneBy.FK.class}) PostRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<PostBase> list = uPostBaseService.listByFilter(rec);
        PageInfo<PostBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("4.1 下架一篇文章")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "文章编号", required = true),
    })
    @RequestMapping(value = "/back", method = RequestMethod.POST)
    public HttpRes postBackHandler(@ApiIgnore @UserJWT Long userId, @ApiIgnore @RequestBody @Validated({OneBy.PK.class}) PostRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uPostBaseService.updateStatus(userId, rec.getPostId(), StatusEnum.PostStatus.DRAFT);
            if (res >= 1) {
                return HttpResUtil.succeed("下架成功");
            } else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("4.2 将一篇文章放回收站")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "文章编号", required = true),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes postDeleteHandler(@ApiIgnore @UserJWT Long userId, @ApiIgnore @RequestBody @Validated({OneBy.PK.class}) PostRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uPostBaseService.updateStatus(userId, rec.getPostId(), StatusEnum.PostStatus.TRASHED);
            if (res >= 1) {
                return HttpResUtil.succeed("删除成功");
            } else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }


}
