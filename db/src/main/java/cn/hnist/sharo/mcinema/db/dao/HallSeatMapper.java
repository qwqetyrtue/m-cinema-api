package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.HallSeat;
import cn.hnist.sharo.mcinema.db.pojo.HallSeatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HallSeatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    long countByExample(HallSeatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int deleteByExample(HallSeatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long seatId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int insert(HallSeat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int insertSelective(HallSeat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    HallSeat selectOneByExample(HallSeatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    HallSeat selectOneByExampleSelective(@Param("example") HallSeatExample example, @Param("selective") HallSeat.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    List<HallSeat> selectByExampleSelective(@Param("example") HallSeatExample example, @Param("selective") HallSeat.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    List<HallSeat> selectByExample(HallSeatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    HallSeat selectByPrimaryKeySelective(@Param("seatId") Long seatId, @Param("selective") HallSeat.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    HallSeat selectByPrimaryKey(Long seatId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    HallSeat selectByPrimaryKeyWithLogicalDelete(@Param("seatId") Long seatId, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") HallSeat record, @Param("example") HallSeatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") HallSeat record, @Param("example") HallSeatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(HallSeat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HallSeat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") HallSeatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_seat
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Long seatId);
}