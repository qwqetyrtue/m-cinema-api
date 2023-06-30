package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.SceneBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.function.Function;

public class SceneRec extends SceneBase implements VoRec<SceneRec> {
    @NotNull(message = "场次编号不能为空",groups = {OneBy.PK.class})
    private Long sceneId;
    @NotNull(message = "电影编号不能为空",groups = {ListBy.Time.class})
    private Long filmId;
    @NotNull(message = "日期不能为空",groups = {ListBy.Time.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;


    @Override
    public SceneRec adder(Function<SceneRec, SceneRec> set) {
        return null;
    }

    @Override
    public SceneRec updater(Function<SceneRec, SceneRec> set) {
        return null;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
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
    public Long getSceneId() {
        return sceneId;
    }

    @Override
    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }
}
