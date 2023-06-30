package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.adminapi.vo.CommentRec;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.CommentBase;
import cn.hnist.sharo.mcinema.db.service.admin.CommentBaseService;
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

@Api(tags = "评论的操作接口")
@RestController
@RequestMapping(value = "/api/admin/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Resource
    CommentBaseService commentBaseService;

    @ApiOperation("1.1 查询评论列表-情景筛选")
    @ApiOperationSupport(order = 1)
    @RequiresPermissions("admin:comment:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "评论情景", required = false),
            @ApiImplicitParam(name = "conditionId", value = "对象编号", required = false),
            @ApiImplicitParam(name = "createTimeUp", value = "评论时间上限", required = false),
            @ApiImplicitParam(name = "createTimeDown", value = "评论时间下限", required = false)
    })
    @RequestMapping(value = "/list/condition", method = RequestMethod.GET)
    public HttpRes commentListByConditionHandler(@ApiIgnore CommentRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<CommentBase> list = commentBaseService.listByCondition(rec, rec.getCreateTimeUp(), rec.getCreateTimeDown());
        PageInfo<CommentBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("1.2 查询评论列表-评论者筛选")
    @ApiOperationSupport(order = 2)
    @RequiresPermissions("admin:comment:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "评论人", required = true),
            @ApiImplicitParam(name = "createTime", value = "评论时间", required = false),
            @ApiImplicitParam(name = "createTimeUp", value = "评论时间上限", required = false),
            @ApiImplicitParam(name = "createTimeDown", value = "评论时间下限", required = false)
    })
    @RequestMapping(value = "/list/user", method = RequestMethod.GET)
    public HttpRes commentListByUserHandler(@ApiIgnore @Validated({ListBy.Owner.class}) CommentRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<CommentBase> list = commentBaseService.listByUser(rec, rec.getCreateTimeUp(), rec.getCreateTimeDown());
        PageInfo<CommentBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation("1.3 查询子评论")
    @ApiOperationSupport(order = 3)
    @RequiresPermissions("admin:comment:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父评论", required = true),
    })
    @RequestMapping(value = "/list/parent", method = RequestMethod.GET)
    public HttpRes commentListByParentHandler(@ApiIgnore @Validated({ListBy.Root.class}) CommentRec rec, PagingRec paging) {
        List<CommentBase> list = commentBaseService.listByParent(rec.getParentId());
        return HttpResUtil.succeedList("查询成功", list);
    }

    @ApiOperation("2.1 删除一条评论")
    @ApiOperationSupport(order = 10)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论编号", required = true),
    })
    @RequiresPermissions("admin:comment:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public HttpRes commentDeleteHandler(@ApiIgnore @Validated({OneBy.PK.class}) CommentRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = commentBaseService.delete(rec.getCommentId());
            if (res >= 1)
                return HttpResUtil.succeed("删除成功");
            else return HttpResUtil.fail("删除失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }
}
