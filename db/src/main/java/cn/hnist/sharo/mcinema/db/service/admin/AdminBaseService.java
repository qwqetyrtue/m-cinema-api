package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.AdminBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;
import cn.hnist.sharo.mcinema.db.pojo.AdminBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminBaseService implements CRUDService<AdminBase, Integer> {
    @Resource
    AdminBaseMapper adminMapper;

    /**
     * <h2>由管理员编号查询管理员信息</h2>
     *
     * @param adminId 管理员编号
     * @return AdminBase
     */
    @Override
    public AdminBase oneByPK(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    public AdminBase oneByAccount(String account) {
        AdminBaseExample example = new AdminBaseExample();
        AdminBaseExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<AdminBase> res = adminMapper.selectByExample(criteria.example());
        if (res != null && res.size() > 0)
            return res.get(0);
        return null;
    }

    public AdminBase oneByAuth(String username, String password) {
        AdminBaseExample example = new AdminBaseExample();
        AdminBaseExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(username).andPwdEqualTo(password);
        return adminMapper.selectOneByExample(criteria.example());
    }

    public List<AdminBase> listByAccount(String account) {
        AdminBaseExample example = new AdminBaseExample();
        AdminBaseExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        return adminMapper.selectByExample(criteria.example());
    }

    public List<AdminBase> listByFilter(AdminBase filter) {
        AdminBaseExample e = new AdminBaseExample();
        AdminBaseExample.Criteria criteria = e.createCriteria();
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        if (DataUtil.checkEmpty(filter.getName()))
            criteria.andNameLike("%" + filter.getName() + "%");
        if (DataUtil.checkEmpty(filter.getRoleIdList()))
            criteria.andRoleIdListEqualTo(filter.getRoleIdList());
        if (DataUtil.checkEmpty(filter.getPhone()))
            criteria.andPhoneLike("%" + filter.getPhone() + "%");
        return adminMapper.selectByExample(criteria.example());
    }

    @Override
    public int update(AdminBase admin) throws DatabaseException {
        AdminBase res = oneByPK(admin.getAdminId());
        if (res == null)
            throw new DatabaseException("未查询到此管理员", ErrorEnum.DATA_NOT_FOUND);
        return updateNoCheck(admin);
    }

    public int updateNoCheck(AdminBase admin) {
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public int insert(AdminBase admin) throws RuntimeException {
        AdminBase res = oneByAccount(admin.getAccount());
        if (res != null) {
            throw new DatabaseException("管理员账号重复", ErrorEnum.CONSTRAINT_EXCEPTION);
        }
        return adminMapper.insertSelective(admin);
    }

    @Override
    public int delete(Integer adminId) throws RuntimeException {
        AdminBase admin = oneByPK(adminId);
        if (admin == null)
            throw new DatabaseException("未查询此管理员", ErrorEnum.DATA_NOT_FOUND);
        else if (admin.getRoleIdList() != null && Arrays.asList(admin.getRoleIdList()).contains(1))
            throw new DatabaseException("删除超级管理员的账户", ErrorEnum.ILLEGAL_OPERATE);
        else if (admin.getDeleted())
            throw new DatabaseException("管理员已被删除", ErrorEnum.DATA_IS_DELETED);
        return adminMapper.logicalDeleteByPrimaryKey(adminId);
    }
}
