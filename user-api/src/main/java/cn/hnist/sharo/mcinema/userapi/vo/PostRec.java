package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.model.StatusEnum;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.PostBase;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.function.Function;

public class PostRec extends PostBase implements VoRec<PostBase> {
    @NotNull(message = "文章编号不能为空", groups = {OneBy.PK.class})
    private Long postId;
    @NotNull(message = "用户编号不能为空", groups = {OneBy.FK.class})
    private Long userId;
    @Size(min = 0, max = 50, message = "标题长度超出范围", groups = {InsertBy.Draft.class, UpdateFor.class})
    private String title;
    @NotBlank(message = "文章的内容不能为空", groups = {InsertBy.Draft.class, UpdateFor.class})
    private String content;
    @Size(min = 0, max = 254, message = "文章分类长度超出范围", groups = {
            InsertBy.Draft.class, ListBy.Owner.class, ListBy.Classify.class, UpdateFor.class})
    @NotBlank(message = "文章分类不能为空", groups = {ListBy.Classify.class})
    private String postClassify;
    @Range(min = 0, max = 3, message = "无法识别的文章状态", groups = {ListBy.Owner.class})
    @NotNull(message = "文章状态不能为空", groups = {ListBy.Owner.class})
    private Short postStatus;

    @Override
    public PostBase adder(Function<PostBase, PostBase> set) {
        PostBase add = new PostBase();
        LocalDateTime now = LocalDateTime.now();
        add.setCreateTime(now);
        add.setUpdateTime(now);
        add.setTitle(title);
        add.setPostClassify(postClassify);
        add.setContent(content);
        return set.apply(add);
    }

    public PostBase draftAdder(Long userId) {
        return adder((add) -> {
            add.setPostStatus(StatusEnum.PostStatus.DRAFT.v());
            add.setUserId(userId);
            return add;
        });
    }

    @Override
    public PostBase updater(Function<PostBase, PostBase> set) {
        PostBase update = new PostBase();
        update.setPostId(postId);
        update.setUpdateTime(LocalDateTime.now());
        return set.apply(update);
    }

    public PostBase draftUpdater(Long userId) {
        return updater((update) -> {
            update.setUserId(userId);
            update.setTitle(title);
            update.setPostClassify(postClassify);
            update.setContent(content);
            return update;
        });
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
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getPostClassify() {
        return postClassify;
    }

    @Override
    public void setPostClassify(String postClassify) {
        this.postClassify = postClassify;
    }

    @Override
    public Long getPostId() {
        return postId;
    }

    @Override
    public void setPostId(Long postId) {
        this.postId = postId;
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
    public Short getPostStatus() {
        return postStatus;
    }

    @Override
    public void setPostStatus(Short postStatus) {
        this.postStatus = postStatus;
    }
}
