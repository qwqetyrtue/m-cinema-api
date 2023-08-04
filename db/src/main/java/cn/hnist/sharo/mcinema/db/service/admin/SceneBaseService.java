package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.HallSeatUtil;
import cn.hnist.sharo.mcinema.db.dao.FilmBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.HallBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.SceneBaseMapper;
import cn.hnist.sharo.mcinema.db.dao.VSceneMapper;
import cn.hnist.sharo.mcinema.db.pojo.*;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class SceneBaseService implements CRUDService<SceneBase, Long> {
    @Resource
    SceneBaseMapper sceneMapper;
    @Resource
    FilmBaseMapper filmMapper;
    @Resource
    HallBaseMapper hallMapper;
    @Resource
    VSceneMapper vSceneMapper;

    @Override
    public SceneBase oneByPK(Long pk) {
        return sceneMapper.selectByPrimaryKey(pk);
    }


    public List<VScene> listVByRecent(LocalDate dayUp, LocalDate dayDown) {
        VSceneExample example = new VSceneExample();
        VSceneExample.Criteria criteria = example.createCriteria();
        criteria.andDayBetween(dayDown, dayUp);
        return vSceneMapper.selectByExample(example.orderBy("update_time"));
    }

    public List<VScene> listByHall(Long hallId, LocalDate dayUp, LocalDate dayDown) {
        VSceneExample example = new VSceneExample();
        VSceneExample.Criteria criteria = example.createCriteria();
        criteria.andHallIdEqualTo(hallId);
        criteria.andDayBetween(dayDown, dayUp);
        return vSceneMapper.selectByExample(criteria.example().orderBy("update_time"));
    }

    public List<VScene> listByFilm(Long filmId, LocalDate dayUp, LocalDate dayDown) {
        VSceneExample example = new VSceneExample();
        VSceneExample.Criteria criteria = example.createCriteria();
        criteria.andFilmIdEqualTo(filmId);
        criteria.andDayBetween(dayDown, dayUp);
        return vSceneMapper.selectByExample(criteria.example().orderBy("update_time"));
    }

    @Override
    public int delete(Long pk) throws WithTypeException {
        SceneBase res = oneByPK(pk);
        if (res == null)
            throw new DatabaseException("此场次安排不存在", ErrorEnum.DATA_NOT_FOUND);
        return sceneMapper.deleteByPrimaryKey(pk);
    }

    @Override
    public int update(SceneBase update) throws WithTypeException {
        SceneBase res = oneByPK(update.getSceneId());
        if (res == null)
            throw new DatabaseException("此场次安排不存在", ErrorEnum.DATA_NOT_FOUND);
        return sceneMapper.updateByPrimaryKeySelective(update);
    }

    @Transactional
    @Override
    public int insert(SceneBase insert) throws WithTypeException {
        FilmBase film = filmMapper.selectByPrimaryKey(insert.getFilmId());
        if (film == null) throw new DatabaseException("此电影不存在", ErrorEnum.DATA_NOT_FOUND);
        if (film.getDeleted()) throw new DatabaseException("此电影已经下架", ErrorEnum.DATA_IS_DELETED);
        HallBase hall = hallMapper.selectByPrimaryKey(insert.getHallId());
        if (hall == null) throw new DatabaseException("此影厅不存在", ErrorEnum.DATA_NOT_FOUND);
        if (hall.getDeleted()) throw new DatabaseException("此影厅不可用", ErrorEnum.DATA_IS_DELETED);
        Integer duration = film.getDuration();
        LocalDateTime endTime = insert.getBeginTime().plusMinutes(duration);
        VSceneExample.Criteria criteria1 = VSceneExample.newAndCreateCriteria();
        criteria1.andHallIdEqualTo(insert.getHallId());
        criteria1.andBeginTimeBetween(insert.getBeginTime(), endTime);
        VSceneExample.Criteria criteria2 = VSceneExample.newAndCreateCriteria();
        criteria2.andHallIdEqualTo(insert.getHallId());
        criteria2.andEndTimeBetween(insert.getBeginTime(), endTime);
        criteria1.example().or(criteria2);
        long res = vSceneMapper.countByExample(criteria1.example());
        if (res != 0)
            throw new DatabaseException("在设置的时间段,影厅已被占用", ErrorEnum.CONSTRAINT_EXCEPTION);
        insert.setSeatChooseArrange(HallSeatUtil.getChooseSeatArrange(hall.getSeatArrange()));
        insert.setSeatArrange(hall.getSeatArrange());
        return sceneMapper.insertSelective(insert);
    }

    public int updateSecure(SceneBase update) {
//        Long res = countByFK_Time(update.getHallId(),update.getFilmId(), update.getDay(),update.getBeginTime());
////        if (res != 0)
////            throw new DatabaseException("在设置的时间段,影厅已被占用", ErrorEnum.CONSTRAINT_EXCEPTION);
////        return sceneMapper.updateByPrimaryKeySelective(update);

        return -1;
    }

    public int updatePrice(SceneBase update) {
        return update(update);
    }
}
