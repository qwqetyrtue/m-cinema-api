package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.db.dao.DiscountBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.DiscountBase;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DiscountBaseService implements CRUDService<DiscountBase,Long> {
    @Resource
    DiscountBaseMapper discountMapper;

    @Override
    public DiscountBase oneByPK(Long pk) {
        return null;
    }

    @Override
    public int delete(Long pk) throws WithTypeException {
        return 0;
    }

    @Override
    public int update(DiscountBase update) throws WithTypeException {
        return 0;
    }

    @Override
    public int insert(DiscountBase insert) throws WithTypeException {
        return 0;
    }
}
