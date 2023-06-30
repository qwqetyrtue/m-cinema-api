package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.SceneBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

public class SceneRec extends SceneBase implements VoRec<SceneBase> {
    @NotNull(message = "排场编号不能为空", groups = {OneBy.PK.class})
    private Long sceneId;
    @NotNull(message = "基础价格不能为空", groups = {InsertBy.class,UpdateFor.Profile.class})
    private BigDecimal price;
    @NotNull(message = "影厅编号不能为空", groups = {InsertBy.class, ListBy._1.class})
    private Long hallId;
    @NotNull(message = "电影编号不能为空", groups = {InsertBy.class, UpdateFor.Secure.class, ListBy._2.class})
    private Long filmId;

    @NotNull(message = "排场日期不能为空", groups = {InsertBy.class, UpdateFor.Secure.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;
    @NotEmpty(message = "放映语言不能为空", groups = {InsertBy.class})
    private String language;
    @NotEmpty(message = "放映类型不能为空", groups = {InsertBy.class})
    private String sceneType;

    @NotNull(message = "排场日期上限不能为空",groups = {ListBy.Time.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayUp;
    @NotNull(message = "排场日期下限不能为空",groups = {ListBy.Time.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayDown;

    @Override
    public SceneBase adder(Function<SceneBase, SceneBase> set) {
        SceneBase add = new SceneBase();
        LocalDateTime now = LocalDateTime.now();
        add.setCreateTime(now);
        add.setUpdateTime(now);
        return set.apply(add);
    }

    public SceneBase adder() {
        return adder((add) -> {
            add.setPrice(price);
            add.setHallId(hallId);
            add.setFilmId(filmId);
            add.setBeginTime(beginTime);
            add.setLanguage(language);
            add.setSceneType(sceneType);
            return add;
        });
    }

    @Override
    public SceneBase updater(Function<SceneBase, SceneBase> set) {
        SceneBase update = new SceneBase();
        update.setSceneId(sceneId);
        update.setUpdateTime(LocalDateTime.now());
        return update;
    }

    public SceneBase secureUpdater() {
        return updater((update) -> {
            update.setFilmId(filmId);
            update.setBeginTime(beginTime);
            return update;
        });
    }

    public SceneBase priceUpdater() {
        return updater((update) -> {
            update.setPrice(price);
            return update;
        });
    }


    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public Long getHallId() {
        return hallId;
    }

    @Override
    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    @Override
    public Long getFilmId() {
        return filmId;
    }

    @Override
    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getSceneType() {
        return sceneType;
    }

    @Override
    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    @Override
    public Long getSceneId() {
        return sceneId;
    }

    @Override
    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    @Override
    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalDate getDayUp() {
        return dayUp;
    }

    public void setDayUp(LocalDate dayUp) {
        this.dayUp = dayUp;
    }

    public LocalDate getDayDown() {
        return dayDown;
    }

    public void setDayDown(LocalDate dayDown) {
        this.dayDown = dayDown;
    }
}