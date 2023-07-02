package cn.hnist.sharo.mcinema.db.service.user;

import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.AdvertisingBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBase;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class U_AdvertisingBaseService{
    @Resource
    AdvertisingBaseMapper advertisingMapper;

    // 对首页的广告数据进行缓存
    @Cacheable(cacheNames = "mcinema:cache:user:advertising:index")
    public List<AdvertisingBase> list() {
        AdvertisingBaseExample.Criteria criteria = AdvertisingBaseExample.newAndCreateCriteria();
        return advertisingMapper.selectByExample(criteria.example().orderBy("create_time DESC"));
    }
}
