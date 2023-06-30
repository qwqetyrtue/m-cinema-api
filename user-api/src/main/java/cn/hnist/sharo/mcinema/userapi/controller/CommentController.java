package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.model.VoteEnum;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.db.pojo.VComment;
import cn.hnist.sharo.mcinema.db.service.user.U_CommentBaseService;
import cn.hnist.sharo.mcinema.userapi.annotation.UserJWT;
import cn.hnist.sharo.mcinema.userapi.vo.CommentRec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

@Api(tags = "评论的操作接口")
@ApiSupport(order = 7)

@RestController
@RequestMapping(value = "/api/user/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Resource
    U_CommentBaseService uCommentBaseService;

    @ApiOperation("1.1 添加一条父评论")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "评论的情景", required = true),
            @ApiImplicitParam(name = "conditionId", value = "评论对象Id", required = true),
            @ApiImplicitParam(name = "content", value = "评论内容", required = true),
            @ApiImplicitParam(name = "star", value = "评分", required = true)
    })
    @RequestMapping(value = "/insert/root", method = RequestMethod.POST)
    public HttpRes commentConditionInsertHandler(@UserJWT Long userId, @ApiIgnore @RequestBody @Validated({InsertBy.class, InsertBy.Root.class}) CommentRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uCommentBaseService.insert(rec.rootAdder(userId));
            if (res >= 1)
                return HttpResUtil.succeed("添加成功");
            else return HttpResUtil.fail("添加失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("1.2 添加一条子评论")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "评论的情景", required = true),
            @ApiImplicitParam(name = "conditionId", value = "评论对象Id", required = true),
            @ApiImplicitParam(name = "content", value = "评论内容", required = true),
            @ApiImplicitParam(name = "parentId", value = "父评论Id", required = true)
    })
    @RequestMapping(value = "/insert/branch", method = RequestMethod.POST)
    public HttpRes commentCommentInsertHandler(@UserJWT Long userId, @ApiIgnore @RequestBody @Validated({InsertBy.class, InsertBy.Branch.class}) CommentRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uCommentBaseService.insert(rec.branchAdder(userId));
            if (res >= 1)
                return HttpResUtil.succeed("添加成功");
            else return HttpResUtil.fail("添加失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }


    @ApiOperation("2.1 删除自己的评论")
    @ApiOperationSupport(order = 10)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes commentDeleteHandler(@UserJWT Long userId, @RequestBody @Validated({OneBy.PK.class}) CommentRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uCommentBaseService.delete(userId, rec.getCommentId());
            if (res >= 1)
                return HttpResUtil.succeed("删除成功");
            else return HttpResUtil.fail("删除失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("3.1 显示对当前对象的单层评论")
    @ApiOperationSupport(order = 20)
    @RequestMapping(value = "/list/layer", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "评论的情景", required = true),
            @ApiImplicitParam(name = "conditionId", value = "评论对象Id", required = true),
    })
    public HttpRes commentListByConditionHandler(@ApiIgnore @Validated({ListBy.Root.class}) CommentRec rec, PagingRec paging) {
        return HttpResUtil.hasError(() -> {
            PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
            List<VComment> list = uCommentBaseService.listLayerByCondition(rec.getCondition(), rec.getConditionId());
            PageInfo<VComment> info = new PageInfo<>(list);
            return HttpResUtil.succeedList("查询成功", info);
        });
    }

    @ApiOperation("3.2 显示当前对象中评论的子评论")
    @ApiOperationSupport(order = 21)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "评论的情景", required = true),
            @ApiImplicitParam(name = "conditionId", value = "评论对象Id", required = true),
            @ApiImplicitParam(name = "parentId", value = "父评论Id", required = true),
    })
    @RequestMapping(value = "/list/child", method = RequestMethod.GET)
    public HttpRes commentListByParentHandler(@ApiIgnore @Validated({ListBy.Branch.class}) CommentRec rec, PagingRec paging) {
        return HttpResUtil.hasError(() -> {
            PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
            List<VComment> list = uCommentBaseService.listLayerByParent(rec.getCondition(), rec.getConditionId(), rec.getParentId());
            PageInfo<VComment> info = new PageInfo<>(list);
            return HttpResUtil.succeedList("查询成功", info);
        });
    }

    @ApiOperation("4.1 给评论点赞")
    @ApiOperationSupport(order = 31)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conditionId", value = "评论Id", required = true),
            @ApiImplicitParam(name = "userId", value = "用户名", required = true),
    })
    @RequestMapping(value = "/vote/{vote}", method = RequestMethod.POST)
    public HttpRes commentUpvoteHandler(@ApiIgnore @RequestBody @Validated({InsertBy.Vote.class}) CommentRec rec, @PathVariable("vote") String vote) {
        return HttpResUtil.hasError(() -> {
            VoteEnum voteEnum = VoteEnum.parse(vote);
            if(voteEnum == null)
                return HttpResUtil.fail("路径错误",ErrorEnum.ILLEGAL_URL);
            int res = uCommentBaseService.vote(rec.getCommentId(), rec.getUserId(), voteEnum);
            if (res >= 1)
                return HttpResUtil.succeed("操作成功");
            else return HttpResUtil.fail("操作失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

}
