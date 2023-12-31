package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.FilmCollect;
import cn.hnist.sharo.mcinema.db.pojo.FilmCollectExample;
import cn.hnist.sharo.mcinema.db.pojo.FilmCollectKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FilmCollectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    long countByExample(FilmCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int deleteByExample(FilmCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(FilmCollectKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int insert(FilmCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int insertSelective(FilmCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    FilmCollect selectOneByExample(FilmCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    FilmCollect selectOneByExampleSelective(@Param("example") FilmCollectExample example, @Param("selective") FilmCollect.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    List<FilmCollect> selectByExampleSelective(@Param("example") FilmCollectExample example, @Param("selective") FilmCollect.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    List<FilmCollect> selectByExample(FilmCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    FilmCollect selectByPrimaryKeySelective(@Param("record") FilmCollectKey key, @Param("selective") FilmCollect.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    FilmCollect selectByPrimaryKey(FilmCollectKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") FilmCollect record, @Param("example") FilmCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") FilmCollect record, @Param("example") FilmCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FilmCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table film_collect
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FilmCollect record);
}