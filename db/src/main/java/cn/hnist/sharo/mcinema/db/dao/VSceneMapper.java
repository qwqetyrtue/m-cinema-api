package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.VScene;
import cn.hnist.sharo.mcinema.db.pojo.VSceneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VSceneMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    long countByExample(VSceneExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    int deleteByExample(VSceneExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    int insert(VScene record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    int insertSelective(VScene record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    VScene selectOneByExample(VSceneExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    VScene selectOneByExampleSelective(@Param("example") VSceneExample example, @Param("selective") VScene.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    List<VScene> selectByExampleSelective(@Param("example") VSceneExample example, @Param("selective") VScene.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    List<VScene> selectByExample(VSceneExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VScene record, @Param("example") VSceneExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_scene
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VScene record, @Param("example") VSceneExample example);
}