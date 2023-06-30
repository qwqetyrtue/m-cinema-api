package cn.hnist.sharo.mcinema.db.service.system;

import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.StorageBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.StorageBase;
import cn.hnist.sharo.mcinema.db.pojo.StorageBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StorageBaseService implements CRUDService<StorageBase, Long> {
    @Resource
    StorageBaseMapper storageMapper;


    @Override
    public StorageBase oneByPK(Long pk) {
        return storageMapper.selectByPrimaryKey(pk);
    }

    public List<StorageBase> listByFilter(StorageBase filter) {
        StorageBaseExample example = new StorageBaseExample();
        StorageBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getType()))
            criteria.andTypeEqualTo(filter.getType());
        if (DataUtil.checkEmpty(filter.getContentType()))
            criteria.andContentTypeEqualTo(filter.getContentType());
        if (DataUtil.checkEmpty(filter.getName()))
            criteria.andNameLike("%" + filter.getName() + "%");
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        return storageMapper.selectByExample(criteria.example());
    }

    public List<StorageBase> listByTime(
            StorageBase filter,
            LocalDateTime createTimeUp, LocalDateTime createTimeDown,
            LocalDateTime updateTimeUp, LocalDateTime updateTimeDown) {
        StorageBaseExample example = new StorageBaseExample();
        StorageBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getType()))
            criteria.andTypeEqualTo(filter.getType());
        if (DataUtil.checkEmpty(createTimeDown))
            criteria.andCreateTimeGreaterThanOrEqualTo(createTimeDown);
        if (DataUtil.checkEmpty(createTimeUp))
            criteria.andCreateTimeLessThanOrEqualTo(createTimeUp);
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        return storageMapper.selectByExample(criteria.example());
    }

    public StorageBase oneByKey(String key) {
        StorageBaseExample example = new StorageBaseExample();
        StorageBaseExample.Criteria criteria = example.createCriteria();
        criteria.andKeyEqualTo(key);
        return storageMapper.selectOneByExample(example);
    }

    @Override
    public int delete(Long pk) throws RuntimeException {
        return storageMapper.deleteByPrimaryKey(pk);
    }

    @Override
    public int update(StorageBase update) throws RuntimeException {
        return storageMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public int insert(StorageBase insert) throws RuntimeException {
        return storageMapper.insertSelective(insert);
    }
}
