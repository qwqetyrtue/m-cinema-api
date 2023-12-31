package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.FilmWant;
import cn.hnist.sharo.mcinema.db.pojo.FilmWantExample;
import cn.hnist.sharo.mcinema.db.pojo.FilmWantKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FilmWantMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    long countByExample(FilmWantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int deleteByExample(FilmWantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(FilmWantKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int insert(FilmWant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int insertSelective(FilmWant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    FilmWant selectOneByExample(FilmWantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    FilmWant selectOneByExampleSelective(@Param("example") FilmWantExample example, @Param("selective") FilmWant.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    List<FilmWant> selectByExampleSelective(@Param("example") FilmWantExample example, @Param("selective") FilmWant.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    List<FilmWant> selectByExample(FilmWantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    FilmWant selectByPrimaryKeySelective(@Param("record") FilmWantKey key, @Param("selective") FilmWant.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    FilmWant selectByPrimaryKey(FilmWantKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") FilmWant record, @Param("example") FilmWantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") FilmWant record, @Param("example") FilmWantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FilmWant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_want
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FilmWant record);
}