package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.PostBase;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class PostRec extends PostBase implements VoRec<PostBase> {

    @NotNull(message = "文章编号不能为空", groups = {OneBy.PK.class})
    private Long postId;
    @NotNull(message = "审核结果不能为空", groups = {UpdateFor.Review.class})
    private Boolean pass;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTimeDown;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewTimeDown;

    @Override
    public PostBase adder(Function<PostBase, PostBase> set) {
        return null;
    }

    @Override
    public PostBase updater(Function<PostBase, PostBase> set) {
        PostBase update = new PostBase();
        update.setReviewTime(LocalDateTime.now());
        return set.apply(update);
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    @Override
    public Long getPostId() {
        return postId;
    }

    @Override
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public LocalDateTime getReleaseTimeUp() {
        return releaseTimeUp;
    }

    public void setReleaseTimeUp(LocalDateTime releaseTimeUp) {
        this.releaseTimeUp = releaseTimeUp;
    }

    public LocalDateTime getReleaseTimeDown() {
        return releaseTimeDown;
    }

    public void setReleaseTimeDown(LocalDateTime releaseTimeDown) {
        this.releaseTimeDown = releaseTimeDown;
    }

    public LocalDateTime getReviewTimeUp() {
        return reviewTimeUp;
    }

    public void setReviewTimeUp(LocalDateTime reviewTimeUp) {
        this.reviewTimeUp = reviewTimeUp;
    }

    public LocalDateTime getReviewTimeDown() {
        return reviewTimeDown;
    }

    public void setReviewTimeDown(LocalDateTime reviewTimeDown) {
        this.reviewTimeDown = reviewTimeDown;
    }
}
