package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.CommentBase;
import cn.hnist.sharo.mcinema.db.pojo.CommentBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    long countByExample(CommentBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int deleteByExample(CommentBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long commentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int insert(CommentBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int insertSelective(CommentBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    CommentBase selectOneByExample(CommentBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    CommentBase selectOneByExampleSelective(@Param("example") CommentBaseExample example, @Param("selective") CommentBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    List<CommentBase> selectByExampleSelective(@Param("example") CommentBaseExample example, @Param("selective") CommentBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    List<CommentBase> selectByExample(CommentBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    CommentBase selectByPrimaryKeySelective(@Param("commentId") Long commentId, @Param("selective") CommentBase.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    CommentBase selectByPrimaryKey(Long commentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CommentBase record, @Param("example") CommentBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CommentBase record, @Param("example") CommentBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CommentBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_base
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CommentBase record);
}