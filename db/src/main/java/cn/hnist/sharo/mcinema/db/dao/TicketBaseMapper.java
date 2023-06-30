package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.TicketBase;
import cn.hnist.sharo.mcinema.db.pojo.TicketBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TicketBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    long countByExample(TicketBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int deleteByExample(TicketBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long ticketId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int insert(TicketBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int insertSelective(TicketBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    TicketBase selectOneByExample(TicketBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    TicketBase selectOneByExampleSelective(@Param("example") TicketBaseExample example, @Param("selective") TicketBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    List<TicketBase> selectByExampleSelective(@Param("example") TicketBaseExample example, @Param("selective") TicketBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    List<TicketBase> selectByExample(TicketBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    TicketBase selectByPrimaryKeySelective(@Param("ticketId") Long ticketId, @Param("selective") TicketBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    TicketBase selectByPrimaryKey(Long ticketId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    TicketBase selectByPrimaryKeyWithLogicalDelete(@Param("ticketId") Long ticketId, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TicketBase record, @Param("example") TicketBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TicketBase record, @Param("example") TicketBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TicketBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TicketBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") TicketBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ticket_base
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Long ticketId);
}