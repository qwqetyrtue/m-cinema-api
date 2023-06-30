package cn.hnist.sharo.mcinema.db.dao.custom;

import cn.hnist.sharo.mcinema.db.pojo.FilmBase;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FilmCustomMapper {
    @Select("SELECT DISTINCT fb.`name`,fb.`film_id` as filmId,fb.`images`,fb.`actor`,fb.`score` " +
            "FROM film_base AS fb " +
            "INNER JOIN scene_base AS sb ON fb.film_id = sb.film_id " +
            "WHERE DATE(sb.begin_time) = CURDATE()")
    List<FilmBase> listHotFilm();

    @Select("SELECT DISTINCT fb.`name`,fb.`film_id` as filmId,fb.`images`,fb.`actor`,fb.`score` " +
            "FROM film_base AS fb " +
            "INNER JOIN scene_base AS sb ON fb.film_id = sb.film_id " +
            "WHERE DATE(sb.begin_time) > CURDATE() + INTERVAL 7 DAY")
    List<FilmBase> listSoonFilm();

    @Select("SELECT DISTINCT fb.`name`,fb.`film_id` as filmId,fb.`images`,fb.`actor`,fb.`score` " +
            "FROM film_base AS fb " +
            "INNER JOIN scene_base AS sb ON fb.film_id = sb.film_id " +
            "WHERE DATE(sb.begin_time) BETWEEN CURDATE() + INTERVAL 1 DAY AND CURDATE() + INTERVAL 7 DAY")
    List<FilmBase> listToBeFilm();
}
