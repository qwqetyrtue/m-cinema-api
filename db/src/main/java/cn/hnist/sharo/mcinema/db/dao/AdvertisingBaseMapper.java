package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBase;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertisingBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    long countByExample(AdvertisingBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int deleteByExample(AdvertisingBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long advertisingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int insert(AdvertisingBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int insertSelective(AdvertisingBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    AdvertisingBase selectOneByExample(AdvertisingBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    AdvertisingBase selectOneByExampleSelective(@Param("example") AdvertisingBaseExample example, @Param("selective") AdvertisingBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    List<AdvertisingBase> selectByExampleSelective(@Param("example") AdvertisingBaseExample example, @Param("selective") AdvertisingBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    List<AdvertisingBase> selectByExample(AdvertisingBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    AdvertisingBase selectByPrimaryKeySelective(@Param("advertisingId") Long advertisingId, @Param("selective") AdvertisingBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    AdvertisingBase selectByPrimaryKey(Long advertisingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AdvertisingBase record, @Param("example") AdvertisingBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AdvertisingBase record, @Param("example") AdvertisingBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AdvertisingBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertising_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AdvertisingBase record);
}