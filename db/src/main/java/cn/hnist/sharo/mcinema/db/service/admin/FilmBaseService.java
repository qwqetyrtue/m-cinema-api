package cn.hnist.sharo.mcinema.db.service.admin;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.FilmBaseMapper;
import cn.hnist.sharo.mcinema.db.pojo.FilmBase;
import cn.hnist.sharo.mcinema.db.pojo.FilmBaseExample;
import cn.hnist.sharo.mcinema.db.service.CRUDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FilmBaseService implements CRUDService<FilmBase, Long> {
    @Resource
    FilmBaseMapper filmMapper;

    public List<FilmBase> listByTime(FilmBase filter, Integer durationUp, Integer durationDown, LocalDateTime createTimeUp, LocalDateTime createTimeDown) {
        FilmBaseExample example = new FilmBaseExample();
        FilmBaseExample.Criteria criteria = example.createCriteria();

        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());

        if (DataUtil.checkEmpty(durationUp))
            criteria.andDurationLessThanOrEqualTo(durationUp);
        if (DataUtil.checkEmpty(durationDown))
            criteria.andDurationGreaterThanOrEqualTo(durationDown);

        if (DataUtil.checkEmpty(createTimeUp))
            criteria.andCreateTimeLessThanOrEqualTo(createTimeUp);
        if (DataUtil.checkEmpty(createTimeDown))
            criteria.andCreateTimeGreaterThanOrEqualTo(createTimeDown);

        return filmMapper.selectByExample(example);
    }

    public List<FilmBase> listByClassify(FilmBase filter, Integer ageUp, Integer ageDown) {
        FilmBaseExample example = new FilmBaseExample();
        FilmBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        if (DataUtil.checkEmpty(filter.getFilmClassify()))
            criteria.andFilmClassifyLike("%" + filter.getFilmClassify() + "%");
        if (DataUtil.checkEmpty(filter.getArea()))
            criteria.andAreaLike("%" + filter.getArea() + "%");
        if (DataUtil.checkEmpty(filter.getFilmType()))
            criteria.andFilmTypeLike("%" + filter.getFilmType() + "%");
        if (DataUtil.checkEmpty(filter.getLanguage()))
            criteria.andLanguageEqualTo(filter.getLanguage());
        if (DataUtil.checkEmpty(filter.getVersion()))
            criteria.andVersionLike("%" + filter.getVersion() + "%");
        if (DataUtil.checkEmpty(ageUp))
            criteria.andAgeLessThanOrEqualTo(ageUp);
        if (DataUtil.checkEmpty(ageDown))
            criteria.andAgeGreaterThanOrEqualTo(ageDown);
        return filmMapper.selectByExample(criteria.example());
    }

    public List<FilmBase> listByProfile(FilmBase filter) {
        FilmBaseExample example = new FilmBaseExample();
        FilmBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getDeleted()))
            criteria.andDeletedEqualTo(filter.getDeleted());
        if (DataUtil.checkEmpty(filter.getName()))
            criteria.andNameLike("%" + filter.getName() + "%");
        else if (DataUtil.checkEmpty(filter.getForeignName()))
            criteria.andNameLike("%" + filter.getForeignName() + "%");
        else if (DataUtil.checkEmpty(filter.getAlias()))
            criteria.andAliasLike("%" + filter.getAlias() + "%");

        return filmMapper.selectByExample(criteria.example());
    }

    public List<FilmBase> listFormOptionsByFilter(FilmBase filter) {
        FilmBase.Column[] columns = {FilmBase.Column.filmId, FilmBase.Column.name};
        FilmBaseExample example = new FilmBaseExample();
        FilmBaseExample.Criteria criteria = example.createCriteria();
        if (DataUtil.checkEmpty(filter.getName()))
            criteria.andNameLike("%" + filter.getName() + "%");
        else return new ArrayList<>();
        return filmMapper.selectByExample(criteria.example());
    }

    /**
     * <h2>通过ID查询单个电影</h2>
     *
     * @param filmId 电影ID
     * @return 单个电影信息
     */
    public FilmBase oneByPK(Long filmId) {
        return filmMapper.selectByPrimaryKey(filmId);
    }

    /**
     * <h2>添加一部电影</h2>
     *
     * @param film 添加电影信息
     * @return insert语句执行条数
     */
    public int insert(FilmBase film) throws RuntimeException {
        return filmMapper.insertSelective(film);
    }

    /**
     * <h2>更新电影信息</h2>
     *
     * @param update 电影更新信息
     * @return update语句检索条数
     */
    public int update(FilmBase update) throws RuntimeException {
        FilmBase res = oneByPK(update.getFilmId());
        if (res == null)
            throw new DatabaseException("不存在此电影", ErrorEnum.DATA_NOT_FOUND);
        return filmMapper.updateByPrimaryKeySelective(update);
    }

    /**
     * <h2>删除电影信息(逻辑删除)</h2>
     *
     * @param filmId 电影ID
     * @return delete语句检索条数
     */
    public int delete(Long filmId) throws RuntimeException {
        FilmBase res = oneByPK(filmId);
        if (res == null)
            throw new DatabaseException("不存在此电影", ErrorEnum.DATA_NOT_FOUND);
        if (res.getDeleted())
            throw new DatabaseException("此电影已下架", ErrorEnum.DATA_IS_DELETED);
        return filmMapper.logicalDeleteByPrimaryKey(filmId);
    }

    public int recover(Long filmId) throws RuntimeException {
        FilmBase res = oneByPK(filmId);
        if (res == null)
            throw new DatabaseException("不存在此电影", ErrorEnum.DATA_NOT_FOUND);
        if (!res.getDeleted())
            throw new DatabaseException("此电影未下架", ErrorEnum.DATA_IS_DELETED);
        FilmBase record = new FilmBase();
        record.setFilmId(filmId);
        record.setDeleted(false);
        return filmMapper.updateByPrimaryKeySelective(record);
    }
}
