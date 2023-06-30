package cn.hnist.sharo.mcinema.db.dao;

import cn.hnist.sharo.mcinema.db.pojo.AdminPermission;
import cn.hnist.sharo.mcinema.db.pojo.AdminPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    long countByExample(AdminPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int deleteByExample(AdminPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer permissionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int insert(AdminPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int insertSelective(AdminPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    AdminPermission selectOneByExample(AdminPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    AdminPermission selectOneByExampleSelective(@Param("example") AdminPermissionExample example, @Param("selective") AdminPermission.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    List<AdminPermission> selectByExampleSelective(@Param("example") AdminPermissionExample example, @Param("selective") AdminPermission.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    List<AdminPermission> selectByExample(AdminPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    AdminPermission selectByPrimaryKeySelective(@Param("permissionId") Integer permissionId, @Param("selective") AdminPermission.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    AdminPermission selectByPrimaryKey(Integer permissionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    AdminPermission selectByPrimaryKeyWithLogicalDelete(@Param("permissionId") Integer permissionId, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AdminPermission record, @Param("example") AdminPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AdminPermission record, @Param("example") AdminPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AdminPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AdminPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") AdminPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_permission
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer permissionId);
}