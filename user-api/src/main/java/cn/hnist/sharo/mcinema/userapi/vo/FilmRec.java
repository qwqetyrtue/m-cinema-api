package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.FilmBase;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

public class FilmRec extends FilmBase implements VoRec<FilmBase> {

    @NotNull(message = "电影编号不能为空", groups = {OneBy.PK.class})
    private Long filmId;

    @NotEmpty(message = "展示类型不能为空", groups = {ListBy.Index.class})
    private String type;

    @NotEmpty(message = "名称不能为空", groups = {ListBy.Name.class, ListBy.Type.class})
    private String name;
    @NotEmpty(message = "电影品类不能为空", groups = {ListBy.Type.class})
    private String filmType;

    @Override
    public FilmBase adder(Function<FilmBase, FilmBase> set) {
        return null;
    }

    @Override
    public FilmBase updater(Function<FilmBase, FilmBase> set) {
        return null;
    }

    @Override
    public Long getFilmId() {
        return filmId;
    }

    @Override
    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFilmType() {
        return filmType;
    }

    @Override
    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }
}
