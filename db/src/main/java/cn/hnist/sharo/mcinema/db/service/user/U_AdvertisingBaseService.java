package cn.hnist.sharo.mcinema.db.service.user;

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
public class U_AdvertisingBaseService implements CRUDService<AdvertisingBase, Long> {
    @Resource
    AdvertisingBaseMapper advertisingMapper;

    public List<AdvertisingBase> list() {
        AdvertisingBaseExample.Criteria criteria = AdvertisingBaseExample.newAndCreateCriteria();
        return advertisingMapper.selectByExample(criteria.example().orderBy("create_time"));
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
