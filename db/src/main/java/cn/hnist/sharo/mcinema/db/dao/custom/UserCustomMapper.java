package cn.hnist.sharo.mcinema.db.dao.custom;

import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface UserCustomMapper {
    @Select("SELECT (SELECT COUNT(*) FROM user_base ) AS `all`," +
            "COUNT( CASE WHEN create_time >= DATE_SUB( NOW(), INTERVAL 7 DAY ) THEN 1 END ) AS recent," +
            "COUNT( CASE WHEN gender = 1 THEN 1 END ) AS female," +
            "COUNT( CASE WHEN gender = 2 THEN 1 END ) AS male FROM user_base")
    Map<String,Object> statistics();
}