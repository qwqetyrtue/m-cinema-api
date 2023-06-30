package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.CommentBase;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Function;

public class CommentRec extends CommentBase implements VoRec<CommentBase> {
    @NotNull(message = "评论编号不能为空", groups = {InsertBy.Vote.class})
    private Long commentId;
    @NotNull(message = "用户编号不能为空", groups = {InsertBy.Vote.class})
    private Long userId;
    @NotNull(message = "回复场景不能为空", groups = {InsertBy.class, ListBy.Root.class, ListBy.Branch.class})
    private Short condition;
    @NotNull(message = "回复对象不能为空", groups = {InsertBy.class, ListBy.Root.class, ListBy.Branch.class})
    private Long conditionId;
    @NotEmpty(message = "回复内容不能为空", groups = {InsertBy.class})
    private String content;
    @NotNull(message = "评分不能为空", groups = {InsertBy.Root.class})
    private BigDecimal star;
    @NotNull(message = "父评论不能为空", groups = {InsertBy.Branch.class, ListBy.Branch.class})
    private Long parentId;



    @Override
    public CommentBase adder(Function<CommentBase, CommentBase> fuc) {
        CommentBase add = new CommentBase();
        add.setCondition(condition);
        add.setConditionId(conditionId);
        add.setContent(content);
        add.setCreateTime(LocalDateTime.now());
        return fuc.apply(add);
    }

    public CommentBase rootAdder(Long userId) {
        return adder((add) -> {
            add.setStar(star);
            add.setParentId(null);
            add.setUserId(userId);
            return add;
        });
    }

    public CommentBase branchAdder(Long userId) {
        return adder((add) -> {
            add.setUserId(userId);
            add.setParentId(parentId);
            return add;
        });
    }

    @Override
    public CommentBase updater(Function<CommentBase, CommentBase> fuc) {
        return null;
    }

    @Override
    public Short getCondition() {
        return condition;
    }

    public void setCondition(Short condition) {
        this.condition = condition;
    }

    @Override
    public Long getConditionId() {
        return conditionId;
    }

    @Override
    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public BigDecimal getStar() {
        return star;
    }

    @Override
    public void setStar(BigDecimal star) {
        this.star = star;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public Long getCommentId() {
        return commentId;
    }

    @Override
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
