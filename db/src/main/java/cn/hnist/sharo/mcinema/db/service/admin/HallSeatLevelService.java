package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.db.dao.HallSeatLevelMapper;
import cn.hnist.sharo.mcinema.db.pojo.HallSeatLevel;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HallSeatLevelService implements CRUDService<HallSeatLevel,Integer> {
    @Resource
    HallSeatLevelMapper seatLevelMapper;

    @Override
    public HallSeatLevel oneByPK(Integer pk) {
        return null;
    }

    @Override
    public int delete(Integer pk) throws WithTypeException {
        return 0;
    }

    @Override
    public int update(HallSeatLevel update) throws WithTypeException {
        return 0;
    }

    @Override
    public int insert(HallSeatLevel insert) throws WithTypeException {
        return 0;
    }
}
