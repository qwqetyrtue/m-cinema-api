package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.DiscountBase;
import cn.hnist.sharo.mcinema.db.pojo.DiscountBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiscountBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    long countByExample(DiscountBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int deleteByExample(DiscountBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long discountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int insert(DiscountBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int insertSelective(DiscountBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    DiscountBase selectOneByExample(DiscountBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    DiscountBase selectOneByExampleSelective(@Param("example") DiscountBaseExample example, @Param("selective") DiscountBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    List<DiscountBase> selectByExampleSelective(@Param("example") DiscountBaseExample example, @Param("selective") DiscountBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    List<DiscountBase> selectByExample(DiscountBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    DiscountBase selectByPrimaryKeySelective(@Param("discountId") Long discountId, @Param("selective") DiscountBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    DiscountBase selectByPrimaryKey(Long discountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DiscountBase record, @Param("example") DiscountBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DiscountBase record, @Param("example") DiscountBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DiscountBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DiscountBase record);
}