package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.HallBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.HallSeatMapper;
import cn.hnist.sharo.mcinema.db.pojo.HallBase;
import cn.hnist.sharo.mcinema.db.pojo.HallSeat;
import cn.hnist.sharo.mcinema.db.pojo.HallSeatExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HallSeatService implements CRUDService<HallSeat, Long> {
    @Resource
    HallSeatMapper hallSeatMapper;
    @Resource
    HallBaseMapper hallBaseMapper;

    /**
     * <h2>根据Id查询座位</h2>
     *
     * @param seatId 座位编号
     * @return HallSeat
     */
    @Override
    public HallSeat oneByPK(Long seatId) {
        return hallSeatMapper.selectByPrimaryKey(seatId);
    }

    /**
     * <h2>根据影厅编号和厅内序号查询座位</h2>
     *
     * @param hallId      影厅编号
     * @param inHallIndex 厅内编号
     * @return HallSeat / null
     */
    public HallSeat one(Long hallId, Integer inHallIndex) {
        HallSeatExample example = new HallSeatExample();
        HallSeatExample.Criteria criteria = example.createCriteria();
        criteria.andHallIdEqualTo(hallId).andInHallIndexEqualTo(inHallIndex);
        List<HallSeat> res = hallSeatMapper.selectByExample(criteria.example());
        if (res != null && res.size() >= 1)
            return res.get(0);
        else
            return null;
    }

    /**
     * <h2>查询座位列表</h2>
     *
     * @param filter 筛选
     * @return List
     */
    public List<HallSeat> list(HallSeat filter) {
        HallSeatExample example = new HallSeatExample();
        HallSeatExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getHallId())) {
            criteria.andHallIdEqualTo(filter.getHallId());
        }
        if (DataUtil.checkEmpty(filter.getDeleted())) {
            criteria.andDeletedEqualTo(filter.getDeleted());
        }
        if (DataUtil.checkEmpty(filter.getLevel())) {
            criteria.andLevelEqualTo(filter.getLevel());
        }

        return hallSeatMapper.selectByExample(criteria.example());
    }

    @Override
    public int insert(HallSeat insert) throws WithTypeException {
        HallSeat res = one(insert.getHallId(), insert.getInHallIndex());
        if (res != null) {
            throw new DatabaseException("此位置已存在座位 [" + insert.getHallId() + ":" + insert.getInHallIndex() + "]", ErrorEnum.CONSTRAINT_EXCEPTION);
        }
        return hallSeatMapper.insertSelective(insert);
    }

    @Transactional
    public Map<String,Object> batchInsert(Long fk) {
        HallBase res = hallBaseMapper.selectByPrimaryKey(fk);
        if (res == null) throw new DatabaseException("不存在此影厅", ErrorEnum.DATA_NOT_FOUND);
        if(res.getSeatNum() == 0) throw new DatabaseException("此影厅无座位", ErrorEnum.CONSTRAINT_EXCEPTION);
        if(res.getRowNum() == 0 || res.getColumnNum() == 0) throw new DatabaseException("此影厅无座位布局", ErrorEnum.CONSTRAINT_EXCEPTION);
        LocalDateTime now = LocalDateTime.now();
        int num = res.getSeatNum();
        int error = 0;
        List<HallSeat> errorObj = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            HallSeat record = new HallSeat();
            record.setHallId(fk);
            record.setCreateTime(now);
            record.setInHallIndex(i + 1);
            try {
                this.insert(record);
            } catch (WithTypeException e) {
                error++;
                errorObj.add(record);
            }
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("total",num);
        data.put("error",error);
        data.put("list",errorObj);
        return data;
    }

    @Override
    public int delete(Long pk) throws WithTypeException {
        return 0;
    }

    @Override
    public int update(HallSeat update) throws WithTypeException {
        HallSeat res = oneByPK(update.getSeatId());
        if (res == null)
            throw new DatabaseException("此座位不存在", ErrorEnum.DATA_NOT_FOUND);
        return hallSeatMapper.insertSelective(update);
    }

    @Transactional
    public Map<String,Object> batchUpdate(Long[] pkList) {
        int num = pkList.length;
        int error = 0;
        List<Long> errorObj = new ArrayList<>();
        for (Long pk : pkList) {
            try{
                deletedUpdate(pk);
            }catch (WithTypeException e){
                error ++;
                errorObj.add(pk);
            }
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("total",num);
        data.put("error",error);
        data.put("list",errorObj);
        return data;
    }

    public int deletedUpdate(Long pk) throws WithTypeException {
        HallSeat seat = new HallSeat();
        seat.setSeatId(pk);
        seat.setDeleted(false);
        return update(seat);
    }


}
