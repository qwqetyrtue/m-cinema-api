package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.CommentBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class CommentRec extends CommentBase implements VoRec<CommentBase> {
    @NotNull(message = "评论编号不能为空", groups = {OneBy.PK.class})
    private Long commentId;
    @NotNull(message = "用户编号不能为空", groups = {InsertBy.class, ListBy.Owner.class})
    private Long userId;
    @NotNull(message = "评论目标编号不能为空", groups = {InsertBy.class})
    private Long conditionId;
    @NotBlank(message = "评论内容不能为空", groups = {InsertBy.class, UpdateFor.class})
    private String content;
    @NotNull(message = "评论情景不能为空", groups = {InsertBy.class})
    private Short condition;
    @NotNull(message = "父评论编号不能为空", groups = {ListBy.Root.class})
    private Long parentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeDown;

    @Override
    public CommentBase adder(Function<CommentBase, CommentBase> set) {
        CommentBase add = new CommentBase();
        add.setCreateTime(LocalDateTime.now());
        add.setCommentId(commentId);
        add.setUserId(userId);
        add.setCondition(condition);
        add.setConditionId(conditionId);
        add.setContent(content);
        return set.apply(add);
    }

    public CommentBase adder() {
        return adder(add -> add);
    }

    @Override
    public CommentBase updater(Function<CommentBase, CommentBase> set) {
        CommentBase update = new CommentBase();
        return set.apply(update);
    }

    public CommentBase updater() {
        return updater(update -> {
            update.setContent(content);
            return update;
        });
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

    @Override
    public Long getConditionId() {
        return conditionId;
    }

    @Override
    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    @Override
    public Short getCondition() {
        return condition;
    }

    @Override
    public void setCondition(Short condition) {
        this.condition = condition;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTimeUp() {
        return createTimeUp;
    }

    public void setCreateTimeUp(LocalDateTime createTimeUp) {
        this.createTimeUp = createTimeUp;
    }

    public LocalDateTime getCreateTimeDown() {
        return createTimeDown;
    }

    public void setCreateTimeDown(LocalDateTime createTimeDown) {
        this.createTimeDown = createTimeDown;
    }
}
