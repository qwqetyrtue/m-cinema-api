package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.AdvertisingBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBase;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdvertisingBaseService implements CRUDService<AdvertisingBase, Long> {
    @Resource
    AdvertisingBaseMapper advertisingMapper;

    public List<AdvertisingBase> listByName(String name) {
        AdvertisingBaseExample.Criteria criteria = AdvertisingBaseExample.newAndCreateCriteria();
        if(DataUtil.checkEmpty(name))
            criteria.andNameLike("%" + name + "%");
        return advertisingMapper.selectByExample(criteria.example());
    }

    @Override
    public AdvertisingBase oneByPK(Long pk) {
        return advertisingMapper.selectByPrimaryKey(pk);
    }

    @Override
    public int delete(Long pk) throws WithTypeException {
        return advertisingMapper.deleteByPrimaryKey(pk);
    }

    @Override
    public int update(AdvertisingBase update) throws WithTypeException {
        return advertisingMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public int insert(AdvertisingBase insert) throws WithTypeException {
        return advertisingMapper.insertSelective(insert);
    }
}
