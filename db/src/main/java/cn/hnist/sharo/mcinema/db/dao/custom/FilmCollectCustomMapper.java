package cn.hnist.sharo.mcinema.db.dao.custom;

import cn.hnist.sharo.mcinema.db.pojo.FilmBase;
import cn.hnist.sharo.mcinema.db.pojo.custom.FilmCollectCount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface FilmCollectCustomMapper {
    @Select("SELECT type,count(*) as num FROM `film_collect` WHERE user_id = #{userId} GROUP BY type")
    List<FilmCollectCount> countAllCollectGroup(@Param("userId") Long userId);
}
