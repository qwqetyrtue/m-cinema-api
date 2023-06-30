package cn.hnist.sharo.mcinema.db.service.system;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.db.dao.SystemConstantMapper;
import cn.hnist.sharo.mcinema.db.pojo.SystemConstant;
import cn.hnist.sharo.mcinema.db.pojo.SystemConstantExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConstantService implements CRUDService<SystemConstant, Integer> {

    @Resource
    SystemConstantMapper systemConstantMapper;

    public List<SystemConstant> listAll(Boolean delete) {
        SystemConstantExample example = new SystemConstantExample();
        SystemConstantExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(delete);
        return systemConstantMapper.selectByExample(criteria.example());
    }

    @Override
    public SystemConstant oneByPK(Integer constantId) {
        return systemConstantMapper.selectByPrimaryKey(constantId);
    }

    public SystemConstant oneByName(String name) {
        SystemConstantExample example = new SystemConstantExample();
        SystemConstantExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<SystemConstant> list = systemConstantMapper.selectByExample(criteria.example());
        try {
            return list.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public int insert(SystemConstant constant) throws RuntimeException {
        SystemConstant res = oneByName(constant.getName());
        if (res != null)
            throw new DatabaseException("变量重复", ErrorEnum.CONSTRAINT_EXCEPTION);
        return systemConstantMapper.insertSelective(constant);
    }

    @Override
    public int delete(Integer constantId) throws RuntimeException {
        SystemConstant res = oneByPK(constantId);
        if (res == null)
            throw new DatabaseException("变量不存在",ErrorEnum.DATA_NOT_FOUND);
        else if (res.getDeleted())
            throw new DatabaseException("变量已经删除",ErrorEnum.DATA_IS_DELETED);
        return systemConstantMapper.logicalDeleteByPrimaryKey(constantId);
    }

    public int delete(String name) {
        SystemConstant res = oneByName(name);
        if (res == null)
            throw new DatabaseException("变量不存在",ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("变量已经被删除了",ErrorEnum.DATA_IS_DELETED);
        SystemConstantExample example = new SystemConstantExample();
        SystemConstantExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        return systemConstantMapper.logicalDeleteByExample(criteria.example());
    }

    @Override
    public int update(SystemConstant constant) throws RuntimeException {
        SystemConstant res = oneByPK(constant.getConstantId());
        if (res == null)
            throw new DatabaseException("变量不存在",ErrorEnum.DATA_NOT_FOUND);
        return systemConstantMapper.updateByPrimaryKeySelective(constant);
    }

}
