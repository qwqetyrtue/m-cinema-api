package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.FilmCollect;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class FilmCollectRec extends FilmCollect implements VoRec<FilmCollect> {
    private Long userId;
    @NotNull(message = "电影不能为空", groups = {InsertBy.class, OneBy.PK.class})
    private Long filmId;
    @NotNull(message = "类型不能为空", groups = {InsertBy.class,ListBy.Type.class})
    private Short type;
    @NotNull(message = "影片品类不能为空", groups = {ListBy.Type.class})
    private String filmType;

    @Override
    public FilmCollect adder(Function<FilmCollect, FilmCollect> set) {
        FilmCollect add = new FilmCollect();
        add.setFilmId(filmId);
        add.setType(type);
        add.setCreateTime(LocalDateTime.now());
        return set.apply(add);
    }

    public FilmCollect adder(Long userId) {
        return adder(add -> {
            add.setUserId(userId);
            return add;
        });
    }

    @Override
    public FilmCollect updater(Function<FilmCollect, FilmCollect> set) {
        return null;
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
    public Long getFilmId() {
        return filmId;
    }

    @Override
    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Override
    public Short getType() {
        return type;
    }

    @Override
    public void setType(Short type) {
        this.type = type;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }
}
