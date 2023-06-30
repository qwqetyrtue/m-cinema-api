package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.FilmBase;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class FilmRec extends FilmBase implements VoRec<FilmBase> {
    @NotNull(message = "电影Id不能为空", groups = {OneBy.PK.class})
    private Long filmId;
    @NotBlank(message = "电影名称不能为空", groups = {InsertBy.class})
    @Length(max = 255, message = "影片名称过长", groups = {InsertBy.class, UpdateFor.class, ListBy.Name.class})
    private String name;
    @NotEmpty(message = "影片分类不能为空", groups = {InsertBy.class})
    private String filmClassify;
    @Range(min = 1000, max = 10000, message = "年份值超出范围", groups = {InsertBy.class, UpdateFor.class})
    @NotNull(message = "年代不能为空", groups = {InsertBy.class})
    private Integer age;
    @NotBlank(message = "片源地不能为空", groups = {InsertBy.class})
    private String area;
    @NotBlank(message = "影片品类不能为空", groups = {InsertBy.class})
    private String filmType;
    @NotEmpty(message = "影片语言不能为空", groups = {InsertBy.class})
    private String language;
    @NotNull(message = "影片播放时长不能为空", groups = {InsertBy.class})
    @Range(min = 0,max = 300,message ="播放时长值超出范围",groups = {InsertBy.class})
    private Integer duration;
    @Length(max = 500, message = "影片简介过长", groups = {InsertBy.class, UpdateFor.class})
    private String synopsis;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeDown;
    private Integer ageUp;
    private Integer ageDown;
    private Integer durationUp;
    private Integer durationDown;

    @Override
    public FilmBase updater(Function<FilmBase, FilmBase> fuc) {
        FilmBase update = new FilmBase();
        // pk
        update.setFilmId(filmId);
        // 更新修改时间
        update.setUpdateTime(LocalDateTime.now());
        // 可修改属性
        return fuc.apply(update);
    }

    public FilmBase profileUpdater() {
        return updater((update) -> {
            update.setName(name);
            update.setSynopsis(getSynopsis());
            update.setActor(getActor());
            update.setOtherInfo(getOtherInfo());
            update.setForeignName(getForeignName());
            update.setAlias(getAlias());
            return update;
        });
    }

    public FilmBase classifyUpdater() {
        return updater((update) -> {
            update.setFilmClassify(filmClassify);
            update.setArea(area);
            update.setFilmType(filmType);
            update.setAge(age);
            update.setLanguage(language);
            update.setVersion(getVersion());
            return update;
        });
    }

    public FilmBase timeUpdater() {
        return updater((update) -> {
            update.setDuration(duration);
            return update;
        });
    }

    @Override
    public FilmBase adder(Function<FilmBase, FilmBase> set) {
        FilmBase add = new FilmBase();
        add.setDeleted(false);
        LocalDateTime now = LocalDateTime.now();
        add.setCreateTime(now);
        add.setUpdateTime(now);
        add.setName(name);
        add.setFilmClassify(filmClassify);
        add.setArea(area);
        add.setFilmType(filmType);
        add.setAge(age);
        add.setLanguage(language);
        add.setDuration(duration);

        add.setSynopsis(getSynopsis());
        add.setActor(getActor());
        add.setOtherInfo(getOtherInfo());
        add.setForeignName(getForeignName());
        add.setAlias(getAlias());
        add.setVersion(getVersion());
        return set.apply(add);
    }

    public FilmBase adder() {
        return adder(add -> add);
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

    public Integer getAgeUp() {
        return ageUp;
    }

    public void setAgeUp(Integer ageUp) {
        this.ageUp = ageUp;
    }

    public Integer getAgeDown() {
        return ageDown;
    }

    public void setAgeDown(Integer ageDown) {
        this.ageDown = ageDown;
    }

    public Integer getDurationUp() {
        return durationUp;
    }

    public void setDurationUp(Integer durationUp) {
        this.durationUp = durationUp;
    }

    public Integer getDurationDown() {
        return durationDown;
    }

    public void setDurationDown(Integer durationDown) {
        this.durationDown = durationDown;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getArea() {
        return area;
    }

    @Override
    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String getFilmType() {
        return filmType;
    }

    @Override
    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    @Override
    public Integer getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String getFilmClassify() {
        return filmClassify;
    }

    @Override
    public void setFilmClassify(String filmClassify) {
        this.filmClassify = filmClassify;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
    }
}
