package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.StatusEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.TicketBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.VTicketMapper;
import cn.hnist.sharo.mcinema.db.pojo.TicketBase;
import cn.hnist.sharo.mcinema.db.pojo.TicketBaseExample;
import cn.hnist.sharo.mcinema.db.pojo.VTicket;
import cn.hnist.sharo.mcinema.db.pojo.VTicketExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketBaseService implements CRUDService<TicketBase, Long> {
    @Resource
    TicketBaseMapper ticketMapper;
    @Resource
    VTicketMapper vTicketMapper;

    public List<VTicket> listByTime(
            TicketBase filter,
            LocalDateTime createTimeUp, LocalDateTime createTimeDown,
            LocalDateTime useTimeUp, LocalDateTime useTimeDown,
            LocalDateTime refundTimeUp, LocalDateTime refundTimeDown
    ) {
        VTicketExample example = new VTicketExample();
        VTicketExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getTicketStatus())) {
            // 根据不同的状态查询对应的操作时间,如已退票,结合退票时间查询,退票时间又包括:退票时间,退票时间上限,退票时间下限
            // 状态:售出,查询范围:创建时间
            if (filter.getTicketStatus().equals(StatusEnum.TicketStatus.PAID.v())) {
                criteria.andTicketStatusEqualTo(StatusEnum.TicketStatus.PAID.v());
                if (DataUtil.checkEmpty(createTimeUp))
                    criteria.andCreateTimeLessThanOrEqualTo(createTimeUp);
                if (DataUtil.checkEmpty(createTimeDown))
                    criteria.andCreateTimeGreaterThanOrEqualTo(createTimeDown);
            }
            // 状态:退票,查询范围:创建时间
            else if (filter.getTicketStatus().equals(StatusEnum.TicketStatus.REFUND.v())) {
                criteria.andTicketStatusEqualTo(StatusEnum.TicketStatus.REFUND.v());
                if (DataUtil.checkEmpty(refundTimeUp))
                    criteria.andRefundTimeLessThanOrEqualTo(refundTimeUp);
                if (DataUtil.checkEmpty(refundTimeDown))
                    criteria.andRefundTimeGreaterThanOrEqualTo(refundTimeDown);
            }
            // 状态:完成,查询范围:使用时间
            else if (filter.getTicketStatus().equals(StatusEnum.TicketStatus.USED.getValue())) {
                if (DataUtil.checkEmpty(useTimeUp))
                    criteria.andUseTimeLessThanOrEqualTo(useTimeUp);
                if (DataUtil.checkEmpty(useTimeDown))
                    criteria.andUseTimeGreaterThanOrEqualTo(useTimeDown);
            }
            // 状态:其他,查询故障票务
            else {
                criteria.andTicketStatusEqualTo(StatusEnum.TicketStatus.ACCIDENT.v());
            }
        }
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        return vTicketMapper.selectByExample(criteria.example());
    }

    public List<VTicket> listByHall(TicketBase filter, Long hallId) {
        VTicketExample example = new VTicketExample();
        VTicketExample.Criteria criteria = example.createCriteria();
        criteria.andHallIdEqualTo(hallId);
        if (DataUtil.checkEmpty(filter.getTicketStatus()))
            criteria.andTicketStatusEqualTo(filter.getTicketStatus());
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        return vTicketMapper.selectByExample(criteria.example());
    }

    public List<VTicket> listByUser(TicketBase filter) {
        VTicketExample example = new VTicketExample();
        VTicketExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(filter.getUserId());
        if (DataUtil.checkEmpty(filter.getTicketStatus()))
            criteria.andTicketStatusEqualTo(filter.getTicketStatus());
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        return vTicketMapper.selectByExample(criteria.example());
    }

    public List<VTicket> listByFilm(TicketBase filter,Long filmId) {
        VTicketExample example = new VTicketExample();
        VTicketExample.Criteria criteria = example.createCriteria();
        criteria.andFilmIdEqualTo(filmId);
        if (DataUtil.checkEmpty(filter.getTicketStatus()))
            criteria.andTicketStatusEqualTo(filter.getTicketStatus());
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        return vTicketMapper.selectByExample(criteria.example());
    }

    @Override
    public TicketBase oneByPK(Long pk) {
        return ticketMapper.selectByPrimaryKey(pk);
    }

    @Override
    public int delete(Long pk) throws RuntimeException {
        TicketBase res = oneByPK(pk);
        if (res == null)
            throw new DatabaseException("此票据不存在", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("票据已被删除", ErrorEnum.DATA_IS_DELETED);
        return ticketMapper.logicalDeleteByPrimaryKey(pk);
    }

    @Override
    public int update(TicketBase update) throws RuntimeException {
        TicketBase res = oneByPK(update.getTicketId());
        if (res == null)
            throw new DatabaseException("此票据不存在", ErrorEnum.DATA_NOT_FOUND);
        return ticketMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public int insert(TicketBase insert) throws RuntimeException {
        TicketBaseExample example = new TicketBaseExample();
        TicketBaseExample.Criteria criteria = example.createCriteria();
        criteria.andSeatIdEqualTo(insert.getSeatId());
        criteria.andSceneIdEqualTo(insert.getSceneId());
        TicketBase res = ticketMapper.selectOneByExample(criteria.example());
        if (res != null && !res.getDeleted())
            throw new DatabaseException("存在相同场次相同座位的票据信息", ErrorEnum.CONSTRAINT_EXCEPTION);
        return ticketMapper.insertSelective(insert);
    }

}
