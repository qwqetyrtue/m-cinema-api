package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.HallBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.HallBase;
import cn.hnist.sharo.mcinema.db.pojo.HallBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HallBaseService implements CRUDService<HallBase, Long> {
    @Resource
    HallBaseMapper hallMapper;

    /**
     * <h2>模糊查询影厅列表</h2>
     *
     * @param filter      查询条件
     * @param seatNumUp   座位数量上限
     * @param seatNumDown 座位数量下限
     * @return 影厅列表
     */
    public List<HallBase> listByFilter(HallBase filter, Integer seatNumUp, Integer seatNumDown) {
        HallBaseExample example = new HallBaseExample();
        HallBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getName()))
            criteria.andNameLike("%" + filter.getName() + "%");
        if (DataUtil.checkEmpty(filter.getHallType()))
            criteria.andHallTypeEqualTo(filter.getHallType());
        if (DataUtil.checkEmpty(seatNumUp))
            criteria.andSeatNumLessThanOrEqualTo(seatNumUp);
        if (DataUtil.checkEmpty(seatNumDown))
            criteria.andSeatNumGreaterThanOrEqualTo(seatNumDown);
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        return hallMapper.selectByExample(criteria.example());
    }

    public List<HallBase> listFormOptionsByFilter(HallBase filter) {
        HallBase.Column[] columns = {HallBase.Column.hallId, HallBase.Column.name};
        HallBaseExample example = new HallBaseExample();
        HallBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getName()))
            criteria.andNameLike("%" + filter.getName() + "%");
        else return new ArrayList<>();
        return hallMapper.selectByExampleSelective(criteria.example(), columns);
    }

    /**
     * <h2>通过影厅ID查询单个影厅信息</h2>
     *
     * @param hallId 影厅ID
     * @return 影厅信息
     */
    @Override
    public HallBase oneByPK(Long hallId) {
        return hallMapper.selectByPrimaryKey(hallId);
    }

    /**
     * <h2>修改影厅信息</h2>
     *
     * @param hallBase 影厅更新信息
     * @return update语句检索条数
     */
    @Override
    public int update(HallBase hallBase) throws WithTypeException {
        HallBase res = oneByPK(hallBase.getHallId());
        if (res == null)
            throw new DatabaseException("不存在此影厅", ErrorEnum.DATA_NOT_FOUND);
        return hallMapper.updateByPrimaryKeySelective(hallBase);
    }

    public int updateForProfile(HallBase hallBase) {
        return update(hallBase);
    }

    public int updateForArrange(HallBase hallBase) {
        return update(hallBase);
    }

    public int updateForInline(Long pk) {
        HallBase res = oneByPK(pk);
        if (res == null)
            throw new DatabaseException("不存在此影厅", ErrorEnum.DATA_NOT_FOUND);
        if (!res.getDeleted())
            throw new DatabaseException("影厅已经上线了", ErrorEnum.INVALID_OPERATE);
        if (DataUtil.checkEmpty(res.getName()) &&
                DataUtil.checkEmpty(res.getHallType()) &&
                DataUtil.checkEmpty(res.getSeatNum()) &&
                DataUtil.checkEmpty(res.getColumnNum()) &&
                DataUtil.checkEmpty(res.getRowNum()) &&
                DataUtil.checkMatrix(res.getSeatArrange(), res.getRowNum(), res.getColumnNum(), false)
        ) {
            HallBase hallBase = new HallBase();
            hallBase.setHallId(pk);
            hallBase.setDeleted(false);
            return hallMapper.updateByPrimaryKeySelective(hallBase);
        } else throw new DatabaseException("缺少数据信息", ErrorEnum.ILLEGAL_OPERATE);
    }


    /**
     * <h2>添加影厅信息</h2>
     *
     * @param hallBase 影厅添加信息
     * @return insert语句添加条数
     */
    @Override
    public int insert(HallBase hallBase) throws RuntimeException {
        return hallMapper.insertSelective(hallBase);
    }

    /**
     * <h2>删除影厅信息</h2>
     *
     * @param hallId 影厅ID
     * @return delete语句检索条数
     */
    @Override
    public int delete(Long hallId) throws RuntimeException {
        HallBase res = oneByPK(hallId);
        if (res == null)
            throw new DatabaseException("不存在此影厅", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("此影厅已被删除", ErrorEnum.DATA_IS_DELETED);
        return hallMapper.logicalDeleteByPrimaryKey(hallId);
    }

}
